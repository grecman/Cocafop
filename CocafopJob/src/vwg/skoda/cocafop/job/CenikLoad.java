package vwg.skoda.cocafop.job;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;

public class CenikLoad {

	//static Logger log = Logger.getLogger(CenikLoad.class);
	static Logger log = Logger.getLogger(CenikLoad.class);

	private StringBuffer sB;

	private static PreparedStatement psIns;
	private static PreparedStatement psUpdMe;
	private static PreparedStatement psUpdCena;

	final static SimpleDateFormat DATE_FORMAT_MES = new SimpleDateFormat("MM"); // pattern pro format datumu
	final static SimpleDateFormat DATE_FORMAT_ROK = new SimpleDateFormat("yyyy");
	static Integer MESIC = null;
	static Integer ROK = null;

	// struktura vstupniho souboru SMLOUVY.txt	
	class StrukturaInFile {

		Integer rok;
		Integer mesic;
		String zavod;
		String cisloDilu;
		String me;
		Float cena;
		Float mnozstvi;
		String mena;
		Float kurz;
		String menaOrig;

	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public static void main(String[] args) throws Exception {

		long zac = System.currentTimeMillis();
		log.info("Plneni ceniku COCAFOP ");
		Arguments pArg = new Arguments();
		Properties props = pArg.parseArg(args);
		if (props.isEmpty()) {
			log.error("No properties! Please set properties.");
			System.out.println("No properties! Please set properties.");
		} else {
			pArg.parseUser(props.getProperty(Arguments.USER_PROPERTY));
			log.debug("User: " + pArg.getUser(props.getProperty(Arguments.USER_PROPERTY)) + "/******@" + pArg.getDatabaseName(props.getProperty(Arguments.USER_PROPERTY)));
			log.debug("in File: " + props.getProperty(Arguments.IN_FILE));
			//System.out.println("User: " + pArg.getUser(props.getProperty(Arguments.USER_PROPERTY)) + "/******@" + pArg.getDatabaseName(props.getProperty(Arguments.USER_PROPERTY)));
			//System.out.println("Password: "+pArg.getPassword(props.getProperty(Arguments.USER_PROPERTY)));
			//System.out.println("in File: " + props.getProperty(Arguments.IN_FILE));
		}

		Connection conn;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			log.info("Class.forName: oracle.jdbc.driver.OracleDriver");
		} catch (Exception e) {
			System.out.println(e);
		}

		try {
			//System.out.println(pArg.getDatabaseName(props.getProperty("user"))+ " "+pArg.getUser(props.getProperty("user")) + " "+ pArg.getPassword(props.getProperty("user")));
			//conn = DriverManager.getConnection("jdbc:oracle:thin:@lzenta.skoda-db", "skasko2_usrwas", "skaskow2");
			conn = DriverManager.getConnection("jdbc:oracle:oci8:@" + pArg.getDatabaseName(props.getProperty("user")) + ".skoda-db", pArg.getUser(props.getProperty("user")),
					pArg.getPassword(props.getProperty("user")));
		} catch (SQLException sqle) {
			System.err.println("Unable to get connection: " + sqle.toString());
			conn = null;
		}

		if (conn == null) {
			System.err.println("Error in getting connection - connection is null");
			log.info("Error in getting connection - connection is null");
		}

		CenikLoad cen = new CenikLoad();

		List<StrukturaInFile> vstupniSouborAr = cen.nactiSoubor(props.getProperty(Arguments.IN_FILE));

		//HashMap<String,StrukturaInFile> vstupniSouborHm = cen.nactiSoubor(props.getProperty(Arguments.IN_FILE));
		// GRE: varianta s osetrenim duplicit (HashMap)

		/*    	
		    	for (Iterator it = vstupniSouborHm.entrySet().iterator(); it.hasNext();){
		    		Map.Entry e = (Entry) it.next();
		    		StrukturaSap st = cen.new StrukturaSap();
		    		st = (StrukturaSap) e.getValue();
		    		System.out.println(st.kotace + "   " + st.zavod + "  "  + st.cisloDilu);    		
		    	}    	
		*/

