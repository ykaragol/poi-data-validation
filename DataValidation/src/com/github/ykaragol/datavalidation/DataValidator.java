package com.github.ykaragol.datavalidation;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataValidationConstraint;
import org.apache.poi.ss.usermodel.DataValidationConstraint.OperatorType;
import org.apache.poi.ss.usermodel.DataValidationConstraint.ValidationType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.AreaReference;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFDataValidation;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import com.github.ykaragol.datavalidation.core.Operator;
import com.github.ykaragol.datavalidation.core.Validator;
import com.github.ykaragol.datavalidation.operators.BetweenOperator;
import com.github.ykaragol.datavalidation.operators.EqualOperator;
import com.github.ykaragol.datavalidation.operators.GreaterOrEqualOperator;
import com.github.ykaragol.datavalidation.operators.GreaterThanOperator;
import com.github.ykaragol.datavalidation.operators.LessOrEqualOperator;
import com.github.ykaragol.datavalidation.operators.LessThanOperator;
import com.github.ykaragol.datavalidation.operators.NotBetweenOperator;
import com.github.ykaragol.datavalidation.operators.NotEqualOperator;
import com.github.ykaragol.datavalidation.utils.CellValueParser;
import com.github.ykaragol.datavalidation.validator.DateValidator;
import com.github.ykaragol.datavalidation.validator.DecimalValidator;
import com.github.ykaragol.datavalidation.validator.FormulaValidator;
import com.github.ykaragol.datavalidation.validator.ListValidator;
import com.github.ykaragol.datavalidation.validator.NullValidator;
import com.github.ykaragol.datavalidation.validator.NumericValidator;
import com.github.ykaragol.datavalidation.validator.TextLengthValidator;
import com.github.ykaragol.datavalidation.validator.TimeValidator;

public class DataValidator {

	public List<ValidationResult> validateSheet(XSSFSheet sheet) {
		List<ValidationResult> results = new LinkedList<ValidationResult>();

		List<XSSFDataValidation> dataValidations = sheet.getDataValidations();
		for (XSSFDataValidation xssfDataValidation : dataValidations) {
			DataValidationConstraint validationConstraint = xssfDataValidation.getValidationConstraint();
			CellRangeAddressList regions = xssfDataValidation.getRegions();
			CellRangeAddress[] cellRangeAddresses = regions.getCellRangeAddresses();
			Validator validator = buildValidator(sheet, validationConstraint);
			for (CellRangeAddress cellRangeAddress : cellRangeAddresses) {
				int firstRow = Math.max(cellRangeAddress.getFirstRow(), sheet.getFirstRowNum());
				int lastRow = Math.min(cellRangeAddress.getLastRow(), sheet.getLastRowNum());

				for (int i = firstRow; i <= lastRow; i++) {
					XSSFRow row = sheet.getRow(i);
					if (row == null || row.getFirstCellNum() < 0) {
						continue;
					}

					int firstColumn = Math.max(cellRangeAddress.getFirstColumn(), row.getFirstCellNum());
					int lastColumn = Math.min(cellRangeAddress.getLastColumn(), row.getLastCellNum());

					for (int j = firstColumn; j <= lastColumn; j++) {
						XSSFCell cell = row.getCell(j);
						if (cell == null) {
							continue;
						}
						boolean inRange = cellRangeAddress.isInRange(cell.getRowIndex(), cell.getColumnIndex());
						if (inRange) {
							ValidationResult result = validator.validate(cell);
							if (result != null) {
								results.add(result);
							}
						}
					}
				}
			}
		}
		return results;
		/*
		 * TODO think about splitting this function into 2 parts: 1.
		 * getAllValidators 2. validate with all validators
		 */
	}

	private Validator buildValidator(XSSFSheet sheet, DataValidationConstraint validationConstraint) {
		int validationType = validationConstraint.getValidationType();

		Validator validator;
		switch (validationType) {
		case ValidationType.ANY:
			validator = new NullValidator();
			break;
		case ValidationType.DATE:
			validator = new DateValidator(buildOperator(sheet, validationConstraint));
			break;
		case ValidationType.DECIMAL:
			validator = new DecimalValidator(buildOperator(sheet, validationConstraint));
			break;
		case ValidationType.FORMULA:
			validator = new FormulaValidator(null);// TODO how to do?
			break;
		case ValidationType.INTEGER:
			validator = new NumericValidator(buildOperator(sheet, validationConstraint));
			break;
		case ValidationType.LIST:
			validator = new ListValidator(buildReferenceList(sheet, validationConstraint));
			break;
		case ValidationType.TEXT_LENGTH:
			validator = new TextLengthValidator(buildOperator(sheet, validationConstraint));
			break;
		case ValidationType.TIME:
			validator = new TimeValidator(buildOperator(sheet, validationConstraint));
			break;
		default:
			throw new UnsupportedOperationException("Validation Type is not supported: " + validationType);
		}
		return validator;
	}

