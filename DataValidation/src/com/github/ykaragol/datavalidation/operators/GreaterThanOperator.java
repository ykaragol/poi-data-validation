package com.github.ykaragol.datavalidation.operators;

import com.github.ykaragol.datavalidation.core.Operator;

public class GreaterThanOperator implements Operator {

	private double formula1;

	public GreaterThanOperator(Double formula1) {
		this.formula1 = formula1;
	}

	@Override
	public boolean check(Double value) {
		return Double.compare(value, formula1) > 0;
	}

}
