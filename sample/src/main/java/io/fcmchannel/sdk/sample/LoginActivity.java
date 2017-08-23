package io.fcmchannel.sdk.sample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import io.fcmchannel.sdk.FcmClient;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (FcmClient.isContactRegistered()) {
            startChatBot();
        }
        setupView();
    }

    private void setupView() {
        final EditText editText = (EditText) findViewById(R.id.email);
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String urn = editText.getText().toString().trim();
                if (!TextUtils.isEmpty(urn)) {
                    FcmClient.registerContact(urn);
                    startChatBot();
                }
            }
        });
    }

    private void startChatBot() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

}
