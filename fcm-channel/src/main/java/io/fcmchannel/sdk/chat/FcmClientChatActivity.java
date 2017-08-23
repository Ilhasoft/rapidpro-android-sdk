package io.fcmchannel.sdk.chat;

import android.content.res.TypedArray;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.TypedValue;
import android.view.View;

import io.fcmchannel.sdk.FcmClient;
import io.fcmchannel.sdk.R;
import io.fcmchannel.sdk.UiConfiguration;

/**
 * Created by john-mac on 6/27/16.
 */
public class FcmClientChatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int theme = FcmClient.getUiConfiguration().getTheme();
        if (theme != UiConfiguration.INVALID_VALUE) {
            setTheme(theme);
        }

        setContentView(R.layout.fcm_client_activity_chat);
        addFcmClientChatFragment(savedInstanceState);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setupToolbar(toolbar);
    }

    private void addFcmClientChatFragment(@Nullable Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.content, new FcmClientChatFragment())
                    .commit();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    @SuppressWarnings("ConstantConditions")
    private void setupToolbar(Toolbar toolbar) {
        if (getSupportActionBar() == null) {
            toolbar.setVisibility(View.VISIBLE);

            int titleColor = getTitleColorFromSettings();
            toolbar.setTitle(FcmClient.getUiConfiguration().getTitleString());
            toolbar.setTitleTextColor(titleColor);
            toolbar.setBackgroundColor(getToolbarColor());
            setSupportActionBar(toolbar);
        } else {
            ActionBar actionBar = getSupportActionBar();
            actionBar.setBackgroundDrawable(new ColorDrawable(getToolbarColor()));
            setActionBarTitleColor(actionBar, FcmClient.getUiConfiguration().getTitleString());
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(FcmClient.getUiConfiguration().getBackResource());
    }

    @ColorInt
    private int getTitleColorFromSettings() {
        int titleColor = FcmClient.getUiConfiguration().getTitleColor();
        titleColor = titleColor != UiConfiguration.INVALID_VALUE ? titleColor :
                ResourcesCompat.getColor(getResources(), android.R.color.white, getTheme());
        return titleColor;
    }

    @ColorInt
    private int getToolbarColor() {
        int toolbarColor = FcmClient.getUiConfiguration().getToolbarColor();
        return toolbarColor == UiConfiguration.INVALID_VALUE ? fetchColorPrimary() : toolbarColor;
    }

    private void setActionBarTitleColor(ActionBar actionBar, String title){
        Spannable text = new SpannableString(title);
        int titleColor = getTitleColorFromSettings();
        text.setSpan(new ForegroundColorSpan(titleColor), 0, text.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        actionBar.setTitle(text);
    }

    private int fetchColorPrimary() {
        TypedValue typedValue = new TypedValue();
        TypedArray typedArray = obtainStyledAttributes(typedValue.data, new int[] { R.attr.colorPrimary });
        int color = typedArray.getColor(0, 0);
        typedArray.recycle();
        return color;
    }

}