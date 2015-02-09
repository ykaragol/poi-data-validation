package com.github.ykaragol.datavalidation.operators;

import com.github.ykaragol.datavalidation.Operator;

public class BetweenOperator implements Operator {

	private GreaterOrEqualOperator greaterOrEqualOperator;
	private LessOrEqualOperator lessOrEqualOperator;
	
	public BetweenOperator(String formula1, String formula2) {
		greaterOrEqualOperator = new GreaterOrEqualOperator(formula1);
		lessOrEqualOperator = new LessOrEqualOperator(formula2);
	}

	@Override
	public boolean check(Double value){
		return greaterOrEqualOperator.check(value) && lessOrEqualOperator.check(value);
	}
}
