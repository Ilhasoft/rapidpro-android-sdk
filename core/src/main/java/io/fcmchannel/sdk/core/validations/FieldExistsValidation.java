package io.fcmchannel.sdk.core.validations;

import io.fcmchannel.sdk.core.models.Flow;
import io.fcmchannel.sdk.core.models.RulesetResponse;

/**
 * Created by johncordeiro on 15/10/15.
 */
public class FieldExistsValidation implements FlowRuleValidation {

    @Override
    public boolean validate(Flow flow, RulesetResponse response) throws NullPointerException {
        return !response.getResponse().isEmpty();
    }
}
