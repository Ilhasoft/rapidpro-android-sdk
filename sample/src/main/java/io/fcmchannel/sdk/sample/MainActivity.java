package io.fcmchannel.sdk.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import io.fcmchannel.sdk.FcmClient;
import io.fcmchannel.sdk.chat.menu.FcmClientMenuService;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.content, FcmClient.createFcmClientChatFragment())
                    .commit();
        }
        FcmClient.requestFloatingPermissionsIfNeeded(this);
        setupView();
    }

    @SuppressWarnings("ConstantConditions")
    private void setupView() {
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_float: {
                onClickActionFloat();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void onClickActionFloat() {
        if (FcmClient.hasFloatingPermission()) {
            FcmClientMenuService.showFloatingMenu(this);
        } else {
            FcmClient.requestFloatingPermissionsIfNeeded(this);
        }
    }
}
