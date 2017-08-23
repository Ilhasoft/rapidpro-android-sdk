package io.fcmchannel.sdk.core.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;

/**
 * Created by johncordeiro on 13/10/15.
 */
public class FlowRuleTest implements Parcelable {

    private HashMap<String, String> test;

    private String base;

    private String type;

    private String min;

    private String max;

    public HashMap<String, String> getTest() {
        return test;
    }

    public void setTest(HashMap<String, String> test) {
        this.test = test;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMin() {
        return min;
    }

    public void setMin(String min) {
        this.min = min;
    }

    public String getMax() {
        return max;
    }

    public void setMax(String max) {
        this.max = max;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeSerializable(this.test);
        dest.writeString(this.base);
        dest.writeString(this.type);
        dest.writeString(this.min);
        dest.writeString(this.max);
    }

    public FlowRuleTest() {
    }

    protected FlowRuleTest(Parcel in) {
        this.test = (HashMap<String, String>) in.readSerializable();
        this.base = in.readString();
        this.type = in.readString();
        this.min = in.readString();
        this.max = in.readString();
    }

    public static final Creator<FlowRuleTest> CREATOR = new Creator<FlowRuleTest>() {
        public FlowRuleTest createFromParcel(Parcel source) {
            return new FlowRuleTest(source);
        }

        public FlowRuleTest[] newArray(int size) {
            return new FlowRuleTest[size];
        }
    };
}
