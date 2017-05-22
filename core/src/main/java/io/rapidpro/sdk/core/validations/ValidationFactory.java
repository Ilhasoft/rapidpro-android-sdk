package io.rapidpro.sdk.core.validations;

import android.support.annotation.NonNull;

import io.rapidpro.sdk.core.models.TypeValidation;

/**
 * Created by johncordeiro on 15/10/15.
 */
public class ValidationFactory {

    @NonNull
    public static FlowRuleValidation getInstance(TypeValidation typeValidation) {
        switch(typeValidation) {
            case Number:
                return new NumberValidation();
            case NumberLessThan:
                return new NumberLessThanValidation();
            case NumberGreaterThan:
                return new NumberGreaterThanValidation();
            case NumberBetween:
                return new NumberBetweenValidation();
            case NumberEqual:
                return new NumberEqualValidation();
            case Date:
                return new DateValidation();
            case DateAfter:
                return new DateAfterValidation();
            case DateBefore:
                return new DateBeforeValidation();
            case DateEqual:
                return new DateEqualValidation();
            case OpenFieldStarts:
                return new FieldStartsValidation();
            case OpenFieldRegex:
                return new FieldRegexValidation();
            default:
                return new FieldExistsValidation();
        }
    }

}
