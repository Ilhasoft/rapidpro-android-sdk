package io.fcmchannel.sdk.core.validations;

import io.fcmchannel.sdk.core.models.Flow;
import io.fcmchannel.sdk.core.models.RulesetResponse;

/**
 * Created by johncordeiro on 15/10/15.
 */
public interface FlowRuleValidation {

    boolean validate(Flow flow, RulesetResponse response);

}
