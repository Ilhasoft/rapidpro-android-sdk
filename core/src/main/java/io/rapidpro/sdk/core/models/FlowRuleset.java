package io.rapidpro.sdk.core.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by johncordeiro on 13/10/15.
 */
public class FlowRuleset implements Parcelable {

    private String uuid;

    @SerializedName("webhook_action")
    private String webhookAction;

    private List<FlowRule> rules;

    private String webhook;

    @SerializedName("ruleset_type")
    private String rulesetType;

    private String label;

    private String operand;

    @SerializedName("label_key")
    private String labelKey;

    @SerializedName("response_key")
    private String responseType;

    @SerializedName("x")
    private Integer positionX;

    @SerializedName("y")
    private Integer positionY;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getWebhookAction() {
        return webhookAction;
    }

    public void setWebhookAction(String webhookAction) {
        this.webhookAction = webhookAction;
    }

    public List<FlowRule> getRules() {
        return rules;
    }

    public void setRules(List<FlowRule> rules) {
        this.rules = rules;
    }

    public String getWebhook() {
        return webhook;
    }

    public void setWebhook(String webhook) {
        this.webhook = webhook;
    }

    public String getRulesetType() {
        return rulesetType;
    }

    public void setRulesetType(String rulesetType) {
        this.rulesetType = rulesetType;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getOperand() {
        return operand;
    }

    public void setOperand(String operand) {
        this.operand = operand;
    }

    public String getLabelKey() {
        return labelKey;
    }

    public void setLabelKey(String labelKey) {
        this.labelKey = labelKey;
    }

    public String getResponseType() {
        return responseType;
    }

    public void setResponseType(String responseType) {
        this.responseType = responseType;
    }

    public Integer getPositionX() {
        return positionX;
    }

    public void setPositionX(Integer positionX) {
        this.positionX = positionX;
    }

    public Integer getPositionY() {
        return positionY;
    }

    public void setPositionY(Integer positionY) {
        this.positionY = positionY;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.uuid);
        dest.writeString(this.webhookAction);
        dest.writeTypedList(rules);
        dest.writeString(this.webhook);
        dest.writeString(this.rulesetType);
        dest.writeString(this.label);
        dest.writeString(this.operand);
        dest.writeString(this.labelKey);
        dest.writeString(this.responseType);
        dest.writeValue(this.positionX);
        dest.writeValue(this.positionY);
    }

    public FlowRuleset() {
    }

    protected FlowRuleset(Parcel in) {
        this.uuid = in.readString();
        this.webhookAction = in.readString();
        this.rules = in.createTypedArrayList(FlowRule.CREATOR);
        this.webhook = in.readString();
        this.rulesetType = in.readString();
        this.label = in.readString();
        this.operand = in.readString();
        this.labelKey = in.readString();
        this.responseType = in.readString();
        this.positionX = (Integer) in.readValue(Integer.class.getClassLoader());
        this.positionY = (Integer) in.readValue(Integer.class.getClassLoader());
    }

    public static final Creator<FlowRuleset> CREATOR = new Creator<FlowRuleset>() {
        public FlowRuleset createFromParcel(Parcel source) {
            return new FlowRuleset(source);
        }

        public FlowRuleset[] newArray(int size) {
            return new FlowRuleset[size];
        }
    };
}
