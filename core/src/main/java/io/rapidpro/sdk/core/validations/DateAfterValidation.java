package io.rapidpro.sdk.core.validations;

import java.text.ParseException;
import java.util.Date;

import io.rapidpro.sdk.core.managers.FlowRunnerManager;
import io.rapidpro.sdk.core.models.Flow;
import io.rapidpro.sdk.core.models.RulesetResponse;

/**
 * Created by johncordeiro on 15/10/15.
 */
public class DateAfterValidation extends DateValidation implements FlowRuleValidation {

    @Override
    public boolean validate(Flow flow, RulesetResponse response) {
        try {
            Integer timeDelta = getTimeDeltaValue(response);
            Date deltaTime = getDeltaTime(timeDelta);

            Date date = FlowRunnerManager.getDefaultDateFormat().parse(response.getResponse());
            return date.after(deltaTime);
        } catch(ParseException | NumberFormatException exception) {
            return false;
        }
    }

}
