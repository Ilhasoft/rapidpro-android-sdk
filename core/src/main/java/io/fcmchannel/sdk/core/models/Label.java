package io.fcmchannel.sdk.core.models;

/**
 * Created by john-mac on 6/29/16.
 */
public class Label {

    private String uuid;

    private String name;

    public String getUuid() {
        return uuid;
    }

    public Label setUuid(String uuid) {
        this.uuid = uuid;
        return this;
    }

    public String getName() {
        return name;
    }

    public Label setName(String name) {
        this.name = name;
        return this;
    }
}
