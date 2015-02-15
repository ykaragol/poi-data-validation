package com.github.ykaragol.datavalidation.operators;

import com.github.ykaragol.datavalidation.core.Operator;

public class EqualOperator implements Operator {

	private Double formula1;

	public EqualOperator(Double formula1) {
		this.formula1 = formula1;
	}

	@Override
	public boolean check(Double value) {
		return Double.compare(formula1, value) == 0;
	}

}