	private Operator buildOperator(XSSFSheet sheet, DataValidationConstraint validationConstraint) {
		int operatorType = validationConstraint.getOperator();
		String formula1 = validationConstraint.getFormula1();
		String formula2 = validationConstraint.getFormula2();

		Operator operator;
		switch (operatorType) {
		case OperatorType.BETWEEN:
			operator = new BetweenOperator(getSingleValue(sheet, formula1), getSingleValue(sheet, formula2));
			break;
		case OperatorType.NOT_BETWEEN:
			operator = new NotBetweenOperator(getSingleValue(sheet, formula1), getSingleValue(sheet, formula2));
			break;
		case OperatorType.EQUAL:
			operator = new EqualOperator(getSingleValue(sheet, formula1));
			break;
		case OperatorType.NOT_EQUAL:
			operator = new NotEqualOperator(getSingleValue(sheet, formula1));
			break;
		case OperatorType.GREATER_OR_EQUAL:
			operator = new GreaterOrEqualOperator(getSingleValue(sheet, formula1));
			break;
		case OperatorType.GREATER_THAN:
			operator = new GreaterThanOperator(getSingleValue(sheet, formula1));
			break;
		case OperatorType.LESS_OR_EQUAL:
			operator = new LessOrEqualOperator(getSingleValue(sheet, formula1));
			break;
		case OperatorType.LESS_THAN:
			operator = new LessThanOperator(getSingleValue(sheet, formula1));
			break;
		// OperatorType.IGNORED = Operator.BETWEEN
		default:
			throw new UnsupportedOperationException("Operation Type is not supported: " + operatorType);
		}
		return operator;
	}

	private Double getSingleValue(XSSFSheet sheet, String formula) {
		try {
			return Double.parseDouble(formula);
		} catch (NumberFormatException e) {
			CellReference cellReference = new CellReference(formula);
			if (cellReference.getSheetName() != null) {
				sheet = sheet.getWorkbook().getSheet(cellReference.getSheetName());
			}
			XSSFRow row = sheet.getRow(cellReference.getRow());
			XSSFCell cell = row.getCell(cellReference.getCol());
			return cell.getNumericCellValue();
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private List<Object> buildReferenceList(XSSFSheet sheet, DataValidationConstraint validationConstraint) {
		List listValues;
		String formula1 = validationConstraint.getFormula1();
		if (formula1.contains("$")) {
			listValues = getReferenceList(sheet, validationConstraint);
		} else {
			listValues = parseListValues(formula1);
		}
		return listValues;
	}

	private List<Object> getReferenceList(XSSFSheet sheet, DataValidationConstraint validationConstraint) {
		List<Object> references = new LinkedList<>();

		AreaReference areaRef = new AreaReference(validationConstraint.getFormula1());
		CellReference[] cellRefs = areaRef.getAllReferencedCells();
		for (CellReference cellRef : cellRefs) {
			XSSFSheet referenceListSheet;
			if (cellRef.getSheetName() != null) {
				referenceListSheet = sheet.getWorkbook().getSheet(cellRef.getSheetName());
			} else {
				referenceListSheet = sheet;
			}
			Row row = referenceListSheet.getRow(cellRef.getRow());
			if (row != null) {
				Cell cell = row.getCell(cellRef.getCol());
				if (cell != null) {
					Object cellValue = CellValueParser.getCellValue(cell);
					references.add(cellValue);
				}
			}
		}
		return references;
	}

	private List<String> parseListValues(String listOfValues) {
		if (listOfValues.startsWith("\"") && listOfValues.endsWith("\"")) {
			listOfValues = listOfValues.substring(1, listOfValues.length() - 1);
		}
		return Arrays.asList(listOfValues.split(","));
	}
}