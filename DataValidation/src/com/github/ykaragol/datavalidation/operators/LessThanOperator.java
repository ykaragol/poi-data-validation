package com.github.ykaragol.datavalidation.operators;

import com.github.ykaragol.datavalidation.Operator;

public class LessThanOperator implements Operator {

	private double double1;

	public LessThanOperator(String formula1) {
		double1 = Double.parseDouble(formula1);
	}

	@Override
	public boolean check(Double value) {
		return Double.compare(value, double1) < 0;
	}

}
