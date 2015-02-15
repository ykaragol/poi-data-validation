package com.github.ykaragol.datavalidation.operators;

import com.github.ykaragol.datavalidation.core.Operator;

public class NotBetweenOperator implements Operator {

	private BetweenOperator betweenOperator;

	public NotBetweenOperator(Double formula1, Double formula2) {
		betweenOperator = new BetweenOperator(formula1, formula2);
	}

	@Override
	public boolean check(Double value) {
		return !betweenOperator.check(value);
	}

}
