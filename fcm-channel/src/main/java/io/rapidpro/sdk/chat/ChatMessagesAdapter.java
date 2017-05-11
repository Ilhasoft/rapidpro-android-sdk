package io.rapidpro.sdk.chat;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import io.rapidpro.sdk.FcmClient;
import io.rapidpro.sdk.core.models.Message;

/**
 * Created by johncordeiro on 7/21/15.
 */
public class ChatMessagesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Message> chatMessages;

    private ChatMessageViewHolder.OnChatMessageSelectedListener onChatMessageSelectedListener;

    public ChatMessagesAdapter() {
        this.chatMessages = new ArrayList<>();
        setHasStableIds(true);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ChatMessageViewHolder(parent.getContext(), parent, FcmClient.getUiConfiguration().getIconResource());
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ChatMessageViewHolder chatMessageViewHolder = ((ChatMessageViewHolder)holder);
        chatMessageViewHolder.setOnChatMessageSelectedListener(onChatMessageSelectedListener);
        chatMessageViewHolder.bindView(chatMessages.get(position));
    }

    @Override
    public long getItemId(int position) {
        Message chatMessage = chatMessages.get(position);
        return chatMessage.getId() != null ? chatMessage.getId().hashCode() : 0;
    }

    @Override
    public int getItemCount() {
        return chatMessages.size();
    }

    public Message getLastMessage() {
        return chatMessages.isEmpty() ? null : chatMessages.get(0);
    }

    public void setMessages(List<Message> messages) {
        this.chatMessages = messages;
        notifyDataSetChanged();
    }

    public void addChatMessage(Message message) {
        int location = chatMessages.indexOf(message);
        if (location >= 0) {
            chatMessages.set(location, message);
            notifyItemChanged(location);
        } else {
            chatMessages.add(0, message);
            notifyItemInserted(0);
        }
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