		Boolean exists = cen.kontrolaExistence(conn);
		if (!exists) {
			int iBatch = 0;// pocet prepare insertu
			//cen.prepareDelete(conn);
			cen.prepareInsert(conn);

			for (int i = 0; i < vstupniSouborAr.size(); i++) {
				StrukturaInFile st = cen.new StrukturaInFile();
				st = vstupniSouborAr.get(i);
				cen.naplnInsert(st);
				psIns.addBatch();
				iBatch = i + 1;
			}

			/*	    	for  (@SuppressWarnings("rawtypes")
							Iterator it = vstupniSouborHm.entrySet().iterator(); it.hasNext();){
				    		StrukturaInFile st = cen.new StrukturaInFile();
				    		@SuppressWarnings("rawtypes")
							Map.Entry e = (Map.Entry) it.next();
				    		st = (StrukturaInFile) e.getValue();
				    		cen.naplnInsert(st);
				    		psIns.addBatch();
				    		++iBatch;
				      	}
			*/

			if (iBatch > 0) {
				psIns.executeBatch();
				cen.prepareUpdateMe(conn);
				psUpdMe.executeUpdate();
				cen.prepareUpdateCena(conn);
				psUpdCena.executeUpdate();

				conn.commit();

				psIns.clearBatch();
				psUpdMe.clearBatch();
				psUpdCena.clearBatch();
			} else {
				log.error("gERROR: Nenacteny zadne hodnoty do psIns (prepareStatement).");
			}
			log.debug("Insertovanych radku: " + iBatch);
		} else {
			log.debug("Cenik s timto datem je jiz nahran v cocafop.gz38t_pricelist");
		}

		long kon = System.currentTimeMillis();
		log.debug("KONEC . . . . . doba davky: " + (kon - zac) / 1000 + " second");
		//System.out.println("KONEC . . . . . doba davky: " + (kon - zac) / 1000 + " second");

	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	

	List<StrukturaInFile> nactiSoubor(String inFile) {
		//System.out.println("###\tnactiSoubor()");
		log.debug("###\tnactiSoubor()");
		try {
			File souborZdroj = new File(inFile);
			//File souborZdroj = new File("C:" + File.separator + "TEMP" + File.separator + "smlouvy.txt");
			FileReader fr = new FileReader(souborZdroj);
			BufferedReader in = new BufferedReader(fr);

			List<StrukturaInFile> ar = new ArrayList<StrukturaInFile>();

			String radka;
			while ((radka = in.readLine()) != null) {
				List<String> tokeny = new ArrayList<String>();
				StringTokenizer t = new StringTokenizer(radka, "\t");
				@SuppressWarnings("unused")
				int i = 0;
				while (t.hasMoreTokens()) {
					String hodnota = t.nextToken();
					tokeny.add(hodnota);
					//System.out.println(i+++" "+hodnota);
				}
				//System.out.println("Size:\t\t"+tokeny.size());
				StrukturaInFile struk = new StrukturaInFile();
				struk.rok = Integer.valueOf(tokeny.get(0).toString().substring(0, 4)).intValue();
				struk.mesic = Integer.valueOf(tokeny.get(0).toString().substring(4)).intValue();
				struk.zavod = tokeny.get(1);
				struk.cisloDilu = rtrim(tokeny.get(2));
				struk.me = tokeny.get(3);
				struk.cena = Float.valueOf(tokeny.get(4).trim()).floatValue();
				struk.mnozstvi = Float.valueOf(tokeny.get(5).trim()).floatValue();
				struk.mena = tokeny.get(6);
				struk.kurz = n(tokeny.size()) < 9 ? 0f : Float.valueOf(n(tokeny.get(7).trim())).floatValue();
				struk.menaOrig = rtrim(n(tokeny.size() < 9 ? null : tokeny.get(8)));
				ar.add(struk);

				ROK = Integer.valueOf(tokeny.get(0).toString().substring(0, 4)).intValue();
				MESIC = Integer.valueOf(tokeny.get(0).toString().substring(4)).intValue();
			}
			fr.close();
			log.debug("Zpracovavane obdobi (cenik): " + ROK + " / " + MESIC);
			return (List<StrukturaInFile>) ar;
		} catch (Exception e) {
			System.out.println("Chyba pri nacteni souboru: " + e);
			log.error("Chyba pri nacteni souboru: " + e);
			return null;
		}
	}

