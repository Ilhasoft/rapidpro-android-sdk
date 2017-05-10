package io.rapidpro.sdk.core.models;

import android.support.annotation.StringDef;

import com.google.gson.annotations.SerializedName;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Date;
import java.util.List;

/**
 * Created by johncordeiro on 01/09/15.
 */
public class Message {

    public static final String DIRECTION_INCOMING = "in";
    public static final String DIRECTION_OUTGOING = "out";

    @Retention(RetentionPolicy.SOURCE)
    @StringDef({DIRECTION_INCOMING, DIRECTION_OUTGOING})
    public @interface Direction{}

    private Integer id;

    private Channel channel;

    private Integer broadcast;

    private Contact contact;

    private String urn;

    @Direction
    private String direction;

    private String type;

    private String status;

    private String visibility;

    private String text;

    private List<Label> labels;

    @SerializedName("created_on")
    private Date createdOn;

    @SerializedName("sent_on")
    private Date sentOn;

    private FlowRuleset ruleset;

    public Integer getId() {
        return id;
    }

    public Message setId(Integer id) {
        this.id = id;
        return this;
    }

    public Channel getChannel() {
        return channel;
    }

    public Message setChannel(Channel channel) {
        this.channel = channel;
        return this;
    }

    public Integer getBroadcast() {
        return broadcast;
    }

    public Message setBroadcast(Integer broadcast) {
        this.broadcast = broadcast;
        return this;
    }

    public Contact getContact() {
        return contact;
    }

    public Message setContact(Contact contact) {
        this.contact = contact;
        return this;
    }

    public String getUrn() {
        return urn;
    }

    public Message setUrn(String urn) {
        this.urn = urn;
        return this;
    }

    @Direction
    public String getDirection() {
        return direction;
    }

    public Message setDirection(@Direction String direction) {
        this.direction = direction;
        return this;
    }

    public String getType() {
        return type;
    }

    public Message setType(String type) {
        this.type = type;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public Message setStatus(String status) {
        this.status = status;
        return this;
    }

    public String getVisibility() {
        return visibility;
    }

    public Message setVisibility(String visibility) {
        this.visibility = visibility;
        return this;
    }

    public String getText() {
        return text;
    }

    public Message setText(String text) {
        this.text = text;
        return this;
    }

    public List<Label> getLabels() {
        return labels;
    }

    public Message setLabels(List<Label> labels) {
        this.labels = labels;
        return this;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public Message setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
        return this;
    }

    public Date getSentOn() {
        return sentOn;
    }

    public Message setSentOn(Date sentOn) {
        this.sentOn = sentOn;
        return this;
    }

    public FlowRuleset getRuleset() {
        return ruleset;
    }

    public Message setRuleset(FlowRuleset ruleset) {
        this.ruleset = ruleset;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Message message = (Message) o;
        return id != null ? id.equals(message.id) : message.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
