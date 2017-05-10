package io.rapidpro.sdk.core.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by johncordeiro on 13/10/15.
 */
public class FlowRun implements Parcelable {

    @SerializedName("flow_uuid")
    private String flowUuid;

    private Integer flow;

    private Integer run;

    private String contact;

    private Boolean completed;

    @SerializedName("created_on")
    private Date createdOn;

    @SerializedName("modified_on")
    private Date modifiedOn;

    @SerializedName("expires_on")
    private Date expiresOn;

    @SerializedName("expired_on")
    private Date expiredOn;

    public String getFlowUuid() {
        return flowUuid;
    }

    public void setFlowUuid(String flowUuid) {
        this.flowUuid = flowUuid;
    }

    public Integer getFlow() {
        return flow;
    }

    public void setFlow(Integer flow) {
        this.flow = flow;
    }

    public Integer getRun() {
        return run;
    }

    public void setRun(Integer run) {
        this.run = run;
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

    public Date getExpiresOn() {
        return expiresOn;
    }

    public void setExpiresOn(Date expiresOn) {
        this.expiresOn = expiresOn;
    }

    public Date getExpiredOn() {
        return expiredOn;
    }

    public void setExpiredOn(Date expiredOn) {
        this.expiredOn = expiredOn;
    }

    @Override
    public String toString() {
        return "FlowRun{" +
                "flowUuid='" + flowUuid + '\'' +
                ", flow=" + flow +
                ", run=" + run +
                ", contact='" + contact + '\'' +
                ", completed=" + completed +
                ", createdOn=" + createdOn +
                ", modifiedOn=" + modifiedOn +
                ", expiresOn=" + expiresOn +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.flowUuid);
        dest.writeValue(this.flow);
        dest.writeValue(this.run);
        dest.writeString(this.contact);
        dest.writeValue(this.completed);
        dest.writeLong(createdOn != null ? createdOn.getTime() : -1);
        dest.writeLong(modifiedOn != null ? modifiedOn.getTime() : -1);
        dest.writeLong(expiresOn != null ? expiresOn.getTime() : -1);
        dest.writeLong(expiredOn != null ? expiredOn.getTime() : -1);
    }

    public FlowRun() {
    }

    protected FlowRun(Parcel in) {
        this.flowUuid = in.readString();
        this.flow = (Integer) in.readValue(Integer.class.getClassLoader());
        this.run = (Integer) in.readValue(Integer.class.getClassLoader());
        this.contact = in.readString();
        this.completed = (Boolean) in.readValue(Boolean.class.getClassLoader());
        long tmpCreatedOn = in.readLong();
        this.createdOn = tmpCreatedOn == -1 ? null : new Date(tmpCreatedOn);
        long tmpModifiedOn = in.readLong();
        this.modifiedOn = tmpModifiedOn == -1 ? null : new Date(tmpModifiedOn);
        long tmpExpiresOn = in.readLong();
        this.expiresOn = tmpExpiresOn == -1 ? null : new Date(tmpExpiresOn);
        long tmpExpiredOn = in.readLong();
        this.expiredOn = tmpExpiredOn == -1 ? null : new Date(tmpExpiredOn);
    }

    public static final Creator<FlowRun> CREATOR = new Creator<FlowRun>() {
        public FlowRun createFromParcel(Parcel source) {
            return new FlowRun(source);
        }

        public FlowRun[] newArray(int size) {
            return new FlowRun[size];
        }
    };
}
