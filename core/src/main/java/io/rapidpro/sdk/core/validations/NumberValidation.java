package io.rapidpro.sdk.core.validations;

import io.rapidpro.sdk.core.models.Flow;
import io.rapidpro.sdk.core.models.RulesetResponse;

/**
 * Created by johncordeiro on 15/10/15.
 */
public class NumberValidation implements FlowRuleValidation {

    @Override
    public boolean validate(Flow flow, RulesetResponse response) {
        try {
            Integer value = Integer.valueOf(response.getResponse());
            return value != null;
        } catch(NumberFormatException exception) {
            return false;
        }
    }
}
