package io.rapidpro.sdk.core.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

import io.rapidpro.sdk.core.models.v2.Contact;

/**
 * Created by johncordeiro on 13/10/15.
 */
public class FlowDefinition implements Parcelable {

    private String version;

    private String site;

    private List<Flow> flows;

    private transient Contact contact;

    private transient FlowRun flowRun;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public List<Flow> getFlows() {
        return flows;
    }

    public void setFlows(List<Flow> flows) {
        this.flows = flows;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public FlowRun getFlowRun() {
        return flowRun;
    }

    public void setFlowRun(FlowRun flowRun) {
        this.flowRun = flowRun;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.version);
        dest.writeString(this.site);
        dest.writeTypedList(this.flows);
    }

    public FlowDefinition() {
    }

    protected FlowDefinition(Parcel in) {
        this.version = in.readString();
        this.site = in.readString();
        this.flows = in.createTypedArrayList(Flow.CREATOR);
    }

    public static final Creator<FlowDefinition> CREATOR = new Creator<FlowDefinition>() {
        @Override
        public FlowDefinition createFromParcel(Parcel source) {
            return new FlowDefinition(source);
        }

        @Override
        public FlowDefinition[] newArray(int size) {
            return new FlowDefinition[size];
        }
    };
}
