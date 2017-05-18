package io.rapidpro.sdk.sample;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import io.rapidpro.sdk.FcmClient;
import io.rapidpro.sdk.chat.menu.FcmClientMenuService;

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
        requestPermissionIfNeeded();
        setupView();
    }

    private void requestPermissionIfNeeded() {
        if (!FcmClient.hasFloatingPermission()) {
            new AlertDialog.Builder(this)
                    .setMessage(R.string.permission_floating_chat)
                    .setNegativeButton(R.string.no, null)
                    .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            FcmClient.requestFloatingPermissions();
                        }
                    }).show();
        }
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(this)) {
                Intent drawOverlaysSettingsIntent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
                drawOverlaysSettingsIntent.setData(Uri.parse("package:" + getPackageName()));
                startActivity(drawOverlaysSettingsIntent);
            } else {
                FcmClientMenuService.showFloatingMenu(getApplicationContext());
            }
        } else {
            FcmClientMenuService.showFloatingMenu(getApplicationContext());
        }
    }
}
