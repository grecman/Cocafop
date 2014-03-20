package vwg.skoda.cocafop.job;

import java.util.Properties;

public class Arguments {

	// properties names
	public static String USER_PROPERTY = "user";
	public static String TERKA_PROPERTY = "terka";
	public static String IN_FILE = "inFile";
	public static String LOG = "log";
	public static String userName;
	public static String databaseName;
	public static String password;

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	
	public Properties parseArg(String args[]) {
        Properties props = new Properties();
        for (int i=0; i<args.length-1; i++)
        {
           if ("-user".equals(args[i]))
           {
               if (args[i+1].startsWith("-"))
               {
                   	props.clear();
                   	return props;
               } else {props.put(USER_PROPERTY,args[i+1]);}
           } else 
           if ("-terka".equals(args[i]))
           {
               if (args[i+1].startsWith("-"))
               {
                   props.clear();
                   return props;
               } else {props.put(TERKA_PROPERTY,args[i+1]);}   
           } else 
           if ("-inFile".equals(args[i]))
           {
               if (args[i+1].startsWith("-")){
                           props.clear();
                           return props;
               } else { props.put(IN_FILE,args[i+1]); }   
           } else
           if ("-log".equals(args[i]))
           {
               if (args[i+1].startsWith("-")){
                           props.clear();
                           return props;
               } else { props.put(LOG,args[i+1]); }   
           } else  
           {                                                             
               props.clear();
               return props;                 
           }
           i++;                                                                 
        }
	return props;
	}
	
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	
	public void parseUser(String userLong){
		userLong = userLong.trim();
		userName = userLong.substring(0, userLong.indexOf('/')).toLowerCase();
		password = userLong.substring(userLong.indexOf('/') + 1, userLong.indexOf('@')).toLowerCase();
		databaseName = userLong.substring(userLong.indexOf('@') + 1, userLong.length()).toLowerCase();
		
	}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	
	public String getUser(String userLong){
		userLong = userLong.trim();
		String uName = userLong.substring(0, userLong.indexOf('/'));
		return uName;
	}

	public String getPassword(String userLong){
		userLong = userLong.trim();
		String passw = userLong.substring(userLong.indexOf('/') + 1, userLong.indexOf('@'));
		return passw;
	}

	public String getDatabaseName(String userLong){
		userLong = userLong.trim();
		String dbName = userLong.substring(userLong.indexOf('@') + 1, userLong.length());
		return dbName;
	}
}
