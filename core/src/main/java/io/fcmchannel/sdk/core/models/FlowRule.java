package io.fcmchannel.sdk.core.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;

/**
 * Created by johncordeiro on 13/10/15.
 */
public class FlowRule implements Parcelable {

    private HashMap<String, String> category;

    private FlowRuleTest test;

    private String destination;

    private String uuid;

    @SerializedName("destination_type")
    private String destinationType;

    public HashMap<String, String> getCategory() {
        return category;
    }

    public void setCategory(HashMap<String, String> category) {
        this.category = category;
    }

    public FlowRuleTest getTest() {
        return test;
    }

    public void setTest(FlowRuleTest test) {
        this.test = test;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getDestinationType() {
        return destinationType;
    }

    public void setDestinationType(String destinationType) {
        this.destinationType = destinationType;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeSerializable(this.category);
        dest.writeParcelable(this.test, 0);
        dest.writeString(this.destination);
        dest.writeString(this.uuid);
        dest.writeString(this.destinationType);
    }

    public FlowRule() {
    }

    protected FlowRule(Parcel in) {
        this.category = (HashMap<String, String>) in.readSerializable();
        this.test = in.readParcelable(FlowRuleTest.class.getClassLoader());
        this.destination = in.readString();
        this.uuid = in.readString();
        this.destinationType = in.readString();
    }

    public static final Creator<FlowRule> CREATOR = new Creator<FlowRule>() {
        public FlowRule createFromParcel(Parcel source) {
            return new FlowRule(source);
        }

        public FlowRule[] newArray(int size) {
            return new FlowRule[size];
        }
    };

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FlowRule flowRule = (FlowRule) o;
        return uuid.equals(flowRule.uuid);

    }

    @Override
    public int hashCode() {
        return uuid.hashCode();
    }
}
