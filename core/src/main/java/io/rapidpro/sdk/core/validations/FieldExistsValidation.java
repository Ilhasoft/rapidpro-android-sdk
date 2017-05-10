package io.rapidpro.sdk.core.validations;

import io.rapidpro.sdk.core.models.FlowDefinition;
import io.rapidpro.sdk.core.models.RulesetResponse;

/**
 * Created by johncordeiro on 15/10/15.
 */
public class FieldExistsValidation implements FlowRuleValidation {

    @Override
    public boolean validate(FlowDefinition flowDefinition, RulesetResponse response) throws NullPointerException {
        return !response.getResponse().isEmpty();
    }
}
