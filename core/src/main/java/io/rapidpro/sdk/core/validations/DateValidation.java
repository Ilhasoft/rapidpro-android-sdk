package io.rapidpro.sdk.core.validations;

import android.support.annotation.NonNull;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import io.rapidpro.sdk.core.managers.FlowRunnerManager;
import io.rapidpro.sdk.core.models.FlowDefinition;
import io.rapidpro.sdk.core.models.RulesetResponse;

/**
 * Created by johncordeiro on 15/10/15.
 */
public class DateValidation implements FlowRuleValidation {

    public static final String DEFAULT_TEST = "@date.today|time_delta:";

    @Override
    public boolean validate(FlowDefinition flowDefinition, RulesetResponse response) {
        try {
            Date date = FlowRunnerManager.getDefaultDateFormat().parse(response.getResponse());
            return date != null;
        } catch (ParseException e) {
            return false;
        }
    }

    protected Integer getTimeDeltaValue(RulesetResponse response) throws NumberFormatException {
        String timeDelta = response.getRule().getTest().getTest().values().iterator().next();
        return Integer.valueOf(timeDelta.replace(DEFAULT_TEST, "").replace("'", ""));
    }

    @NonNull
    protected Date getDeltaTime(Integer timeDelta) {
        Calendar calendar = Calendar.getInstance();
        calendar.roll(Calendar.DAY_OF_MONTH, timeDelta);
        return calendar.getTime();
    }

}
