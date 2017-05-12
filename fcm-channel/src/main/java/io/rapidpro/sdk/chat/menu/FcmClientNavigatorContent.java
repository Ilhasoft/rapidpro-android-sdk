package io.rapidpro.sdk.chat.menu;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import io.mattcarroll.hover.Navigator;
import io.mattcarroll.hover.NavigatorContent;
import io.rapidpro.sdk.FcmClient;
import io.rapidpro.sdk.R;
import io.rapidpro.sdk.chat.FcmClientChatFragment;

/**
 * A Hover menu screen that does not take up the entire screen.
 */
public class FcmClientNavigatorContent implements NavigatorContent {

    private final Context mContext;
    private final FcmClientChatFragment fragment;
    private ViewGroup rootView;

    public FcmClientNavigatorContent(@NonNull Context context) {
        mContext = context.getApplicationContext();
        fragment = new FcmClientChatFragment();
    }

    @NonNull
    @Override
    public View getView() {
        if (null == rootView) {
            int height = (int) mContext.getResources().getDimension(R.dimen.fcm_client_chat_height);

            LayoutInflater inflater = LayoutInflater.from(mContext);
            rootView = (ViewGroup) inflater.inflate(R.layout.fcm_client_chat_box, null);

            TextView title = (TextView) rootView.findViewById(R.id.title);
            title.setText(FcmClient.getUiConfiguration().getTitleString());

            ViewGroup content = (ViewGroup) rootView.findViewById(R.id.content);
            View view = inflater.inflate(R.layout.fcm_client_fragment_chat, null);
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height));
            content.addView(view);

            fragment.onViewCreated(view, null);
        }
        return rootView;
    }

    @Override
    public void onShown(@NonNull Navigator navigator) {
        fragment.registerBroadcasts(mContext);
    }

    @Override
    public void onHidden() {
        fragment.unregisterBroadcasts(mContext);
    }
}
