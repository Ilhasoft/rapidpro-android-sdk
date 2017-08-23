package io.fcmchannel.sdk.core.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

/**
 * Created by johncordeiro on 16/10/15.
 */
public class FlowStep implements Parcelable {

    private String node;

    @SerializedName("arrived_on")
    private Date arrivedOn;

    @SerializedName("time")
    private Date time;

    private List<FlowAction> actions;

    public String getNode() {
        return node;
    }

    public void setNode(String node) {
        this.node = node;
    }

    public Date getArrivedOn() {
        return arrivedOn;
    }

    public void setArrivedOn(Date arrivedOn) {
        this.arrivedOn = arrivedOn;
    }

    public List<FlowAction> getActions() {
        return actions;
    }

    public void setActions(List<FlowAction> actions) {
        this.actions = actions;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.node);
        dest.writeLong(this.arrivedOn != null ? this.arrivedOn.getTime() : -1);
        dest.writeLong(this.time != null ? this.time.getTime() : -1);
        dest.writeTypedList(this.actions);
    }

    public FlowStep() {
    }

    protected FlowStep(Parcel in) {
        this.node = in.readString();
        long tmpArrivedOn = in.readLong();
        this.arrivedOn = tmpArrivedOn == -1 ? null : new Date(tmpArrivedOn);
        long tmpTime = in.readLong();
        this.time = tmpTime == -1 ? null : new Date(tmpTime);
        this.actions = in.createTypedArrayList(FlowAction.CREATOR);
    }

    public static final Creator<FlowStep> CREATOR = new Creator<FlowStep>() {
        @Override
        public FlowStep createFromParcel(Parcel source) {
            return new FlowStep(source);
        }

        @Override
        public FlowStep[] newArray(int size) {
            return new FlowStep[size];
        }
    };
}
