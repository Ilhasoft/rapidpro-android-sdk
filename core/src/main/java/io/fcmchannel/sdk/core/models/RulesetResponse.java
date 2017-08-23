package io.fcmchannel.sdk.core.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by johncordeiro on 14/10/15.
 */
public class RulesetResponse implements Parcelable {

    private FlowRule rule;

    private String response;

    public RulesetResponse(FlowRule rule, String response) {
        this.rule = rule;
        this.response = response;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public FlowRule getRule() {
        return rule;
    }

    public void setRule(FlowRule rule) {
        this.rule = rule;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RulesetResponse that = (RulesetResponse) o;
        return rule.equals(that.rule);

    }

    @Override
    public int hashCode() {
        return rule.hashCode();
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.rule, flags);
        dest.writeString(this.response);
    }

    protected RulesetResponse(Parcel in) {
        this.rule = in.readParcelable(FlowRule.class.getClassLoader());
        this.response = in.readString();
    }

    public static final Creator<RulesetResponse> CREATOR = new Creator<RulesetResponse>() {
        @Override
        public RulesetResponse createFromParcel(Parcel source) {
            return new RulesetResponse(source);
        }

        @Override
        public RulesetResponse[] newArray(int size) {
            return new RulesetResponse[size];
        }
    };
}
