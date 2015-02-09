package com.github.ykaragol.datavalidation.operators;

import com.github.ykaragol.datavalidation.Operator;


public class EqualOperator implements Operator {

	private Double double1;
	
	public EqualOperator(String formula1) {
		double1 = Double.parseDouble(formula1);
	}

	@Override
	public boolean check(Double value) {
		return Double.compare(double1, value) == 0;
	}

}
