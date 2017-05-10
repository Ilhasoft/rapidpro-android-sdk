package io.rapidpro.sdk.core.validations;

import io.rapidpro.sdk.core.models.FlowDefinition;
import io.rapidpro.sdk.core.models.RulesetResponse;

/**
 * Created by johncordeiro on 15/10/15.
 */
public interface FlowRuleValidation {

    boolean validate(FlowDefinition flowDefinition, RulesetResponse response);

}
