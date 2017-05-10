package io.rapidpro.sdk.core.validations;

import io.rapidpro.sdk.core.models.FlowDefinition;
import io.rapidpro.sdk.core.models.RulesetResponse;

/**
 * Created by johncordeiro on 15/10/15.
 */
public class NumberBetweenValidation implements FlowRuleValidation {

    @Override
    public boolean validate(FlowDefinition flowDefinition, RulesetResponse response) {
        try {
            Integer value = Integer.valueOf(response.getResponse());
            return value >= Integer.valueOf(response.getRule().getTest().getMin())
                    && value <= Integer.valueOf(response.getRule().getTest().getMax());
        } catch(NumberFormatException exception) {
            return false;
        }
    }
}
