package io.fcmchannel.sdk.chat.menu;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import io.fcmchannel.sdk.FcmClient;
import io.fcmchannel.sdk.R;
import io.fcmchannel.sdk.chat.FcmClientChatFragment;
import io.mattcarroll.hover.Content;

/**
 * A Hover menu screen that does not take up the entire screen.
 */
class FcmClientContent implements Content {

    private final Context mContext;
    private final FcmClientChatFragment fragment;
    private ViewGroup rootView;
    private View chatFragmentView;

    private boolean created = false;

    FcmClientContent(@NonNull Context context) {
        mContext = context.getApplicationContext();
        fragment = new FcmClientChatFragment();
    }

    @Override
    public boolean isFullscreen() {
        return false;
    }

    @NonNull
    @Override
    public View getView() {
        if (null == rootView) {
            int height = (int) mContext.getResources().getDimension(R.dimen.fcm_client_chat_height);

            LayoutInflater inflater = LayoutInflater.from(mContext);
            rootView = (ViewGroup) inflater.inflate(R.layout.fcm_client_chat_box, null);

            TextView title = rootView.findViewById(R.id.title);
            title.setText(FcmClient.getUiConfiguration().getTitleString());

            ViewGroup content = rootView.findViewById(R.id.content);
            chatFragmentView = inflater.inflate(R.layout.fcm_client_fragment_chat, null);
            chatFragmentView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height));
            content.addView(chatFragmentView);
        }
        return rootView;
    }

    @Override
    public void onShown() {
        if (!created) {
            fragment.onViewCreated(chatFragmentView, null);
            created = true;
        }
        fragment.registerBroadcasts(mContext);
    }

    @Override
    public void onHidden() {
        fragment.unregisterBroadcasts(mContext);
    }

}
