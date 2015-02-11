package com.github.ykaragol.datavalidation.core;

import com.github.ykaragol.datavalidation.ValidationResult;

public class ValidationResultImpl implements ValidationResult {

	private boolean result;
	private String message;

	public ValidationResultImpl(boolean result, String message) {
		super();
		this.result = result;
		this.message = message;
	}

	@Override
	public boolean getResult() {
		return result;
	}

	@Override
	public String getMessage() {
		return message;
	}

}
