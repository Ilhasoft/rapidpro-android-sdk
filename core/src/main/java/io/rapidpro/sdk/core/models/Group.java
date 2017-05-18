package io.rapidpro.sdk.core.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by johncordeiro on 18/08/15.
 */
public class Group implements Parcelable {

    private Integer group;

    private String uuid;

    private String name;

    private Integer size;

    public Integer getGroup() {
        return group;
    }

    public void setGroup(Integer group) {
        this.group = group;
    }

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

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return "Group{" +
                "group=" + group +
                ", uuid='" + uuid + '\'' +
                ", name='" + name + '\'' +
                ", size=" + size +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.group);
        dest.writeString(this.uuid);
        dest.writeString(this.name);
        dest.writeValue(this.size);
    }

    public Group() {
    }

    protected Group(Parcel in) {
        this.group = (Integer) in.readValue(Integer.class.getClassLoader());
        this.uuid = in.readString();
        this.name = in.readString();
        this.size = (Integer) in.readValue(Integer.class.getClassLoader());
    }

    public static final Creator<Group> CREATOR = new Creator<Group>() {
        @Override
        public Group createFromParcel(Parcel source) {
            return new Group(source);
        }

        @Override
        public Group[] newArray(int size) {
            return new Group[size];
        }
    };
}
