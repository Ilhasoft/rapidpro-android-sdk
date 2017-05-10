package io.rapidpro.sdk.core.models;

import android.support.annotation.NonNull;
import android.support.annotation.StringRes;

import io.rapidpro.sdk.core.R;

/**
 * Created by johncordeiro on 14/10/15.
 */
public enum TypeValidation {

    OpenField(Type.OpenField, "true", R.string.error_fill_field),
    Choice(Type.Choice, "contains_any", R.string.error_fill_field),
    OpenFieldContains(Type.OpenField, "contains", R.string.error_fill_field),
    OpenFieldNotEmpty(Type.OpenField, "not_empty", R.string.error_fill_field),
    OpenFieldStarts(Type.OpenField, "starts", R.string.error_invalid_field),
    OpenFieldRegex(Type.OpenField, "regex", R.string.error_invalid_field),
    Number(Type.Number, "number", R.string.error_numeric_field),
    NumberLessThan(Type.Number, "lt", R.string.error_numeric_instruction_field),
    NumberGreaterThan(Type.Number, "gt", R.string.error_numeric_instruction_field),
    NumberBetween(Type.Number, "between", R.string.error_numeric_instruction_field),
    NumberEqual(Type.Number, "eq", R.string.error_numeric_instruction_field),
    Date(Type.Date, "date", R.string.error_date_field),
    DateBefore(Type.Date, "date_before", R.string.error_date_field),
    DateAfter(Type.Date, "date_after", R.string.error_date_field),
    DateEqual(Type.Date, "date_equal", R.string.error_date_field),
    Phone(Type.Phone, "phone", R.string.error_fill_field),
    State(Type.State, "state", R.string.error_fill_field),
    District(Type.District, "district", R.string.error_fill_field);

    private final Type type;
    private final String validation;
    private final @StringRes
    int message;

    TypeValidation(Type type, String validation, int message) {
        this.type = type;
        this.validation = validation;
        this.message = message;
    }

    public static TypeValidation getTypeValidationForRule(FlowRule rule) {
        return getTypeValidation(rule.getTest().getType());
    }

    @NonNull
    public static TypeValidation getTypeValidation(String validation) {
        for (TypeValidation typeValidation : values()) {
            if(typeValidation.validation.equals(validation)) {
                return typeValidation;
            }
        }
        return OpenField;
    }

    public Type getType() {
        return type;
    }

    public String getValidation() {
        return validation;
    }

    public int getMessage() {
        return message;
    }
}
