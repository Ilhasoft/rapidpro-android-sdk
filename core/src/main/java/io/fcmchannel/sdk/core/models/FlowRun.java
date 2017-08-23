package io.fcmchannel.sdk.core.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

import io.fcmchannel.sdk.core.models.v2.Contact;

/**
 * Created by johncordeiro on 13/10/15.
 */
public class FlowRun implements Parcelable {

    public static final String EXIT_TYPE_COMPLETED = "completed";

    private Flow flow;

    private Contact contact;

    private Boolean responded;

    private List<FlowStep> path;

    @SerializedName("created_on")
    private Date createdOn;

    @SerializedName("modified_on")
    private Date modifiedOn;

    @SerializedName("exit_type")
    private String exitType;

    public Boolean getResponded() {
        return responded;
    }

    public void setResponded(Boolean responded) {
        this.responded = responded;
    }

    public List<FlowStep> getPath() {
        return path;
    }

    public void setPath(List<FlowStep> path) {
        this.path = path;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public Date getModifiedOn() {
        return modifiedOn;
    }

    public void setModifiedOn(Date modifiedOn) {
        this.modifiedOn = modifiedOn;
    }

    public String getExitType() {
        return exitType;
    }

    public void setExitType(String exitType) {
        this.exitType = exitType;
    }

    public Flow getFlow() {
        return flow;
    }

    public FlowRun setFlow(Flow flow) {
        this.flow = flow;
        return this;
    }

    public Contact getContact() {
        return contact;
    }

    public FlowRun setContact(Contact contact) {
        this.contact = contact;
        return this;
    }

    @Override
    public String toString() {
        return "FlowRun{" +
                ", flow=" + flow +
                ", contact='" + contact + '\'' +
                ", responded=" + responded +
                ", createdOn=" + createdOn +
                ", modifiedOn=" + modifiedOn +
                ", expiresOn=" + exitType +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.flow, flags);
        dest.writeParcelable(this.contact, flags);
        dest.writeValue(this.responded);
        dest.writeTypedList(this.path);
        dest.writeLong(this.createdOn != null ? this.createdOn.getTime() : -1);
        dest.writeLong(this.modifiedOn != null ? this.modifiedOn.getTime() : -1);
        dest.writeString(this.exitType);
    }

    public FlowRun() {
    }

    protected FlowRun(Parcel in) {
        this.flow = in.readParcelable(Flow.class.getClassLoader());
        this.contact = in.readParcelable(Contact.class.getClassLoader());
        this.responded = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.path = in.createTypedArrayList(FlowStep.CREATOR);
        long tmpCreatedOn = in.readLong();
        this.createdOn = tmpCreatedOn == -1 ? null : new Date(tmpCreatedOn);
        long tmpModifiedOn = in.readLong();
        this.modifiedOn = tmpModifiedOn == -1 ? null : new Date(tmpModifiedOn);
        this.exitType = in.readString();
    }

    public static final Creator<FlowRun> CREATOR = new Creator<FlowRun>() {
        @Override
        public FlowRun createFromParcel(Parcel source) {
            return new FlowRun(source);
        }

        @Override
        public FlowRun[] newArray(int size) {
            return new FlowRun[size];
        }
    };
}
