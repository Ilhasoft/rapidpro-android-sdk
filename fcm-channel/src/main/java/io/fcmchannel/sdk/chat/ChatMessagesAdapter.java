package io.fcmchannel.sdk.chat;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import io.fcmchannel.sdk.FcmClient;
import io.fcmchannel.sdk.UiConfiguration;
import io.fcmchannel.sdk.chat.tags.OnTagClickListener;
import io.fcmchannel.sdk.core.models.FlowRuleset;
import io.fcmchannel.sdk.core.models.Message;

/**
 * Created by johncordeiro on 7/21/15.
 */
class ChatMessagesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int VIEW_TYPE_TAGS = 0;
    private static final int VIEW_TYPE_TEXT_MESSAGES = 1;

    private FlowRuleset rulesets;
    private List<Message> chatMessages;
    private final int iconResource;

    private ChatMessageViewHolder.OnChatMessageSelectedListener onChatMessageSelectedListener;
    private final OnTagClickListener onTagClickListener;

    ChatMessagesAdapter(OnTagClickListener onTagClickListener) {
        this.onTagClickListener = onTagClickListener;
        this.chatMessages = new ArrayList<>();
        this.iconResource = FcmClient.getUiConfiguration().getIconResource() != UiConfiguration.INVALID_VALUE ?
                FcmClient.getUiConfiguration().getIconResource() : FcmClient.getAppIcon();
        setHasStableIds(true);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case VIEW_TYPE_TAGS:
                return new RulesViewHolder(parent.getContext(), parent, onTagClickListener);
            case VIEW_TYPE_TEXT_MESSAGES:
                return new ChatMessageViewHolder(parent.getContext(), parent, iconResource);
            default:
                throw new IllegalStateException("View type not recognized by ChatMessagesAdapter");
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case VIEW_TYPE_TAGS:
                RulesViewHolder rulesViewHolder = ((RulesViewHolder)holder);
                rulesViewHolder.bind(rulesets);
                break;
            case VIEW_TYPE_TEXT_MESSAGES:
                ChatMessageViewHolder chatMessageViewHolder = ((ChatMessageViewHolder)holder);
                chatMessageViewHolder.setOnChatMessageSelectedListener(onChatMessageSelectedListener);
                chatMessageViewHolder.bindView(getItem(getPositionFromMessage(position)));
        }
    }

    private Message getItem(int position) {
        return chatMessages.get(position);
    }

    @Override
    public int getItemViewType(int position) {
        return hasRulesets() && position == 0 ? VIEW_TYPE_TAGS : VIEW_TYPE_TEXT_MESSAGES;
    }

    @Override
    public long getItemId(int position) {
        switch (getItemViewType(position)) {
            case VIEW_TYPE_TAGS:
                return rulesets.getUuid().hashCode();
            case VIEW_TYPE_TEXT_MESSAGES:
                Message chatMessage = getItem(getPositionFromMessage(position));
                return chatMessage.getId() != null ? chatMessage.getId().hashCode() : 0;
            default:
                return super.getItemId(position);
        }
    }

    @Override
    public int getItemCount() {
        return hasRulesets() ? chatMessages.size() + 1 : chatMessages.size();
    }

    void setRulesets(FlowRuleset rulesets) {
        this.rulesets = rulesets;
        notifyDataSetChanged();
    }

    void removeRulesets() {
        if (hasRulesets()) {
            rulesets = null;
            notifyDataSetChanged();
        }
    }

    Message getLastMessage() {
        return chatMessages.isEmpty() ? null : chatMessages.get(0);
    }

    private int getPositionFromMessage(int position) {
        return hasRulesets() ? position - 1 : position;
    }

    void setMessages(List<Message> messages) {
        this.chatMessages = messages;
        notifyDataSetChanged();
    }

    void addChatMessage(Message message) {
        int location = chatMessages.indexOf(message);
        if (location >= 0) {
            chatMessages.set(location, message);
            notifyItemChanged(location);
        } else {
            chatMessages.add(0, message);
            notifyItemInserted(hasRulesets() ? 1 : 0);
        }
    }

    private boolean hasRulesets() {
        return rulesets != null;
    }

    public void removeChatMessage(Message message) {
        int indexOfMessage = chatMessages.indexOf(message);
        if(indexOfMessage >= 0) {
            chatMessages.remove(indexOfMessage);
            notifyItemRemoved(indexOfMessage);
        }
    }

    public void setOnChatMessageSelectedListener(ChatMessageViewHolder.OnChatMessageSelectedListener onChatMessageSelectedListener) {
        this.onChatMessageSelectedListener = onChatMessageSelectedListener;
    }
}
