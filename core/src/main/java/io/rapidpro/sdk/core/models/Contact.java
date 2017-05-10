package io.rapidpro.sdk.core.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by johncordeiro on 18/08/15.
 */
public class Contact implements Parcelable {

    private String uuid;

    private String name;

    private String language;

    private String email;

    private List<String> groups;

    private List<String> urns;

    private HashMap<String, Object> fields;

    private Date modified_on;

    private String phone;

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

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public List<String> getGroups() {
        return groups;
    }

    public void setGroups(List<String> groups) {
        this.groups = groups;
    }

    public List<String> getUrns() {
        return urns;
    }

    public void setUrns(List<String> urns) {
        this.urns = urns;
    }

    public HashMap<String, Object> getFields() {
        return fields;
    }

    public void setFields(HashMap<String, Object> fields) {
        this.fields = fields;
    }

    public Date getModified_on() {
        return modified_on;
    }

    public void setModified_on(Date modified_on) {
        this.modified_on = modified_on;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    @Override
    public String toString() {
        return "Contact{" +
                "name='" + name + '\'' +
                ", language='" + language + '\'' +
                ", groups=" + groups +
                ", email=" + email +
                ", urns=" + urns +
                ", fields=" + fields +
                ", modified_on=" + modified_on +
                ", phone='" + phone + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.uuid);
        dest.writeString(this.name);
        dest.writeString(this.language);
        dest.writeStringList(this.groups);
        dest.writeStringList(this.urns);
        dest.writeSerializable(this.fields);
        dest.writeLong(modified_on != null ? modified_on.getTime() : -1);
        dest.writeString(this.phone);
        dest.writeString(this.email);
    }

    public Contact() {
    }

    protected Contact(Parcel in) {
        this.uuid = in.readString();
        this.name = in.readString();
        this.language = in.readString();
        this.groups = in.createStringArrayList();
        this.urns = in.createStringArrayList();
        this.fields = (HashMap<String, Object>) in.readSerializable();
        long tmpModified_on = in.readLong();
        this.modified_on = tmpModified_on == -1 ? null : new Date(tmpModified_on);
        this.phone = in.readString();
        this.email = in.readString();
    }

    public static final Creator<Contact> CREATOR = new Creator<Contact>() {
        public Contact createFromParcel(Parcel source) {
            return new Contact(source);
        }

        public Contact[] newArray(int size) {
            return new Contact[size];
        }
    };
}
