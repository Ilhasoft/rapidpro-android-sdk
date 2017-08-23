package io.fcmchannel.sdk.core.models.v1;

import android.os.Parcel;

import java.util.List;

import io.fcmchannel.sdk.core.models.base.ContactBase;

/**
 * Created by johncordeiro on 18/08/15.
 */
public class Contact extends ContactBase {

    private List<String> groups;

    public List<String> getGroups() {
        return groups;
    }

    public Contact setGroups(List<String> groups) {
        this.groups = groups;
        return this;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeStringList(this.groups);
    }

    public Contact() {
    }

    protected Contact(Parcel in) {
        super(in);
        this.groups = in.createStringArrayList();
    }

    public static final Creator<Contact> CREATOR = new Creator<Contact>() {
        @Override
        public Contact createFromParcel(Parcel source) {
            return new Contact(source);
        }

        @Override
        public Contact[] newArray(int size) {
            return new Contact[size];
        }
    };
}
