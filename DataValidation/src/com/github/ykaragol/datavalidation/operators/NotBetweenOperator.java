package com.github.ykaragol.datavalidation.operators;

import com.github.ykaragol.datavalidation.Operator;


public class NotBetweenOperator implements Operator {

	private BetweenOperator betweenOperator;
	
	public NotBetweenOperator(String formula1, String formula2) {
		betweenOperator = new BetweenOperator(formula1, formula2);
	}

	@Override
	public boolean check(Double value) {
		return ! betweenOperator.check(value);
	}

	
}
