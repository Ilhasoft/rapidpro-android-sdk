package io.rapidpro.sdk.core.managers;

import android.text.InputType;

import java.text.DateFormat;

import io.rapidpro.sdk.core.models.Flow;
import io.rapidpro.sdk.core.models.FlowActionSet;
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

    public static boolean validateResponse(Flow flow, RulesetResponse response) {
        TypeValidation typeValidation = TypeValidation.getTypeValidationForRule(response.getRule());
        return ValidationFactory.getInstance(typeValidation).validate(flow, response);
    }

    public static boolean isFlowActive(FlowRun flowRun) {
        return !FlowRunnerManager.isFlowCompleted(flowRun)
                && !FlowRunnerManager.isFlowExpired(flowRun);
    }

    public static boolean isFlowExpired(FlowRun flowRun) {
        return flowRun != null && flowRun.getExitType() != null;
    }

    public static boolean isFlowCompleted(FlowRun flowRun) {
        return flowRun != null && flowRun.getExitType() != null
            && flowRun.getExitType().equals(FlowRun.EXIT_TYPE_COMPLETED);
    }

    public static boolean isLastActionSet(FlowActionSet flowActionSet) {
        return flowActionSet == null || flowActionSet.getDestination() == null
                || flowActionSet.getDestination().isEmpty();
    }

    public static boolean hasRecursiveDestination(Flow flow, FlowRuleset ruleSet, FlowRule rule) {
        if(rule.getDestination() != null) {
            FlowActionSet actionSet = getFlowActionSetByUuid(flow, rule.getDestination());
            return actionSet != null && actionSet.getDestination() != null
                && actionSet.getDestination().equals(ruleSet.getUuid());
        }
        return false;
    }

    public static FlowActionSet getFlowActionSetByUuid(Flow flow, String destination) {
        for (FlowActionSet actionSet : flow.getActionSets()) {
            if(destination.equals(actionSet.getUuid())) {
                return actionSet;
            }
        }
        return null;
    }
}
