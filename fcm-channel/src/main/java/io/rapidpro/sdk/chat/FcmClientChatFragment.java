package io.rapidpro.sdk.chat;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.Date;
import java.util.List;

import io.rapidpro.sdk.FcmClient;
import io.rapidpro.sdk.R;
import io.rapidpro.sdk.chat.tags.OnTagClickListener;
import io.rapidpro.sdk.chat.tags.TagsAdapter;
import io.rapidpro.sdk.core.managers.FlowRunnerManager;
import io.rapidpro.sdk.core.models.FlowRuleset;
import io.rapidpro.sdk.core.models.Message;
import io.rapidpro.sdk.core.models.Type;
import io.rapidpro.sdk.services.FcmClientIntentService;
import io.rapidpro.sdk.services.FcmClientRegistrationIntentService;
import io.rapidpro.sdk.util.BundleHelper;
import io.rapidpro.sdk.util.SpaceItemDecoration;

/**
 * Created by john-mac on 8/30/16.
 */
public class FcmClientChatFragment extends Fragment implements FcmClientChatView {

    private EditText message;
    private RecyclerView messageList;
    private RecyclerView tags;
    private ProgressBar progressBar;

    private ChatMessagesAdapter adapter;

    private FcmClientChatPresenter presenter;

    public static boolean visible = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        cleanUnreadMessages();
        return inflater.inflate(R.layout.fcm_client_fragment_chat, container, false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        cleanUnreadMessages();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupView(view);

        presenter = new FcmClientChatPresenter(this);
        presenter.loadMessages();
    }

    private void cleanUnreadMessages() {
        FcmClient.getPreferences().setUnreadMessages(0).commit();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        visible = isVisibleToUser;
    }

    @Override
    public void onPause() {
        super.onPause();
        visible = false;
    }

    @Override
    public void onResume() {
        super.onResume();
        visible = true;
    }

    @SuppressWarnings("ConstantConditions")
    private void setupView(View view) {
        message = (EditText) view.findViewById(R.id.message);
        adapter = new ChatMessagesAdapter();

        tags = (RecyclerView) view.findViewById(R.id.tags);
        int spacing = tags.getPaddingBottom();

        SpaceItemDecoration messagesItemDecoration = new SpaceItemDecoration();
        messagesItemDecoration.setVerticalSpaceHeight(spacing);

        messageList = (RecyclerView) view.findViewById(R.id.messageList);
        messageList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, true));
        messageList.addItemDecoration(messagesItemDecoration);
        messageList.setAdapter(adapter);

        SpaceItemDecoration tagsItemDecoration = new SpaceItemDecoration();
        tagsItemDecoration.setHorizontalSpaceWidth(spacing);

        tags.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        tags.addItemDecoration(tagsItemDecoration);

        ImageView sendMessage = (ImageView) view.findViewById(R.id.sendMessage);
        sendMessage.setOnClickListener(onSendMessageClickListener);

        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
    }

    @Override
    public void onStart() {
        super.onStart();
        registerBroadcasts(getContext());
    }

    public void registerBroadcasts(Context context) {
        if (context != null) {
            LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(context);

            IntentFilter registrationFilter = new IntentFilter(FcmClientRegistrationIntentService.REGISTRATION_COMPLETE);
            localBroadcastManager.registerReceiver(onRegisteredReceiver, registrationFilter);

            IntentFilter messagesBroadcastFilter = new IntentFilter(FcmClientIntentService.ACTION_MESSAGE_RECEIVED);
            localBroadcastManager.registerReceiver(messagesReceiver, messagesBroadcastFilter);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        unregisterBroadcasts(getContext());
    }

    public void unregisterBroadcasts(Context context) {
        try {
            LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(context);
            localBroadcastManager.unregisterReceiver(messagesReceiver);
            localBroadcastManager.unregisterReceiver(onRegisteredReceiver);
        } catch(Exception exception) {
            Log.e("FcmClientChat", "onStop: ", exception);
        }
    }

    private BroadcastReceiver messagesReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle data = intent.getBundleExtra(FcmClientIntentService.KEY_DATA);
            presenter.loadMessage(data);

            Message message = BundleHelper.getMessage(data);
            message.setCreatedOn(new Date());
            adapter.addChatMessage(message);
            onLastMessageChanged();
        }
    };

    @Override
    public void onMessagesLoaded(List<Message> messages) {
        adapter.setMessages(messages);
        onLastMessageChanged();
    }

    @Override
    public void onMessageLoaded(Message message) {
        adapter.addChatMessage(message);
        onLastMessageChanged();
    }

    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void dismissLoading() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showMessage(int messageId) {
        Context context = getContext();
        if (context != null) {
            showMessage(context.getString(messageId));
        }
    }

    @Override
    public void setCurrentRulesets(FlowRuleset rulesets) {
        if (rulesets != null && rulesets.getRules() != null) {
            Type type = presenter.getFirstType(rulesets);
            if (type == Type.Choice) {
                TagsAdapter tagsAdapter = new TagsAdapter(rulesets.getRules(), onTagClickListener);
                tags.setAdapter(tagsAdapter);
                tags.setVisibility(View.VISIBLE);
            } else {
                message.setInputType(FlowRunnerManager.getInputTypeByType(type));
                tags.setVisibility(View.GONE);
            }
        } else {
            tags.setVisibility(View.GONE);
        }
    }

    private void onLastMessageChanged() {
        messageList.scrollToPosition(0);
        presenter.loadCurrentRulesets();
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public Message getLastMessage() {
        return adapter.getLastMessage();
    }

    private View.OnClickListener onSendMessageClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String messageText = message.getText().toString();
            if (!messageText.isEmpty()) {
                presenter.sendMessage(messageText);
            } else {
                message.setError(getContext().getString(R.string.fcm_client_error_send_message));
            }
        }
    };

    @Override
    public void addNewMessage(String messageText) {
        restoreView();

        adapter.addChatMessage(presenter.createChatMessage(messageText));
        messageList.scrollToPosition(0);
    }

    private void restoreView() {
        message.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        message.setError(null);
        message.setText(null);
        tags.setVisibility(View.GONE);
    }

    private BroadcastReceiver onRegisteredReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            presenter.loadMessages();
        }
    };

    private OnTagClickListener onTagClickListener = new OnTagClickListener() {
        @Override
        public void onTagClick(String reply) {
            presenter.sendMessage(reply);
        }
    };
}
