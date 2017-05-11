package io.rapidpro.sdk.core.validations;

import java.util.Map;

import io.rapidpro.sdk.core.models.FlowDefinition;
import io.rapidpro.sdk.core.models.FlowRuleTest;
import io.rapidpro.sdk.core.models.RulesetResponse;

/**
 * Created by johncordeiro on 15/10/15.
 */
public class FieldStartsValidation implements FlowRuleValidation {

    @Override
    public boolean validate(FlowDefinition flowDefinition, RulesetResponse response) {
        FlowRuleTest flowRuleTest = response.getRule().getTest();
        Map<String, String> object = (Map<String, String>) flowRuleTest.getTest();
        return response.getResponse().startsWith(object.get(flowDefinition.getBaseLanguage()));
    }

}
