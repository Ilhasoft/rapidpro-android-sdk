package io.rapidpro.sdk.core.validations;

import io.rapidpro.sdk.core.models.FlowDefinition;
import io.rapidpro.sdk.core.models.RulesetResponse;

/**
 * Created by johncordeiro on 15/10/15.
 */
public class NumberGreaterThanValidation implements FlowRuleValidation {

    @Override
    public boolean validate(FlowDefinition flowDefinition, RulesetResponse response) {
        try {
            String testValue = response.getRule().getTest().getTest().values().iterator().next();

            Integer value = Integer.valueOf(response.getResponse());
            return value > Integer.valueOf(testValue);
        } catch(NumberFormatException exception) {
            return false;
        }
    }
}
