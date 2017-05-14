package io.rapidpro.sdk.chat;

import android.content.res.TypedArray;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.TypedValue;
import android.view.View;

import io.rapidpro.sdk.FcmClient;
import io.rapidpro.sdk.R;
import io.rapidpro.sdk.UiConfiguration;

/**
 * Created by john-mac on 6/27/16.
 */
public class FcmClientChatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

            int titleColorRes = FcmClient.getUiConfiguration().getTitleColor();
            toolbar.setTitle(FcmClient.getUiConfiguration().getTitleString());
            toolbar.setTitleTextColor(getResources().getColor(titleColorRes));
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
    private int getToolbarColor() {
        int toolbarColorResource = FcmClient.getUiConfiguration().getToolbarColor();
        return toolbarColorResource == UiConfiguration.INVALID_VALUE ? fetchColorPrimary() :
                getResources().getColor(toolbarColorResource);
    }

    private void setActionBarTitleColor(ActionBar actionBar, String title){
        Spannable text = new SpannableString(title);
        int titleColorRes = FcmClient.getUiConfiguration().getTitleColor();
        text.setSpan(new ForegroundColorSpan(getResources().getColor(titleColorRes)), 0, text.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
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