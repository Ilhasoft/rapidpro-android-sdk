package io.rapidpro.sdk.sample;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import io.rapidpro.sdk.FcmClient;
import io.rapidpro.sdk.services.FcmClientRegistrationIntentService;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (FcmClient.isContactRegistered()) {
            FcmClient.startFcmClientChatActivity(this);
        }
        setupView();
//        registerReceiver();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        unregisterReceiver(broadcastReceiver);
    }

    private void registerReceiver() {
        IntentFilter intentFilter = new IntentFilter(FcmClientRegistrationIntentService.REGISTRATION_COMPLETE);
        registerReceiver(broadcastReceiver, intentFilter);
    }

    private void setupView() {
        final EditText editText = (EditText) findViewById(R.id.email);
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String urn = editText.getText().toString().trim();
                if (!TextUtils.isEmpty(urn)) {
                    FcmClient.registerContact(urn);
                    FcmClient.startFcmClientChatActivity(LoginActivity.this);
                }
            }
        });
    }

    private final BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            FcmClient.startFcmClientChatActivity(LoginActivity.this);
        }
    };

}
