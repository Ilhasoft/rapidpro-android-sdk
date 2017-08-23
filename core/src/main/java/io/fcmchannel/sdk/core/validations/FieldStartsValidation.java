package io.fcmchannel.sdk.core.validations;

import java.util.Map;

import io.fcmchannel.sdk.core.models.Flow;
import io.fcmchannel.sdk.core.models.FlowRuleTest;
import io.fcmchannel.sdk.core.models.RulesetResponse;

/**
 * Created by johncordeiro on 15/10/15.
 */
public class FieldStartsValidation implements FlowRuleValidation {

    @Override
    public boolean validate(Flow flow, RulesetResponse response) {
        FlowRuleTest flowRuleTest = response.getRule().getTest();
        Map<String, String> object = (Map<String, String>) flowRuleTest.getTest();
        return response.getResponse().startsWith(object.get(flow.getBaseLanguage()));
    }

}
