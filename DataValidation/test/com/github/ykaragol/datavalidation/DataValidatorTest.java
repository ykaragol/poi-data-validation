package com.github.ykaragol.datavalidation;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.util.HashSet;
import java.util.List;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.poifs.crypt.Decryptor;
import org.apache.poi.poifs.crypt.EncryptionInfo;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Before;
import org.junit.Test;

/*
 * This is not a unit test code.
 * This is my way of testing code over poi. 
 */
public class DataValidatorTest {

	public static final String PATH = "./test-files/";
	public static final String FILE_NAME = "DataValidationTest.xlsx";
	
	public DataValidator dataValidator;

	@Before
	public void setup() {
		dataValidator = new DataValidator();
	}

	@Test
	public void testDataValidation() {
		InputStream is = null;
		try {
			File file = new File(PATH + FILE_NAME);
			is = new FileInputStream(file);

			XSSFWorkbook wb = openWorkbook(is);
			XSSFSheet sheet = wb.getSheetAt(0);

			List<ValidationResult> results = dataValidator.validateSheet(sheet);
			HashSet<String> cells = new HashSet<String>();
			for (ValidationResult result : results) {
				cells.add(result.getCellName());
			}
			assertTrue(cells.contains("B2"));
			assertTrue(cells.contains("B3"));
			assertTrue(cells.contains("B4"));
			assertTrue(cells.contains("B5"));
			assertTrue(cells.contains("B6"));
			assertTrue(cells.contains("B7"));
			assertTrue(cells.contains("B8"));
			assertTrue(cells.contains("B9"));
			assertTrue(cells.contains("B10"));
			assertTrue(cells.contains("B11"));
			assertTrue(cells.contains("B12"));
			assertTrue(cells.contains("B13"));
			assertTrue(cells.contains("B14"));
			assertTrue(cells.contains("B15"));
			assertTrue(cells.contains("B16"));
			assertTrue(cells.contains("B17"));
			assertTrue(cells.contains("B18"));
			assertTrue(cells.contains("B19"));
	//		assertTrue(cells.contains("B20"));
			assertTrue(cells.contains("B21"));
			assertTrue(cells.contains("B22"));
			assertTrue(cells.contains("B23"));
			assertTrue(cells.contains("B24"));
			assertTrue(cells.contains("B25"));
			assertTrue(cells.contains("B26"));
			assertTrue(cells.contains("B27"));
			assertTrue(cells.contains("B28"));

		} catch (FileNotFoundException e) {
			fail("FileNotFound");
		} finally {
			closeSilently(is);
		}
	}

	public static XSSFWorkbook openWorkbook(InputStream is) {
		Workbook wb;
		try {
			wb = WorkbookFactory.create(is);
			if (wb instanceof XSSFWorkbook) {
				return (XSSFWorkbook) wb;
			}
			throw new RuntimeException();
		} catch (InvalidFormatException e) {
			throw new RuntimeException();
		} catch (IOException e) {
			throw new RuntimeException();
		}
	}

	public static InputStream convertEncryptedXlsx2DecryptedXlsx(InputStream is)
			throws IOException {
		POIFSFileSystem fs = new POIFSFileSystem(is);
		EncryptionInfo info = new EncryptionInfo(fs);
		Decryptor d = Decryptor.getInstance(info);
		try {
			d.verifyPassword(Decryptor.DEFAULT_PASSWORD);
			InputStream dataStream = d.getDataStream(fs);
			return dataStream;
		} catch (GeneralSecurityException e) {
			throw new IOException(e);
		}
	}

	private void closeSilently(InputStream is) {
		if (is != null) {
			try {
				is.close();
			} catch (IOException e) {
				/* silent */
			}
		}
	}

}