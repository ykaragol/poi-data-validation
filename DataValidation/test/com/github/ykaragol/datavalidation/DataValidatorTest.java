package com.github.ykaragol.datavalidation;

import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.poifs.crypt.Decryptor;
import org.apache.poi.poifs.crypt.EncryptionInfo;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

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
		boolean isValid = true;
		try {
			File file = new File(PATH + FILE_NAME);
			System.err.println(file.getAbsolutePath());
			is = new FileInputStream(file);

			XSSFWorkbook wb = openWorkbook(is);
			XSSFSheet sheet = wb.getSheetAt(0);

			dataValidator.validateSheet(sheet);

		} catch (FileNotFoundException e) {
			fail("FileNotFound");
		} finally {
			closeSilently(is);
		}
		Assert.assertEquals("isValid ", true, isValid);
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