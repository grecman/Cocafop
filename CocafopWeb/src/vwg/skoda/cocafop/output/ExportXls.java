package vwg.skoda.cocafop.output;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import vwg.skoda.cocafop.entities.Bom;
import vwg.skoda.cocafop.entities.ExchangeRate;

public class ExportXls {

	static Logger log = Logger.getLogger(ExportXls.class);

	NumberFormat floatToText = new DecimalFormat("##############0.00000",
			new DecimalFormatSymbols(new Locale("cs")));

	public void bomDetail(HttpServletResponse res, List<Bom> b,
			Boolean permPlant, Boolean permBrand, ExchangeRate ex)
			throws IOException {
		log.debug("###\t bomDetail(" + res + ", size:" + b.size() + ", "
				+ ex.getRate() + ")");
		res.setContentType("application/ms-excel");
		res.setHeader("Content-Disposition",
				"attachment; filename=\"COCAFOP_BoM_detail.xls\"");
		res.setHeader("Pragma", "public");
		res.setHeader("Cache-Control", "max-age=0");

		Workbook workbook = new HSSFWorkbook();
		Sheet sheet = workbook.createSheet("BoM - detail");

		Cell cell = null;
		Row row = null;
		int radka = 0;
		int bunka = 0;

		CellStyle dateFormat = workbook.createCellStyle();
		dateFormat.setDataFormat(workbook.createDataFormat().getFormat(
				"yyyy-mm-dd"));
		CellStyle floatFormat5 = workbook.createCellStyle();
		floatFormat5.setDataFormat(workbook.createDataFormat().getFormat(
				"##############0.00000"));
		CellStyle floatFormat2 = workbook.createCellStyle();
		floatFormat2.setDataFormat(workbook.createDataFormat().getFormat(
				"##############0.00"));

		row = sheet.createRow(radka++);
		row.createCell(bunka++).setCellValue("Type");
		row.createCell(bunka++).setCellValue("Part Number");
		row.createCell(bunka++).setCellValue("Part Description");
		row.createCell(bunka++).setCellValue("UoM");
		row.createCell(bunka++).setCellValue("Quantity");
		if (permPlant) {
			row.createCell(bunka++).setCellValue("Price Plant");
			row.createCell(bunka++).setCellValue("UoM Plant");
			row.createCell(bunka++).setCellValue("Quantity Plant");
			row.createCell(bunka++).setCellValue("Price Plant New");
			row.createCell(bunka++).setCellValue("Quantity x Price (Plant)");
		}
		if (permBrand) {
			row.createCell(bunka++).setCellValue("Price Brand");
			row.createCell(bunka++).setCellValue("UoM Brand");
			row.createCell(bunka++).setCellValue("Quantity Brand");
			row.createCell(bunka++).setCellValue("Price Brand New");
			row.createCell(bunka++).setCellValue("Quantity x Price (Brand)");
		}

		for (Bom g : b) {
			bunka = 0;
			row = sheet.createRow(radka++);
			row.createCell(bunka++).setCellValue(g.getTyp());
			row.createCell(bunka++).setCellValue(g.getPartNumber());
			row.createCell(bunka++).setCellValue(n(g.getPartDescEng()));
			row.createCell(bunka++).setCellValue(g.getUom());
			// instanceof - zjiöùuje, zda je oper·tor poûadovanÈho typu
			if (n0(g.getQuantity()) instanceof Float) {
				cell = row.createCell(bunka++);
				cell.setCellValue(g.getQuantity());
				cell.setCellStyle(floatFormat2);
			} else {
				row.createCell(bunka++).setCellValue("");
			}

			if (permPlant) {
				if (n0(g.getPricePlant()) instanceof Float) {
					cell = row.createCell(bunka++);
					cell.setCellValue(g.getPricePlant());
					cell.setCellStyle(floatFormat5);
				} else {
					row.createCell(bunka++).setCellValue("");
				}

				row.createCell(bunka++).setCellValue(n(g.getUomPlant()));

				if (n0(g.getQuantityPlant()) instanceof Float) {
					cell = row.createCell(bunka++);
					cell.setCellValue(g.getQuantityPlant());
					cell.setCellStyle(floatFormat2);
				} else {
					row.createCell(bunka++).setCellValue("");
				}

				if (n0(g.getPricePlantNew()) instanceof Float) {
					cell = row.createCell(bunka++);
					cell.setCellValue(g.getPricePlantNew());
					cell.setCellStyle(floatFormat5);
				} else {
					row.createCell(bunka++).setCellValue("");
				}

				if (g.getQuantityPlant() != null
						&& g.getPricePlantNew() != null) {
					cell = row.createCell(bunka++);
					cell.setCellValue(g.getQuantityPlant()
							* g.getPricePlantNew());
					cell.setCellStyle(floatFormat2);
				} else if (g.getQuantityPlant() != null
						&& g.getPricePlantNew() == null) {
					cell = row.createCell(bunka++);
					cell.setCellValue(g.getQuantityPlant() * g.getPricePlant());
					cell.setCellStyle(floatFormat2);
				} else if (g.getQuantityPlant() == null
						&& g.getPricePlantNew() != null) {
					cell = row.createCell(bunka++);
					cell.setCellValue(g.getQuantity() * g.getPricePlantNew());
					cell.setCellStyle(floatFormat2);
				} else {
					cell = row.createCell(bunka++);
					cell.setCellValue(g.getQuantity() * g.getPricePlant());
					cell.setCellStyle(floatFormat2);
				}
			}
			if (permBrand) {
				if (n0(g.getPriceBrand()) instanceof Float) {
					cell = row.createCell(bunka++);
					cell.setCellValue(g.getPriceBrand());
					cell.setCellStyle(floatFormat5);
				} else {
					row.createCell(bunka++).setCellValue("");
				}

				row.createCell(bunka++).setCellValue(n(g.getUomBrand()));

				if (n0(g.getQuantityBrand()) instanceof Float) {
					cell = row.createCell(bunka++);
					cell.setCellValue(g.getQuantityBrand());
					cell.setCellStyle(floatFormat2);
				} else {
					row.createCell(bunka++).setCellValue("");
				}

				if (n0(g.getPriceBrandNew()) instanceof Float) {
					cell = row.createCell(bunka++);
					cell.setCellValue(g.getPriceBrandNew());
					cell.setCellStyle(floatFormat5);
				} else {
					row.createCell(bunka++).setCellValue("");
				}
				if (g.getQuantityBrand() != null
						&& g.getPriceBrandNew() != null) {
					cell = row.createCell(bunka++);
					cell.setCellValue(g.getQuantityBrand()
							* g.getPriceBrandNew());
					cell.setCellStyle(floatFormat2);
				} else if (g.getQuantityBrand() != null
						&& g.getPriceBrandNew() == null) {
					cell = row.createCell(bunka++);
					cell.setCellValue(g.getQuantityBrand() * g.getPriceBrand());
					cell.setCellStyle(floatFormat2);
				} else if (g.getQuantityBrand() == null
						&& g.getPriceBrandNew() != null) {
					cell = row.createCell(bunka++);
					cell.setCellValue(g.getQuantity() * g.getPriceBrandNew());
					cell.setCellStyle(floatFormat2);
				} else {
					// System.out.println(g.getPartNumber()+"\t"+
					// g.getQuantity()+"\t"+ g.getPriceBrand());
					cell = row.createCell(bunka++);
					cell.setCellValue(g.getQuantity() * g.getPriceBrand());
					cell.setCellStyle(floatFormat2);
				}
			}
		}

		sheet = workbook.createSheet("Order - detail");
		radka = 0;

		bunka = 0;
		row = sheet.createRow(radka++);
		row.createCell(bunka++).setCellValue("MODEL KEY");
		bunka = 0;
		row = sheet.createRow(radka++);
		row.createCell(bunka++).setCellValue("Plant:");
		row.createCell(bunka++).setCellValue(
				b.get(0).getMkOrder().getModelKey().getBrand().getPlant()
						.getId()
						+ " - "
						+ b.get(0).getMkOrder().getModelKey().getBrand()
								.getPlant().getPlantName());
		bunka = 0;
		row = sheet.createRow(radka++);
		row.createCell(bunka++).setCellValue("Brand:");
		row.createCell(bunka++).setCellValue(
				b.get(0).getMkOrder().getModelKey().getBrand().getBrandMark()
						+ " - "
						+ b.get(0).getMkOrder().getModelKey().getBrand()
								.getBrandName());
		bunka = 0;
		row = sheet.createRow(radka++);
		row.createCell(bunka++).setCellValue("Model number:");
		row.createCell(bunka++).setCellValue(
				b.get(0).getMkOrder().getModelKey().getModelNumber());
		bunka = 0;
		row = sheet.createRow(radka++);
		row.createCell(bunka++).setCellValue("Year - month");
		row.createCell(bunka++).setCellValue(
				b.get(0).getMkOrder().getModelKey().getRok() + " - "
						+ b.get(0).getMkOrder().getMesic());
		bunka = 0;
		row = sheet.createRow(radka++);
		row.createCell(bunka++).setCellValue("Type:");
		row.createCell(bunka++).setCellValue(
				b.get(0).getMkOrder().getModelKey().getTyp());
		bunka = 0;
		row = sheet.createRow(radka++);
		row.createCell(bunka++).setCellValue("Model key:");
		row.createCell(bunka++).setCellValue(
				b.get(0).getMkOrder().getModelKey().getModelKey());
		bunka = 0;
		row = sheet.createRow(radka++);
		row.createCell(bunka++).setCellValue("Colour:");
		row.createCell(bunka++).setCellValue(
				b.get(0).getMkOrder().getModelKey().getColour());
		bunka = 0;
		row = sheet.createRow(radka++);
		row.createCell(bunka++).setCellValue("Valid from:");
		row.createCell(bunka++).setCellValue(
				b.get(0).getMkOrder().getModelKey().getValidFromYear()
						+ "/"
						+ b.get(0).getMkOrder().getModelKey()
								.getValidFromMonth());
		bunka = 0;
		row = sheet.createRow(radka++);
		row.createCell(bunka++).setCellValue("Valid to:");
		row.createCell(bunka++)
				.setCellValue(
						b.get(0).getMkOrder().getModelKey().getValidToYear()
								+ "/"
								+ b.get(0).getMkOrder().getModelKey()
										.getValidToMonth());
		bunka = 0;
		row = sheet.createRow(radka++);
		row.createCell(bunka++).setCellValue("Options:");
		row.createCell(bunka++).setCellValue(
				b.get(0).getMkOrder().getModelKey().getOptions());
		bunka = 0;
		row = sheet.createRow(radka++);
		row.createCell(bunka++).setCellValue("Comment:");
		row.createCell(bunka++).setCellValue(
				b.get(0).getMkOrder().getModelKey().getCommentUser());

		bunka = 0;
		row = sheet.createRow(radka++);
		row = sheet.createRow(radka++);
		row.createCell(bunka++).setCellValue("ORDER");
		bunka = 0;
		row = sheet.createRow(radka++);
		row.createCell(bunka++).setCellValue("IFA:");
		row.createCell(bunka++).setCellValue(
				b.get(0).getMkOrder().getOrder().getKifa());
		bunka = 0;
		row = sheet.createRow(radka++);
		row.createCell(bunka++).setCellValue("KNR:");
		row.createCell(bunka++).setCellValue(
				b.get(0).getMkOrder().getOrder().getKnr());
		bunka = 0;
		row = sheet.createRow(radka++);
		row.createCell(bunka++).setCellValue("VIN:");
		row.createCell(bunka++).setCellValue(
				b.get(0).getMkOrder().getOrder().getVin());
		bunka = 0;
		row = sheet.createRow(radka++);
		row.createCell(bunka++).setCellValue("Model key:");
		row.createCell(bunka++).setCellValue(
				b.get(0).getMkOrder().getOrder().getModelKey());
		bunka = 0;
		row = sheet.createRow(radka++);
		row.createCell(bunka++).setCellValue("Model key version:");
		row.createCell(bunka++).setCellValue(
				b.get(0).getMkOrder().getOrder().getModelKeyVersion());
		bunka = 0;
		row = sheet.createRow(radka++);
		row.createCell(bunka++).setCellValue("Colour:");
		row.createCell(bunka++).setCellValue(
				b.get(0).getMkOrder().getOrder().getColour());
		bunka = 0;
		row = sheet.createRow(radka++);
		row.createCell(bunka++).setCellValue("Model year:");
		row.createCell(bunka++).setCellValue(
				b.get(0).getMkOrder().getOrder().getModelYear());
		bunka = 0;
		row = sheet.createRow(radka++);
		row.createCell(bunka++).setCellValue("Delivery program:");
		row.createCell(bunka++).setCellValue(
				b.get(0).getMkOrder().getOrder().getDeliveryProgram());
		bunka = 0;
		row = sheet.createRow(radka++);
		row.createCell(bunka++).setCellValue("Options:");
		row.createCell(bunka++).setCellValue(
				b.get(0).getMkOrder().getOrder().getOptions());
		bunka = 0;
		row = sheet.createRow(radka++);
		row.createCell(bunka++).setCellValue("PR descriptions:");
		row.createCell(bunka++).setCellValue(
				b.get(0).getMkOrder().getOrder().getPrDescription());
		bunka = 0;
		row = sheet.createRow(radka++);
		row.createCell(bunka++).setCellValue("Production date:");
		cell = row.createCell(bunka++);
		cell.setCellValue(b.get(0).getMkOrder().getOrder().getProductionDate());
		cell.setCellStyle(dateFormat);
		bunka = 0;
		row = sheet.createRow(radka++);
		row.createCell(bunka++).setCellValue("Exchange rate:");
		row.createCell(bunka++).setCellValue(
				ex.getRate() + " " + ex.getCurrency());

		OutputStream os = res.getOutputStream();
		workbook.write(os);
		os.close();

	}

