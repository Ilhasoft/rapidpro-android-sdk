package io.rapidpro.sdk.core.models.v2;

import android.os.Parcel;

import java.util.List;

import io.rapidpro.sdk.core.models.Group;
import io.rapidpro.sdk.core.models.base.ContactBase;

/**
 * Created by John Cordeiro on 5/18/17.
 * Copyright © 2017 rapidpro-android-sdk, Inc. All rights reserved.
 */

public class Contact extends ContactBase {

    private List<Group> groups;

    public List<Group> getGroups() {
        return groups;
    }

    public Contact setGroups(List<Group> groups) {
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
        dest.writeTypedList(this.groups);
    }

    public Contact() {
    }

    protected Contact(Parcel in) {
        super(in);
        this.groups = in.createTypedArrayList(Group.CREATOR);
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
