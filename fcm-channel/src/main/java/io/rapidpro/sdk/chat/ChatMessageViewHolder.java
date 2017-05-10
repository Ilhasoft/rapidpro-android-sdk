package io.rapidpro.sdk.chat;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.DateFormat;

import io.rapidpro.sdk.R;
import io.rapidpro.sdk.core.models.Message;
import io.rapidpro.sdk.util.PicassoImageGetter;

/**
 * Created by john-mac on 4/11/16.
 */
public class ChatMessageViewHolder extends RecyclerView.ViewHolder {

    private Message chatMessage;

    private ViewGroup parent;

    private TextView message;
    private TextView date;
    private ImageView icon;

    private OnChatMessageSelectedListener onChatMessageSelectedListener;

    private final DateFormat hourFormatter;
    private final Context context;
    private final PicassoImageGetter imageGetter;

    public ChatMessageViewHolder(Context context, ViewGroup parent, @DrawableRes int iconRes) {
        super(LayoutInflater.from(context).inflate(R.layout.fcm_client_item_chat_message, parent, false));
        this.context = context;
        this.hourFormatter = DateFormat.getTimeInstance(DateFormat.SHORT);
        this.parent = (ViewGroup) itemView.findViewById(R.id.bubble);

        this.message = (TextView) itemView.findViewById(R.id.chatMessage);
        this.message.setMovementMethod(LinkMovementMethod.getInstance());
        this.date = (TextView) itemView.findViewById(R.id.chatMessageDate);

        this.icon = (ImageView) itemView.findViewById(R.id.icon);
        this.icon.setImageResource(iconRes);

        this.itemView.setOnLongClickListener(onLongClickListener);
        this.imageGetter = new PicassoImageGetter(message, R.drawable.fcm_client_ic_error_outline_grey);
    }

    public void bindView(Message chatMessage) {
        this.chatMessage = chatMessage;
        message.setText(Html.fromHtml(chatMessage.getText(), imageGetter, null));
        date.setText(hourFormatter.format(chatMessage.getCreatedOn()));

        bindContainer(chatMessage);
    }

    private void bindContainer(Message chatMessage) {
        boolean userAuthor = isUserAuthor(chatMessage);
        icon.setVisibility(userAuthor ? View.GONE : View.VISIBLE);

        int smallSpace = getDpDimen(10);
        int largeSpace = getDpDimen(40);

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT
                , ViewGroup.LayoutParams.WRAP_CONTENT);
        addRule(userAuthor, params);
        params.leftMargin = userAuthor ? largeSpace : smallSpace;
        params.rightMargin = userAuthor ? smallSpace : largeSpace;
        parent.setLayoutParams(params);

        int drawable = userAuthor ? R.drawable.fcm_client_bubble_me : R.drawable.fcm_client_bubble_other;
        parent.setBackgroundResource(drawable);

        int textColor = userAuthor ? Color.BLACK : Color.WHITE;
        message.setTextColor(textColor);
    }

    private boolean isUserAuthor(Message chatMessage) {
        return chatMessage.getDirection().equals(Message.DIRECTION_INCOMING);
    }

    private void addRule(boolean userAuthor, RelativeLayout.LayoutParams params) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            if (!userAuthor) {
                params.addRule(RelativeLayout.END_OF, R.id.icon);
            } else {
                params.addRule(RelativeLayout.ALIGN_PARENT_END);
            }
        } else {
            if (!userAuthor) {
                params.addRule(RelativeLayout.RIGHT_OF, R.id.icon);
            } else {
                params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            }
        }

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

    public void setOnChatMessageSelectedListener(OnChatMessageSelectedListener onChatMessageSelectedListener) {
        this.onChatMessageSelectedListener = onChatMessageSelectedListener;
    }

    public interface OnChatMessageSelectedListener {
        void onChatMessageSelected(Message chatMessage);
    }
}