	public void bomComparison(HttpServletResponse res, List<Bom> b1,
			List<Bom> b2, Boolean permPlantWrite, Boolean permBrandWrite,
			ExchangeRate ex1, ExchangeRate ex2) throws IOException {
		log.debug("###\t bomComparison( size1:" + b1.size() + "("
				+ ex1.getRate() + ")" + ",  size2:" + b2.size() + "("
				+ ex2.getRate() + ")" + ")");

		res.setContentType("application/ms-excel");
		res.setHeader("Content-Disposition","attachment; filename=\"COCAFOP_BoM_detail.xls\"");
		res.setHeader("Pragma", "public");
		res.setHeader("Cache-Control", "max-age=0");

		Workbook workbook = new HSSFWorkbook();
		Sheet sheet = workbook.createSheet("BoM - comparison");

		Cell cell = null;
		Row row = null;
		int radka = 0;
		int bunka = 0;

		CellStyle dateFormat = workbook.createCellStyle();
		dateFormat.setDataFormat(workbook.createDataFormat().getFormat(
				"yyyy-mm-dd"));
		CellStyle floatFormat5 = workbook.createCellStyle();
		floatFormat5.setDataFormat(workbook.createDataFormat().getFormat(
				"##############0.00000"));
		CellStyle floatFormat2 = workbook.createCellStyle();
		floatFormat2.setDataFormat(workbook.createDataFormat().getFormat(
				"##############0.00"));

		if (b1.size() == 0 || b2.size() == 0) {
			log.debug("###\t bomComparison - jeden z BOMu je prazdny !!!!");
			row = sheet.createRow(radka++);
			row.createCell(bunka++).setCellValue(
					"One of BOMs are empty, there is nothing for comparison.");
		} else {

			row = sheet.createRow(radka++);
			row.createCell(bunka++).setCellValue("Part Number");
			row.createCell(bunka++).setCellValue("Part No.REVERSE");
			row.createCell(bunka++).setCellValue("Part Description");
			row.createCell(bunka++).setCellValue("Type");
			row.createCell(bunka++).setCellValue("Quantity");
			row.createCell(bunka++).setCellValue("UoM");
			row.createCell(bunka++).setCellValue("Price");
			row.createCell(bunka++).setCellValue("Q * P");
			row.createCell(bunka++).setCellValue("Quantity");
			row.createCell(bunka++).setCellValue("UoM");
			row.createCell(bunka++).setCellValue("Price");
			row.createCell(bunka++).setCellValue("Q * P");

			Boolean nalezenDil;
			bunka = 0;
			for (Bom a : b1) {
				nalezenDil = false;
				Float cena1 = null;
				Float mnoz1 = null;
				String uom1 = null;
				Float cenaMnoz1 = null;
				if (permPlantWrite) {
					cena1 = (a.getPricePlantNew() == null ? a.getPricePlant()
							: a.getPricePlantNew());
					mnoz1 = (a.getQuantityPlant() == null ? a.getQuantity() : a
							.getQuantityPlant());
					uom1 = (a.getUomPlant() == null ? a.getUom() : a
							.getUomPlant());
					cenaMnoz1 = n(cena1) * n(mnoz1);
				} else if (permBrandWrite) {
					cena1 = (a.getPriceBrandNew() == null ? a.getPriceBrand()
							: a.getPriceBrandNew());
					mnoz1 = (a.getQuantityBrand() == null ? a.getQuantity() : a
							.getQuantityBrand());
					uom1 = (a.getUomBrand() == null ? a.getUom() : a
							.getUomBrand());
					cenaMnoz1 = n(cena1) * n(mnoz1);
				}
				for (Bom b : b2) {
					Float cena2 = null;
					Float mnoz2 = null;
					String uom2 = null;
					Float cenaMnoz2 = null;
					if (permPlantWrite) {
						cena2 = (b.getPricePlantNew() == null ? b
								.getPricePlant() : b.getPricePlantNew());
						mnoz2 = (b.getQuantityPlant() == null ? b.getQuantity()
								: b.getQuantityPlant());
						uom2 = (b.getUomPlant() == null ? b.getUom() : b
								.getUomPlant());
						cenaMnoz2 = n(cena2) * n(mnoz2);
					}
					if (permBrandWrite) {
						cena2 = (b.getPriceBrandNew() == null ? b
								.getPriceBrand() : b.getPriceBrandNew());
						mnoz2 = (b.getQuantityBrand() == null ? b.getQuantity()
								: b.getQuantityBrand());
						uom2 = (b.getUomBrand() == null ? b.getUom() : b
								.getUomBrand());
						cenaMnoz2 = n(cena2) * n(mnoz2);
					}
					if (a.getPartNumber().startsWith(b.getPartNumber())
							&& cenaMnoz1.toString().startsWith(
									cenaMnoz2.toString())) {
						nalezenDil = true;
						// log.trace("###  KOMPLETNI SHODA  ###:\t "+a.getPartNumber()+"\t"+cena1+"\t"+mnoz1+"\t\t\t"+b.getPartNumber()+"\t"+cena2+"\t"+mnoz2);
					} else if (a.getPartNumber().startsWith(b.getPartNumber())
							&& cenaMnoz1 != cenaMnoz2) {
						nalezenDil = true;
						// log.trace("###  ROZDIL  ###:\t "+a.getPartNumber()+"\t"+cena1+"\t"+mnoz1+"\t\t\t"+b.getPartNumber()+"\t"+cena2+"\t"+mnoz2);
						row = sheet.createRow(radka++);
						bunka = 0;
						row.createCell(bunka++).setCellValue(a.getPartNumber());
						row.createCell(bunka++).setCellValue(
								a.getPartNumber().toString().substring(4)
										+ a.getPartNumber().toString()
												.substring(0, 4));
						row.createCell(bunka++)
								.setCellValue(a.getPartDescEng());
						row.createCell(bunka++).setCellValue(a.getTyp());
						row.createCell(bunka++).setCellValue(mnoz1);
						row.createCell(bunka++).setCellValue(uom1);
						row.createCell(bunka++).setCellValue(cena1);
						row.createCell(bunka++).setCellValue(cenaMnoz1);
						row.createCell(bunka++).setCellValue(mnoz2);
						row.createCell(bunka++).setCellValue(uom2);
						row.createCell(bunka++).setCellValue(cena2);
						row.createCell(bunka++).setCellValue(cenaMnoz2);
					}
				}
				if (!nalezenDil) {
					// log.trace("###  Dil jen v 1  ###:\t "+a.getPartNumber()+"\t"+cena1+"\t"+mnoz1);
					row = sheet.createRow(radka++);
					bunka = 0;
					row.createCell(bunka++).setCellValue(a.getPartNumber());
					row.createCell(bunka++).setCellValue(
							a.getPartNumber().toString().substring(4)
									+ a.getPartNumber().toString()
											.substring(0, 4));
					row.createCell(bunka++).setCellValue(a.getPartDescEng());
					row.createCell(bunka++).setCellValue(a.getTyp());
					row.createCell(bunka++).setCellValue(mnoz1);
					row.createCell(bunka++).setCellValue(uom1);
					row.createCell(bunka++).setCellValue(cena1);
					row.createCell(bunka++).setCellValue(cenaMnoz1);
				}
			}

			for (Bom b : b2) {
				nalezenDil = false;
				Float cena2 = null;
				Float mnoz2 = null;
				String uom2 = null;
				Float cenaMnoz2 = null;
				if (permPlantWrite) {
					cena2 = (b.getPricePlantNew() == null ? b.getPricePlant()
							: b.getPricePlantNew());
					mnoz2 = (b.getQuantityPlant() == null ? b.getQuantity() : b
							.getQuantityPlant());
					uom2 = (b.getUomPlant() == null ? b.getUom() : b
							.getUomPlant());
					cenaMnoz2 = n(cena2) * n(mnoz2);
				}
				if (permBrandWrite) {
					cena2 = (b.getPriceBrandNew() == null ? b.getPriceBrand()
							: b.getPriceBrandNew());
					mnoz2 = (b.getQuantityBrand() == null ? b.getQuantity() : b
							.getQuantityBrand());
					uom2 = (b.getUomBrand() == null ? b.getUom() : b
							.getUomBrand());
					cenaMnoz2 = n(cena2) * n(mnoz2);
				}
				for (Bom a : b1) {
					if (b.getPartNumber().startsWith(a.getPartNumber())) {
						nalezenDil = true;
					}
				}
				if (!nalezenDil) {
					// log.trace("###  Dil jen v 2  ###:\t "+b.getPartNumber()+"\t"+cena2+"\t"+mnoz2);
					row = sheet.createRow(radka++);
					bunka = 0;
					row.createCell(bunka++).setCellValue(b.getPartNumber());
					row.createCell(bunka++).setCellValue(
							b.getPartNumber().toString().substring(4)
									+ b.getPartNumber().toString()
											.substring(0, 4));
					row.createCell(bunka++).setCellValue(b.getPartDescEng());
					row.createCell(bunka++).setCellValue(b.getTyp());
					row.createCell(bunka++).setCellValue("");
					row.createCell(bunka++).setCellValue("");
					row.createCell(bunka++).setCellValue("");
					row.createCell(bunka++).setCellValue("");
					row.createCell(bunka++).setCellValue(mnoz2);
					row.createCell(bunka++).setCellValue(uom2);
					row.createCell(bunka++).setCellValue(cena2);
					row.createCell(bunka++).setCellValue(cenaMnoz2);
				}
			}

			// DalsÌ list

			sheet = workbook.createSheet("Current model - detail");
			radka = 0;

			bunka = 0;
			row = sheet.createRow(radka++);
			row.createCell(bunka++).setCellValue("Permission for:");
			if (permPlantWrite) {
				row.createCell(bunka++).setCellValue("PLANT  (write)");
			}
			if (permBrandWrite) {
				row.createCell(bunka++).setCellValue("BRAND  (write)");
			}
			bunka = 0;
			row = sheet.createRow(radka++);
			row.createCell(bunka++).setCellValue("MODEL KEY");
			bunka = 0;
			row = sheet.createRow(radka++);
			row.createCell(bunka++).setCellValue("Plant:");
			row.createCell(bunka++).setCellValue(
					b1.get(0).getMkOrder().getModelKey().getBrand().getPlant()
							.getId()
							+ " - "
							+ b1.get(0).getMkOrder().getModelKey().getBrand()
									.getPlant().getPlantName());
			bunka = 0;
			row = sheet.createRow(radka++);
			row.createCell(bunka++).setCellValue("Brand:");
			row.createCell(bunka++).setCellValue(
					b1.get(0).getMkOrder().getModelKey().getBrand()
							.getBrandMark()
							+ " - "
							+ b1.get(0).getMkOrder().getModelKey().getBrand()
									.getBrandName());
			bunka = 0;
			row = sheet.createRow(radka++);
			row.createCell(bunka++).setCellValue("Model number:");
			row.createCell(bunka++).setCellValue(
					b1.get(0).getMkOrder().getModelKey().getModelNumber());
			bunka = 0;
			row = sheet.createRow(radka++);
			row.createCell(bunka++).setCellValue("Year - month");
			row.createCell(bunka++).setCellValue(
					b1.get(0).getMkOrder().getModelKey().getRok() + " - "
							+ b1.get(0).getMkOrder().getMesic());
			bunka = 0;
			row = sheet.createRow(radka++);
			row.createCell(bunka++).setCellValue("Type:");
			row.createCell(bunka++).setCellValue(
					b1.get(0).getMkOrder().getModelKey().getTyp());
			bunka = 0;
			row = sheet.createRow(radka++);
			row.createCell(bunka++).setCellValue("Model key:");
			row.createCell(bunka++).setCellValue(
					b1.get(0).getMkOrder().getModelKey().getModelKey());
			bunka = 0;
			row = sheet.createRow(radka++);
			row.createCell(bunka++).setCellValue("Colour:");
			row.createCell(bunka++).setCellValue(
					b1.get(0).getMkOrder().getModelKey().getColour());
			bunka = 0;
			row = sheet.createRow(radka++);
			row.createCell(bunka++).setCellValue("Valid from:");
			row.createCell(bunka++).setCellValue(
					b1.get(0).getMkOrder().getModelKey().getValidFromYear()
							+ "/"
							+ b1.get(0).getMkOrder().getModelKey()
									.getValidFromMonth());
			bunka = 0;
			row = sheet.createRow(radka++);
			row.createCell(bunka++).setCellValue("Valid to:");
			row.createCell(bunka++).setCellValue(
					b1.get(0).getMkOrder().getModelKey().getValidToYear()
							+ "/"
							+ b1.get(0).getMkOrder().getModelKey()
									.getValidToMonth());
			bunka = 0;
			row = sheet.createRow(radka++);
			row.createCell(bunka++).setCellValue("Options:");
			row.createCell(bunka++).setCellValue(
					b1.get(0).getMkOrder().getModelKey().getOptions());
			bunka = 0;
			row = sheet.createRow(radka++);
			row.createCell(bunka++).setCellValue("Comment:");
			row.createCell(bunka++).setCellValue(
					b1.get(0).getMkOrder().getModelKey().getCommentUser());

			bunka = 0;
			row = sheet.createRow(radka++);
			row = sheet.createRow(radka++);
			row.createCell(bunka++).setCellValue("ORDER");
			bunka = 0;
			row = sheet.createRow(radka++);
			row.createCell(bunka++).setCellValue("IFA:");
			row.createCell(bunka++).setCellValue(
					b1.get(0).getMkOrder().getOrder().getKifa());
			bunka = 0;
			row = sheet.createRow(radka++);
			row.createCell(bunka++).setCellValue("KNR:");
			row.createCell(bunka++).setCellValue(
					b1.get(0).getMkOrder().getOrder().getKnr());
			bunka = 0;
			row = sheet.createRow(radka++);
			row.createCell(bunka++).setCellValue("VIN:");
			row.createCell(bunka++).setCellValue(
					b1.get(0).getMkOrder().getOrder().getVin());
			bunka = 0;
			row = sheet.createRow(radka++);
			row.createCell(bunka++).setCellValue("Model key:");
			row.createCell(bunka++).setCellValue(
					b1.get(0).getMkOrder().getOrder().getModelKey());
			bunka = 0;
			row = sheet.createRow(radka++);
			row.createCell(bunka++).setCellValue("Model key version:");
			row.createCell(bunka++).setCellValue(
					b1.get(0).getMkOrder().getOrder().getModelKeyVersion());
			bunka = 0;
			row = sheet.createRow(radka++);
			row.createCell(bunka++).setCellValue("Colour:");
			row.createCell(bunka++).setCellValue(
					b1.get(0).getMkOrder().getOrder().getColour());
			bunka = 0;
			row = sheet.createRow(radka++);
			row.createCell(bunka++).setCellValue("Model year:");
			row.createCell(bunka++).setCellValue(
					b1.get(0).getMkOrder().getOrder().getModelYear());
			bunka = 0;
			row = sheet.createRow(radka++);
			row.createCell(bunka++).setCellValue("Delivery program:");
			row.createCell(bunka++).setCellValue(
					b1.get(0).getMkOrder().getOrder().getDeliveryProgram());
			bunka = 0;
			row = sheet.createRow(radka++);
			row.createCell(bunka++).setCellValue("Options:");
			row.createCell(bunka++).setCellValue(
					b1.get(0).getMkOrder().getOrder().getOptions());
			bunka = 0;
			row = sheet.createRow(radka++);
			row.createCell(bunka++).setCellValue("PR descriptions:");
			row.createCell(bunka++).setCellValue(
					b1.get(0).getMkOrder().getOrder().getPrDescription());
			bunka = 0;
			row = sheet.createRow(radka++);
			row.createCell(bunka++).setCellValue("Production date:");
			cell = row.createCell(bunka++);
			cell.setCellValue(b1.get(0).getMkOrder().getOrder()
					.getProductionDate());
			cell.setCellStyle(dateFormat);
			bunka = 0;
			row = sheet.createRow(radka++);
			row.createCell(bunka++).setCellValue("Exchange rate:");
			row.createCell(bunka++).setCellValue(
					ex1.getRate() + " " + ex1.getCurrency());

			// DalsÌ list
			sheet = workbook.createSheet("2nd model - detail");
			radka = 0;

			bunka = 0;
			row = sheet.createRow(radka++);
			row.createCell(bunka++).setCellValue("Permission for:");
			if (permPlantWrite) {
				row.createCell(bunka++).setCellValue("PLANT  (write)");
			}
			if (permBrandWrite) {
				row.createCell(bunka++).setCellValue("BRAND  (write)");
			}
			bunka = 0;
			row = sheet.createRow(radka++);
			row.createCell(bunka++).setCellValue("MODEL KEY");
			bunka = 0;
			row = sheet.createRow(radka++);
			row.createCell(bunka++).setCellValue("Plant:");
			row.createCell(bunka++).setCellValue(
					b2.get(0).getMkOrder().getModelKey().getBrand().getPlant()
							.getId()
							+ " - "
							+ b2.get(0).getMkOrder().getModelKey().getBrand()
									.getPlant().getPlantName());
			bunka = 0;
			row = sheet.createRow(radka++);
			row.createCell(bunka++).setCellValue("Brand:");
			row.createCell(bunka++).setCellValue(
					b2.get(0).getMkOrder().getModelKey().getBrand()
							.getBrandMark()
							+ " - "
							+ b2.get(0).getMkOrder().getModelKey().getBrand()
									.getBrandName());
			bunka = 0;
			row = sheet.createRow(radka++);
			row.createCell(bunka++).setCellValue("Model number:");
			row.createCell(bunka++).setCellValue(
					b2.get(0).getMkOrder().getModelKey().getModelNumber());
			bunka = 0;
			row = sheet.createRow(radka++);
			row.createCell(bunka++).setCellValue("Year - month");
			row.createCell(bunka++).setCellValue(
					b2.get(0).getMkOrder().getModelKey().getRok() + " - "
							+ b2.get(0).getMkOrder().getMesic());
			bunka = 0;
			row = sheet.createRow(radka++);
			row.createCell(bunka++).setCellValue("Type:");
			row.createCell(bunka++).setCellValue(
					b2.get(0).getMkOrder().getModelKey().getTyp());
			bunka = 0;
			row = sheet.createRow(radka++);
			row.createCell(bunka++).setCellValue("Model key:");
			row.createCell(bunka++).setCellValue(
					b2.get(0).getMkOrder().getModelKey().getModelKey());
			bunka = 0;
			row = sheet.createRow(radka++);
			row.createCell(bunka++).setCellValue("Colour:");
			row.createCell(bunka++).setCellValue(
					b2.get(0).getMkOrder().getModelKey().getColour());
			bunka = 0;
			row = sheet.createRow(radka++);
			row.createCell(bunka++).setCellValue("Valid from:");
			row.createCell(bunka++).setCellValue(
					b2.get(0).getMkOrder().getModelKey().getValidFromYear()
							+ "/"
							+ b2.get(0).getMkOrder().getModelKey()
									.getValidFromMonth());
			bunka = 0;
			row = sheet.createRow(radka++);
			row.createCell(bunka++).setCellValue("Valid to:");
			row.createCell(bunka++).setCellValue(
					b2.get(0).getMkOrder().getModelKey().getValidToYear()
							+ "/"
							+ b2.get(0).getMkOrder().getModelKey()
									.getValidToMonth());
			bunka = 0;
			row = sheet.createRow(radka++);
			row.createCell(bunka++).setCellValue("Options:");
			row.createCell(bunka++).setCellValue(
					b2.get(0).getMkOrder().getModelKey().getOptions());
			bunka = 0;
			row = sheet.createRow(radka++);
			row.createCell(bunka++).setCellValue("Comment:");
			row.createCell(bunka++).setCellValue(
					b2.get(0).getMkOrder().getModelKey().getCommentUser());

			bunka = 0;
			row = sheet.createRow(radka++);
			row = sheet.createRow(radka++);
			row.createCell(bunka++).setCellValue("ORDER");
			bunka = 0;
			row = sheet.createRow(radka++);
			row.createCell(bunka++).setCellValue("IFA:");
			row.createCell(bunka++).setCellValue(
					b2.get(0).getMkOrder().getOrder().getKifa());
			bunka = 0;
			row = sheet.createRow(radka++);
			row.createCell(bunka++).setCellValue("KNR:");
			row.createCell(bunka++).setCellValue(
					b2.get(0).getMkOrder().getOrder().getKnr());
			bunka = 0;
			row = sheet.createRow(radka++);
			row.createCell(bunka++).setCellValue("VIN:");
			row.createCell(bunka++).setCellValue(
					b2.get(0).getMkOrder().getOrder().getVin());
			bunka = 0;
			row = sheet.createRow(radka++);
			row.createCell(bunka++).setCellValue("Model key:");
			row.createCell(bunka++).setCellValue(
					b2.get(0).getMkOrder().getOrder().getModelKey());
			bunka = 0;
			row = sheet.createRow(radka++);
			row.createCell(bunka++).setCellValue("Model key version:");
			row.createCell(bunka++).setCellValue(
					b2.get(0).getMkOrder().getOrder().getModelKeyVersion());
			bunka = 0;
			row = sheet.createRow(radka++);
			row.createCell(bunka++).setCellValue("Colour:");
			row.createCell(bunka++).setCellValue(
					b2.get(0).getMkOrder().getOrder().getColour());
			bunka = 0;
			row = sheet.createRow(radka++);
			row.createCell(bunka++).setCellValue("Model year:");
			row.createCell(bunka++).setCellValue(
					b2.get(0).getMkOrder().getOrder().getModelYear());
			bunka = 0;
			row = sheet.createRow(radka++);
			row.createCell(bunka++).setCellValue("Delivery program:");
			row.createCell(bunka++).setCellValue(
					b2.get(0).getMkOrder().getOrder().getDeliveryProgram());
			bunka = 0;
			row = sheet.createRow(radka++);
			row.createCell(bunka++).setCellValue("Options:");
			row.createCell(bunka++).setCellValue(
					b2.get(0).getMkOrder().getOrder().getOptions());
			bunka = 0;
			row = sheet.createRow(radka++);
			row.createCell(bunka++).setCellValue("PR descriptions:");
			row.createCell(bunka++).setCellValue(
					b2.get(0).getMkOrder().getOrder().getPrDescription());
			bunka = 0;
			row = sheet.createRow(radka++);
			row.createCell(bunka++).setCellValue("Production date:");
			cell = row.createCell(bunka++);
			cell.setCellValue(b2.get(0).getMkOrder().getOrder()
					.getProductionDate());
			cell.setCellStyle(dateFormat);
			bunka = 0;
			row = sheet.createRow(radka++);
			row.createCell(bunka++).setCellValue("Exchange rate:");
			row.createCell(bunka++).setCellValue(
					ex2.getRate() + " " + ex2.getCurrency());
		}
		OutputStream os = res.getOutputStream();
		workbook.write(os);
		os.close();

	}

	// ###################################################################################################
	// Kdyz bude float 0 nebo NULL, tak at je to String="" (prazdna bunka v xls)
	Object n0(Float f) {
		if (f == null || f == 0f)
			return "";
		return f;
	}

	Float n(Float f) {
		return f == null ? 0f : f;
	}

	String n(String s) {
		return s == null ? "" : s;
	}

	public static void main(String[] args) throws IOException {

		Workbook workbook = new HSSFWorkbook();

		Sheet sheet = workbook.createSheet("Sheet one");
		Row row = sheet.createRow(0);
		row.createCell(1).setCellValue("Value 2");
		row.createCell(2).setCellValue("Value 3");

		FileOutputStream outputStream = new FileOutputStream("Bom_detail.xls");
		workbook.write(outputStream);
		outputStream.close();
	}

}
