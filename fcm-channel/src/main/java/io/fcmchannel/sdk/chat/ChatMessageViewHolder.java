package io.fcmchannel.sdk.chat;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.support.v7.widget.RecyclerView;
import android.text.util.Linkify;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DateFormat;

import io.fcmchannel.sdk.R;
import io.fcmchannel.sdk.core.models.Message;

/**
 * Created by john-mac on 4/11/16.
 */
class ChatMessageViewHolder extends RecyclerView.ViewHolder {

    private Message chatMessage;

    private ViewGroup parent;

    private TextView message;
    private TextView date;
    private ImageView icon;

    private OnChatMessageSelectedListener onChatMessageSelectedListener;

    private final DateFormat hourFormatter;
    private final Context context;

    private final int leftMarginIncoming;
    private final int leftMarginOutgoing;
    private final int bottomMarginIncoming;
    private final int bottomMarginOutgoing;

    ChatMessageViewHolder(Context context, ViewGroup parent, @DrawableRes int iconRes) {
        super(LayoutInflater.from(context).inflate(R.layout.fcm_client_item_chat_message, parent, false));
        this.context = context;
        this.hourFormatter = DateFormat.getTimeInstance(DateFormat.SHORT);
        this.parent = (ViewGroup) itemView.findViewById(R.id.bubble);

        this.message = (TextView) itemView.findViewById(R.id.chatMessage);
        this.date = (TextView) itemView.findViewById(R.id.chatMessageDate);

        this.icon = (ImageView) itemView.findViewById(R.id.icon);
        this.icon.setImageResource(iconRes);

        this.itemView.setOnLongClickListener(onLongClickListener);

        this.leftMarginIncoming = getDpDimen(10);
        this.leftMarginOutgoing = getDpDimen(45);
        this.bottomMarginIncoming = getDpDimen(3);
        this.bottomMarginOutgoing = getDpDimen(15);
    }

    void bindView(Message chatMessage) {
        this.chatMessage = chatMessage;
        message.setText(chatMessage.getText());
        Linkify.addLinks(message, Linkify.ALL);
        date.setText(hourFormatter.format(chatMessage.getCreatedOn()));

        bindContainer(chatMessage);
    }

    private void bindContainer(Message chatMessage) {
        boolean incoming = isIncoming(chatMessage);
        icon.setVisibility(incoming ? View.GONE : View.VISIBLE);

        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) parent.getLayoutParams();
        setupBubblePosition(incoming, params);
        parent.setLayoutParams(params);

        int drawable = incoming ? R.drawable.fcm_client_bubble_me : R.drawable.fcm_client_bubble_other;
        parent.setBackgroundResource(drawable);

        int textColor = incoming ? Color.BLACK : Color.WHITE;
        message.setTextColor(textColor);
    }

    private void setupBubblePosition(boolean incoming, FrameLayout.LayoutParams params) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            params.gravity = incoming ? Gravity.END : Gravity.START;
            params.setMarginStart(incoming ? leftMarginIncoming : leftMarginOutgoing);
        } else {
            params.gravity = incoming ? Gravity.RIGHT : Gravity.LEFT;
            params.leftMargin = incoming ? leftMarginIncoming : leftMarginOutgoing;
        }
        params.bottomMargin = incoming ? bottomMarginIncoming : bottomMarginOutgoing;
    }

    private boolean isIncoming(Message chatMessage) {
        return chatMessage.getDirection().equals(Message.DIRECTION_INCOMING);
    }

    private int getDpDimen(int value) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value
                , context.getResources().getDisplayMetrics());
    }

    private View.OnLongClickListener onLongClickListener = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View view) {
            if(onChatMessageSelectedListener != null) {
                onChatMessageSelectedListener.onChatMessageSelected(chatMessage);
            }
            return false;
        }
    };

    void setOnChatMessageSelectedListener(OnChatMessageSelectedListener onChatMessageSelectedListener) {
        this.onChatMessageSelectedListener = onChatMessageSelectedListener;
    }

    interface OnChatMessageSelectedListener {
        void onChatMessageSelected(Message chatMessage);
    }
}
