package io.fcmchannel.sdk.core.validations;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import io.fcmchannel.sdk.core.managers.FlowRunnerManager;
import io.fcmchannel.sdk.core.models.Flow;
import io.fcmchannel.sdk.core.models.RulesetResponse;

/**
 * Created by johncordeiro on 15/10/15.
 */
public class DateEqualValidation extends DateValidation implements FlowRuleValidation {

    @Override
    public boolean validate(Flow flow, RulesetResponse response) {
        try {
            Integer timeDelta = getTimeDeltaValue(response);
            Date deltaTime = getDeltaTime(timeDelta);

            Date date = FlowRunnerManager.getDefaultDateFormat().parse(response.getResponse());
            return isDateEquals(date, deltaTime);
        } catch(ParseException | NumberFormatException exception) {
            return false;
        }
    }

    private boolean isDateEquals(Date date1, Date date2) {
        Calendar calendar1 = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        calendar1.setTime(date1);
        calendar2.setTime(date2);
        return calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR) &&
                calendar1.get(Calendar.DAY_OF_YEAR) == calendar2.get(Calendar.DAY_OF_YEAR);
    }
}
