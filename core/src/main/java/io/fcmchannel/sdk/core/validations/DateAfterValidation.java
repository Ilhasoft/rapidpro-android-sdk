package io.fcmchannel.sdk.core.validations;

import java.text.ParseException;
import java.util.Date;

import io.fcmchannel.sdk.core.managers.FlowRunnerManager;
import io.fcmchannel.sdk.core.models.Flow;
import io.fcmchannel.sdk.core.models.RulesetResponse;

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
