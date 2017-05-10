package io.rapidpro.sdk.core.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

/**
 * Created by johncordeiro on 13/10/15.
 */
public class FlowDefinition implements Parcelable {

    private transient Contact contact;

    @SerializedName("base_language")
    private String baseLanguage;

    @SerializedName("action_sets")
    private List<FlowActionSet> actionSets;

    @SerializedName("last_saved")
    private Date lastSaved;

    private Integer version;

    @SerializedName("flow_type")
    private String type;

    private String entry;

    @SerializedName("rule_sets")
    private List<FlowRuleset> ruleSets;

    private FlowMetadata metadata;

    private transient FlowRun flowRun;

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
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

    public Date getLastSaved() {
        return lastSaved;
    }

    public void setLastSaved(Date lastSaved) {
        this.lastSaved = lastSaved;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getEntry() {
        return entry;
    }

    public void setEntry(String entry) {
        this.entry = entry;
    }

    public List<FlowRuleset> getRuleSets() {
        return ruleSets;
    }

    public void setRuleSets(List<FlowRuleset> ruleSets) {
        this.ruleSets = ruleSets;
    }

    public FlowMetadata getMetadata() {
        return metadata;
    }

    public void setMetadata(FlowMetadata metadata) {
        this.metadata = metadata;
    }

    public FlowRun getFlowRun() {
        return flowRun;
    }

    public void setFlowRun(FlowRun flowRun) {
        this.flowRun = flowRun;
    }

    @Override
    public String toString() {
        return "FlowDefinition{" +
                "baseLanguage='" + baseLanguage + '\'' +
                ", actionSets=" + actionSets +
                ", lastSaved=" + lastSaved +
                ", entry='" + entry + '\'' +
                ", ruleSets=" + ruleSets +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.contact, 0);
        dest.writeString(this.baseLanguage);
        dest.writeTypedList(actionSets);
        dest.writeLong(lastSaved != null ? lastSaved.getTime() : -1);
        dest.writeValue(this.version);
        dest.writeString(this.type);
        dest.writeString(this.entry);
        dest.writeTypedList(ruleSets);
        dest.writeParcelable(this.metadata, 0);
        dest.writeParcelable(this.flowRun, 0);
    }

    public FlowDefinition() {
    }

    protected FlowDefinition(Parcel in) {
        this.contact = in.readParcelable(Contact.class.getClassLoader());
        this.baseLanguage = in.readString();
        this.actionSets = in.createTypedArrayList(FlowActionSet.CREATOR);
        long tmpLastSaved = in.readLong();
        this.lastSaved = tmpLastSaved == -1 ? null : new Date(tmpLastSaved);
        this.version = (Integer) in.readValue(Integer.class.getClassLoader());
        this.type = in.readString();
        this.entry = in.readString();
        this.ruleSets = in.createTypedArrayList(FlowRuleset.CREATOR);
        this.metadata = in.readParcelable(FlowMetadata.class.getClassLoader());
        this.flowRun = in.readParcelable(FlowRun.class.getClassLoader());
    }

    public static final Creator<FlowDefinition> CREATOR = new Creator<FlowDefinition>() {
        public FlowDefinition createFromParcel(Parcel source) {
            return new FlowDefinition(source);
        }

        public FlowDefinition[] newArray(int size) {
            return new FlowDefinition[size];
        }
    };
}
