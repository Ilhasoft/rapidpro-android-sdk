package io.fcmchannel.sdk.core.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;
import java.util.List;

/**
 * Created by johncordeiro on 16/10/15.
 */
public class FlowStepSet implements Parcelable {

    private String flow;

    private String contact;

    private Boolean completed;

    private Date started;

    private Integer revision;

    private List<FlowStep> steps;

    public String getFlow() {
        return flow;
    }

    public void setFlow(String flow) {
        this.flow = flow;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    public Date getStarted() {
        return started;
    }

    public void setStarted(Date started) {
        this.started = started;
    }

    public Integer getRevision() {
        return revision;
    }

    public void setRevision(Integer revision) {
        this.revision = revision;
    }

    public List<FlowStep> getSteps() {
        return steps;
    }

    public void setSteps(List<FlowStep> steps) {
        this.steps = steps;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.flow);
        dest.writeString(this.contact);
        dest.writeValue(this.completed);
        dest.writeLong(started != null ? started.getTime() : -1);
        dest.writeValue(this.revision);
        dest.writeTypedList(steps);
    }

    public FlowStepSet() {
    }

    protected FlowStepSet(Parcel in) {
        this.flow = in.readString();
        this.contact = in.readString();
        this.completed = (Boolean) in.readValue(Boolean.class.getClassLoader());
        long tmpStarted = in.readLong();
        this.started = tmpStarted == -1 ? null : new Date(tmpStarted);
        this.revision = (Integer) in.readValue(Integer.class.getClassLoader());
        this.steps = in.createTypedArrayList(FlowStep.CREATOR);
    }

    public static final Creator<FlowStepSet> CREATOR = new Creator<FlowStepSet>() {
        public FlowStepSet createFromParcel(Parcel source) {
            return new FlowStepSet(source);
        }

        public FlowStepSet[] newArray(int size) {
            return new FlowStepSet[size];
        }
    };
}
