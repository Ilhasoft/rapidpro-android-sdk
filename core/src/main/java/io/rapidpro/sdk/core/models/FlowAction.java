package io.rapidpro.sdk.core.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;

/**
 * Created by johncordeiro on 13/10/15.
 */
public class FlowAction implements Parcelable {

    @SerializedName("msg")
    private HashMap<String, String> message;

    private String type;

    public HashMap<String, String> getMessage() {
        return message;
    }

    public void setMessage(HashMap<String, String> message) {
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeSerializable(this.message);
        dest.writeString(this.type);
    }

    public FlowAction() {
    }

    protected FlowAction(Parcel in) {
        this.message = (HashMap<String, String>) in.readSerializable();
        this.type = in.readString();
    }

    public static final Creator<FlowAction> CREATOR = new Creator<FlowAction>() {
        public FlowAction createFromParcel(Parcel source) {
            return new FlowAction(source);
        }

        public FlowAction[] newArray(int size) {
            return new FlowAction[size];
        }
    };
}
