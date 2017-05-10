package io.rapidpro.sdk.core.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by johncordeiro on 13/10/15.
 */
public class FlowActionSet implements Parcelable {

    @SerializedName("x")
    private Integer positionX;

    @SerializedName("y")
    private Integer positionY;

    private String destination;

    private String uuid;

    private List<FlowAction> actions;

    public Integer getPositionX() {
        return positionX;
    }

    public void setPositionX(Integer positionX) {
        this.positionX = positionX;
    }

    public Integer getPositionY() {
        return positionY;
    }

    public void setPositionY(Integer positionY) {
        this.positionY = positionY;
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

    public List<FlowAction> getActions() {
        return actions;
    }

    public void setActions(List<FlowAction> actions) {
        this.actions = actions;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.positionX);
        dest.writeValue(this.positionY);
        dest.writeString(this.destination);
        dest.writeString(this.uuid);
        dest.writeTypedList(actions);
    }

    public FlowActionSet() {
    }

    protected FlowActionSet(Parcel in) {
        this.positionX = (Integer) in.readValue(Integer.class.getClassLoader());
        this.positionY = (Integer) in.readValue(Integer.class.getClassLoader());
        this.destination = in.readString();
        this.uuid = in.readString();
        this.actions = in.createTypedArrayList(FlowAction.CREATOR);
    }

    public static final Creator<FlowActionSet> CREATOR = new Creator<FlowActionSet>() {
        public FlowActionSet createFromParcel(Parcel source) {
            return new FlowActionSet(source);
        }

        public FlowActionSet[] newArray(int size) {
            return new FlowActionSet[size];
        }
    };

    @Override
    public String toString() {
        return "FlowActionSet{" +
                "positionX=" + positionX +
                ", positionY=" + positionY +
                ", destination='" + destination + '\'' +
                ", uuid='" + uuid + '\'' +
                ", actions=" + actions +
                '}';
    }
}
