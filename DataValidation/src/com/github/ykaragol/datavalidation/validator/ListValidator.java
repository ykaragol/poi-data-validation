package com.github.ykaragol.datavalidation.validator;

import java.util.HashSet;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFCell;

import com.github.ykaragol.datavalidation.ValidationResult;
import com.github.ykaragol.datavalidation.core.ValidationResultImpl;
import com.github.ykaragol.datavalidation.core.Validator;
import com.github.ykaragol.datavalidation.utils.CellValueParser;

public class ListValidator implements Validator {

	private HashSet<Object> referenceValues;

	public ListValidator(List<Object> referenceValues) {
		this.referenceValues = new HashSet<Object>(referenceValues);
	}

	@Override
	public ValidationResult validate(XSSFCell cell) {
		Object cellValue = CellValueParser.getCellValue(cell);
		if (referenceValues.contains(cellValue)) {
			return null;
		}
		return new ValidationResultImpl(cell.getSheet().getSheetName(), cell.getReference(), false,
				"Value type is not as expected!");
	}

}
