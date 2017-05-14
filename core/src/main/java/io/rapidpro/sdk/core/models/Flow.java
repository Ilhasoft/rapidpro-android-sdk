package io.rapidpro.sdk.core.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by John Cordeiro on 4/18/17.
 * Copyright © 2017 Soloshot, Inc. All rights reserved.
 */

public class Flow implements Parcelable {

    private String uuid;

    private String name;

    @SerializedName("base_language")
    private String baseLanguage;

    @SerializedName("action_sets")
    private List<FlowActionSet> actionSets;

    @SerializedName("rule_sets")
    private List<FlowRuleset> ruleSets;

    private Integer version;

    @SerializedName("flow_type")
    private String type;

    private String entry;

    private FlowMetadata metadata;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBaseLanguage() {
        return baseLanguage;
    }

    public void setBaseLanguage(String baseLanguage) {
        this.baseLanguage = baseLanguage;
    }

    public List<FlowActionSet> getActionSets() {
        return actionSets;
    }

    public void setActionSets(List<FlowActionSet> actionSets) {
        this.actionSets = actionSets;
    }

    public List<FlowRuleset> getRuleSets() {
        return ruleSets;
    }

    public void setRuleSets(List<FlowRuleset> ruleSets) {
        this.ruleSets = ruleSets;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEntry() {
        return entry;
    }

    public void setEntry(String entry) {
        this.entry = entry;
    }

    public FlowMetadata getMetadata() {
        return metadata;
    }

    public void setMetadata(FlowMetadata metadata) {
        this.metadata = metadata;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Flow flow = (Flow) o;

        String currentUuid = uuid != null ? uuid : metadata != null ? metadata.getUuid() : null;
        String flowUuid = flow.uuid != null ? flow.uuid : flow.metadata != null ? flow.metadata.getUuid() : null;

        if (currentUuid != null && flowUuid != null) {
            return currentUuid.equals(flowUuid);
        } else {
            return super.equals(o);
        }
    }

    @Override
    public int hashCode() {
        String currentUuid = uuid != null ? uuid : metadata != null ? metadata.getUuid() : null;
        if (currentUuid != null) {
            return currentUuid.hashCode();
        } else {
            return super.hashCode();
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.uuid);
        dest.writeString(this.name);
        dest.writeString(this.baseLanguage);
        dest.writeTypedList(this.actionSets);
        dest.writeTypedList(this.ruleSets);
        dest.writeValue(this.version);
        dest.writeString(this.type);
        dest.writeString(this.entry);
        dest.writeParcelable(this.metadata, flags);
    }

    public Flow() {
    }

    protected Flow(Parcel in) {
        this.uuid = in.readString();
        this.name = in.readString();
        this.baseLanguage = in.readString();
        this.actionSets = in.createTypedArrayList(FlowActionSet.CREATOR);
        this.ruleSets = in.createTypedArrayList(FlowRuleset.CREATOR);
        this.version = (Integer) in.readValue(Integer.class.getClassLoader());
        this.type = in.readString();
        this.entry = in.readString();
        this.metadata = in.readParcelable(FlowMetadata.class.getClassLoader());
    }

    public static final Creator<Flow> CREATOR = new Creator<Flow>() {
        @Override
        public Flow createFromParcel(Parcel source) {
            return new Flow(source);
        }

        @Override
        public Flow[] newArray(int size) {
            return new Flow[size];
        }
    };
}
