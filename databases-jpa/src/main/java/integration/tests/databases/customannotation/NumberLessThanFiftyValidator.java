package integration.tests.databases.customannotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NumberLessThanFiftyValidator implements ConstraintValidator<NumberLessThanFiftyConstraint, Integer>
{

	@Override
	public void initialize(NumberLessThanFiftyConstraint contactNumber) {
	}

	@Override
	public boolean isValid(Integer priceField, ConstraintValidatorContext cxt) {
		return priceField != null && priceField <= 50;
	}

}
