package io.fcmchannel.sdk.core.validations;

import io.fcmchannel.sdk.core.models.Flow;
import io.fcmchannel.sdk.core.models.RulesetResponse;

/**
 * Created by johncordeiro on 15/10/15.
 */
public class NumberLessThanValidation implements FlowRuleValidation {

    @Override
    public boolean validate(Flow flow, RulesetResponse response) {
        try {
            String testValue = response.getRule().getTest().getTest().values().iterator().next();

            Integer value = Integer.valueOf(response.getResponse());
            return value < Integer.valueOf(testValue);
        } catch(NumberFormatException exception) {
            return false;
        }
    }
}