	/*	HashMap<String, StrukturaInFile> nactiSoubor(String inFile) {
			//System.out.println("###\tnactiSoubor()");
			log.debug("###\tnactiSoubor()");
			try {
				File souborZdroj = new File(inFile);
				//File souborZdroj = new File("C:" + File.separator + "TEMP" + File.separator + "smlouvy.txt");
				FileReader fr = new FileReader(souborZdroj);
				BufferedReader in = new BufferedReader(fr);

				HashMap<String,StrukturaInFile> hm = new HashMap<String,StrukturaInFile>();
				
				String radka;
				while ((radka = in.readLine()) != null) {
					List<String> ggg = new ArrayList<String>();
					StringTokenizer token = new StringTokenizer(radka, "\t");
					//StringTokenizer token = new StringTokenizer(radka, ";");
					while (token.hasMoreTokens()) {
						String hodnota = token.nextToken();
						ggg.add(hodnota);
						//System.out.println(hodnota);
					}
					StrukturaInFile struk = new StrukturaInFile();
					struk.rok = Integer.valueOf(ggg.get(0).toString().substring(0, 4)).intValue();
					struk.mesic = Integer.valueOf(ggg.get(0).toString().substring(4)).intValue();
					struk.zavod = ggg.get(1);
					struk.cisloDilu = rtrim(ggg.get(2));
					struk.me = ggg.get(3);
					struk.cena = Float.valueOf(ggg.get(4).trim()).floatValue();
					struk.mnozstvi = Float.valueOf(ggg.get(5).trim()).floatValue();
					struk.mena = ggg.get(6);
					struk.kurz = n(tokeny.size()) < 9 ? 0f : Float.valueOf(n(tokeny.get(7).trim())).floatValue();
					struk.menaOrig = rtrim(n(tokeny.size() < 9 ? null : tokeny.get(8)));

					ROK = Integer.valueOf(ggg.get(0).toString().substring(0, 4)).intValue();
					MESIC = Integer.valueOf(ggg.get(0).toString().substring(4)).intValue();
					
					hm.put(struk.cisloDilu, struk);
				}
				fr.close();
				log.debug("Zpracovavane obdobi (cenik): " + ROK + " / " + MESIC);
				return (HashMap<String,StrukturaInFile>) hm;
			} catch (Exception e) {
				System.out.println("Chyba pri nacteni souboru: " + e);
				log.error("Chyba pri nacteni souboru: " + e);
				return null;
			}
		}
	*/

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private Boolean kontrolaExistence(Connection conn) throws SQLException {
		log.debug("###\t kontrolaExistence()");

		Statement stmt = null;
		ResultSet rs = null;

		//String sqlScript = "SELECT SUBSTR (rokmesic, 1, 4) || TO_NUMBER (SUBSTR (rokmesic, 5, 2)) rok_mesic FROM (SELECT MAX (rok || DECODE (LENGTH (mesic), 1, 0 || mesic, mesic)) rokmesic FROM cocafop.gz38t_pricelist) ";

		String sqlScript = "SELECT distinct rok||mesic rok_mesic FROM cocafop.gz38t_pricelist where rok = " + ROK + " and mesic = " + MESIC;

		ArrayList<String> ar = new ArrayList<String>();

		try {
			stmt = conn.createStatement();
			stmt.execute(sqlScript);
			rs = stmt.getResultSet();
			while (rs.next()) {
				ar.add(rs.getString("rok_mesic"));
			}
			rs.close();
			stmt.close();
		} catch (SQLException sqle) {
			System.err.println("Chyba pri sql " + sqlScript + sqle.toString());
			log.error("Chyba pri sql " + sqlScript + sqle.toString());
		}

		if (ar.isEmpty()) {
			return false;
		} else {
			String ulozenyRokMesicVDatabazi = ar.get(0);
			String rokMesicVeFilu = ROK.toString() + MESIC.toString();
			if (rokMesicVeFilu.equalsIgnoreCase(ulozenyRokMesicVDatabazi)) {
				return true;
			} else {
				return false;
			}
		}
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	

	private void prepareInsert(Connection conn) throws Exception {

		sB = new StringBuffer("INSERT INTO COCAFOP.GZ38T_PRICELIST ");
		sB.append("(ROK, MESIC, WK, PART_NUMBER, UOM_SAP, PRICE_SAP, QUANTITY, CURRENCY_WK, RATE, CURRENCY_ORIG)");
		sB.append(" VALUES (?,?,?,?,?, ?,?,?,?,?)");
		try {
			psIns = conn.prepareStatement(sB.toString());
		} catch (SQLException sqle) {
			System.out.println("Chyba pri " + sB + sqle.toString());
			log.error("Chyba pri " + sB + sqle.toString());
			throw sqle;
		}
		log.debug("..." + sB);
		//System.out.println("..." + sB);
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private void naplnInsert(StrukturaInFile struk) throws Exception {
		int p = 1;
		try {
			psIns.setInt(p++, ROK);
			psIns.setInt(p++, MESIC);
			psIns.setString(p++, struk.zavod);
			psIns.setString(p++, struk.cisloDilu);
			psIns.setString(p++, struk.me);
			psIns.setFloat(p++, struk.cena);
			psIns.setFloat(p++, struk.mnozstvi);
			psIns.setString(p++, struk.mena);
			psIns.setFloat(p++, n(struk.kurz));
			psIns.setString(p++, n(struk.menaOrig));
		} catch (SQLException sqle) {
			System.out.println("Chyba pri " + sB + sqle.toString());
			log.error("Chyba pri " + sB + sqle.toString());
			throw sqle;
		}
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private void prepareUpdateMe(Connection conn) throws Exception {

		sB = new StringBuffer("UPDATE COCAFOP.GZ38T_PRICELIST s21 ");
		sB.append(" SET UOM = (SELECT me FROM tech.gz20t21sap t21 WHERE t21.sap_meins = RTRIM (s21.UOM_SAP)) ");
		sB.append(" WHERE rok = " + ROK + "  and mesic = " + MESIC);
		psUpdMe = conn.prepareStatement(sB.toString());
		log.debug("..." + sB);
		//System.out.println("..." + sB);
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private void prepareUpdateCena(Connection conn) throws Exception {
		sB = new StringBuffer(
				"UPDATE COCAFOP.GZ38T_PRICELIST s21 "
						+ "SET PRICE = (SELECT (t21.PRICE_SAP / tech21.sap_koef) AS PRICE FROM COCAFOP.GZ38T_PRICELIST t21, tech.gz20t21sap tech21 WHERE s21.ROWID = t21.ROWID AND RTRIM (t21.UOM_SAP) = tech21.sap_meins)"
						+ "	WHERE rok = " + ROK + " AND mesic = " + MESIC);
		psUpdCena = conn.prepareStatement(sB.toString());
		log.debug("..." + sB);
		//System.out.println("..." + sB);
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	Float n(Float f) {
		return f == null ? 0f : f;
	}

	Integer n(Integer i) {
		return i == null ? 0 : i;
	}

	String n(String s) {
		return s == null ? "" : s;
	}

	public static String rtrim(String source) {
		return source.replaceAll("\\s+$", "");
	}

}
