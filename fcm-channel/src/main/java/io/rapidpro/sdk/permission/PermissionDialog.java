package io.rapidpro.sdk.permission;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import io.rapidpro.sdk.FcmClient;
import io.rapidpro.sdk.R;
import io.rapidpro.sdk.util.BitmapHelper;

/**
 * Created by John Cordeiro on 5/19/17.
 * Copyright © 2017 rapidpro-android-sdk, Inc. All rights reserved.
 */

public class PermissionDialog extends Dialog {

    public PermissionDialog(@NonNull Context context) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.fcm_client_float_permission);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupView();
    }

    private void setupView() {
        TextView permissionMessageView = (TextView) findViewById(R.id.fcm_client_permission_message);
        String permissionMessage = FcmClient.getUiConfiguration().getPermissionMessage();
        if (!TextUtils.isEmpty(permissionMessage)) {
            permissionMessageView.setText(permissionMessage);
        }

        ImageView permissionIcon = (ImageView) findViewById(R.id.fcm_client_tab_view).findViewById(R.id.icon);
        int iconFloatingChat = FcmClient.getUiConfiguration().getIconFloatingChat();
        RoundedBitmapDrawable roundedBitmap = BitmapHelper.getRoundedBitmap(getContext(), iconFloatingChat, getRadius());
        permissionIcon.setImageDrawable(roundedBitmap);

        TextView badge = (TextView) findViewById(R.id.fcm_client_tab_view).findViewById(R.id.badge);
        badge.setVisibility(View.VISIBLE);
        badge.setText("2");

        View accept = findViewById(R.id.fcm_client_permission_accept);
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                FcmClient.showManageOverlaySettings();
            }
        });

        View reject = findViewById(R.id.fcm_client_permission_reject);
        reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    private int getRadius() {
        return (int) getContext().getResources().getDimension(R.dimen.fcm_client_chat_icon_radius);
    }
}
