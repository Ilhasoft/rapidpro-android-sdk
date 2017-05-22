package io.rapidpro.sdk.core.validations;

import java.util.Map;

import io.rapidpro.sdk.core.models.Flow;
import io.rapidpro.sdk.core.models.Flow;
import io.rapidpro.sdk.core.models.FlowRuleTest;
import io.rapidpro.sdk.core.models.RulesetResponse;

/**
 * Created by johncordeiro on 15/10/15.
 */
public class FieldRegexValidation implements FlowRuleValidation {

    @Override
    public boolean validate(Flow flow, RulesetResponse response) {
        FlowRuleTest flowRuleTest = response.getRule().getTest();
        Map<String, String> object = (Map<String, String>) flowRuleTest.getTest();
        return response.getResponse().contains(object.get(flow.getBaseLanguage()));
    }

}
