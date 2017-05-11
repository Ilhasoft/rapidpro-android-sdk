package io.rapidpro.sdk.core.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by johncordeiro on 16/10/15.
 */
public class FlowMetadata implements Parcelable {

    private String uuid;

    private Integer expires;

    private String name;

    private Integer revision;

    private Integer id;

    @SerializedName("save_on")
    private Date savedOn;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Integer getExpires() {
        return expires;
    }

    public void setExpires(Integer expires) {
        this.expires = expires;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getRevision() {
        return revision;
    }

    public void setRevision(Integer revision) {
        this.revision = revision;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getSavedOn() {
        return savedOn;
    }

    public void setSavedOn(Date savedOn) {
        this.savedOn = savedOn;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.uuid);
        dest.writeValue(this.expires);
        dest.writeString(this.name);
        dest.writeValue(this.revision);
        dest.writeValue(this.id);
        dest.writeLong(savedOn != null ? savedOn.getTime() : -1);
    }

    public FlowMetadata() {
    }

    protected FlowMetadata(Parcel in) {
        this.uuid = in.readString();
        this.expires = (Integer) in.readValue(Integer.class.getClassLoader());
        this.name = in.readString();
        this.revision = (Integer) in.readValue(Integer.class.getClassLoader());
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        long tmpSavedOn = in.readLong();
        this.savedOn = tmpSavedOn == -1 ? null : new Date(tmpSavedOn);
    }

    public static final Creator<FlowMetadata> CREATOR = new Creator<FlowMetadata>() {
        public FlowMetadata createFromParcel(Parcel source) {
            return new FlowMetadata(source);
        }

        public FlowMetadata[] newArray(int size) {
            return new FlowMetadata[size];
        }
    };
}
