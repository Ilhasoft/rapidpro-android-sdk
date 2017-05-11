package io.rapidpro.sdk.core.managers;

import android.text.InputType;

import java.text.DateFormat;
import java.util.Date;

import io.rapidpro.sdk.core.models.FlowActionSet;
import io.rapidpro.sdk.core.models.FlowDefinition;
import io.rapidpro.sdk.core.models.FlowRule;
import io.rapidpro.sdk.core.models.FlowRuleset;
import io.rapidpro.sdk.core.models.FlowRun;
import io.rapidpro.sdk.core.models.RulesetResponse;
import io.rapidpro.sdk.core.models.Type;
import io.rapidpro.sdk.core.models.TypeValidation;
import io.rapidpro.sdk.core.validations.ValidationFactory;

/**
 * Created by johncordeiro on 14/10/15.
 */
public class FlowRunnerManager {

    public static int getInputTypeByType(Type type) {
        switch (type) {
            case Date:
                return InputType.TYPE_CLASS_DATETIME;
            case Number:
                return InputType.TYPE_CLASS_NUMBER;
            case Phone:
                return InputType.TYPE_CLASS_PHONE;
            default:
            case OpenField:
                return InputType.TYPE_CLASS_TEXT;
        }
    }

    public static DateFormat getDefaultDateFormat() {
        return DateFormat.getDateInstance();
    }

    public static boolean validateResponse(FlowDefinition flowDefinition, RulesetResponse response) {
        TypeValidation typeValidation = TypeValidation.getTypeValidationForRule(response.getRule());
        return ValidationFactory.getInstance(typeValidation).validate(flowDefinition, response);
    }

    public static boolean isFlowActive(FlowDefinition flowDefinition) {
        return !FlowRunnerManager.isFlowCompleted(flowDefinition)
                && !FlowRunnerManager.isFlowExpired(flowDefinition);
}

    public static boolean isFlowExpired(FlowDefinition flowDefinition) {
        FlowRun flowRun = flowDefinition.getFlowRun();
        return flowRun != null && (flowRun.getExpiredOn() != null
                || (flowRun.getExpiresOn() != null && flowRun.getExpiresOn().before(new Date())));
    }

    public static boolean isFlowCompleted(FlowDefinition flowDefinition) {
        FlowRun flowRun = flowDefinition.getFlowRun();
        return flowRun != null && flowRun.getCompleted();
    }

    public static boolean isLastActionSet(FlowActionSet flowActionSet) {
        return flowActionSet == null || flowActionSet.getDestination() == null
                || flowActionSet.getDestination().isEmpty();
    }

    public static boolean hasRecursiveDestination(FlowDefinition flowDefinition, FlowRuleset ruleSet, FlowRule rule) {
        if(rule.getDestination() != null) {
            FlowActionSet actionSet = getFlowActionSetByUuid(flowDefinition, rule.getDestination());
            return actionSet != null && actionSet.getDestination() != null
                && actionSet.getDestination().equals(ruleSet.getUuid());
        }
        return false;
    }

    public static FlowActionSet getFlowActionSetByUuid(FlowDefinition flowDefinition, String destination) {
        for (FlowActionSet actionSet : flowDefinition.getActionSets()) {
            if(destination.equals(actionSet.getUuid())) {
                return actionSet;
            }
        }
        return null;
    }
}
