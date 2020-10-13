package net.gongmingqm10.sipsample.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import net.gongmingqm10.sipsample.R;
import net.gongmingqm10.sipsample.SipApplication;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends BaseActivity {
    private static final int MY_PERMISSIONS_REQUEST_CODE = 123;
    @BindView(R.id.userName)
    EditText userNameEdit;
     @BindView(R.id.password)
    EditText passwordEdit;
     @BindView(R.id.domain)
    EditText domainEdit;
     @BindView(R.id.loginBtn)
    Button loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        checkPermission(false);
        ButterKnife.bind(this);
        if (SipApplication.getInstance().getAccount() != null) {
            startMainActivity();
        }
        init();
    }

    private void init() {
        setTitle("Login My Account");
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(LoginActivity.this, Manifest.permission.USE_SIP)
                        + ContextCompat.checkSelfPermission(
                        LoginActivity.this, Manifest.permission.RECORD_AUDIO)
                        != PackageManager.PERMISSION_GRANTED) {
                    checkPermission(true);
                } else {
                    validateAndSubmit();
                }
            }
        });
    }

    private void validateAndSubmit() {
        String userName = userNameEdit.getText().toString().trim();
        String password = passwordEdit.getText().toString().trim();
        String domain = domainEdit.getText().toString().trim();
        if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(password) || TextUtils.isEmpty(domain)) {
            showToast("Please fill all the fields");
            return;
        }
        SipApplication.getInstance().saveAccount(userName, password, domain);
        startMainActivity();
    }

    private void startMainActivity() {
        startActivity(new Intent(this, MainActivity.class));
        this.finish();
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    protected void checkPermission(boolean isFromBtnClick) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.USE_SIP)
                + ContextCompat.checkSelfPermission(
                this, Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {

            // Do something, when permissions not granted
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this, Manifest.permission.USE_SIP)
                    || ActivityCompat.shouldShowRequestPermissionRationale(
                    this, Manifest.permission.RECORD_AUDIO)) {
                // If we should give explanation of requested permissions

                // Show an alert dialog here with request explanation
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Phone and Record audio permissions are required to do the task.");
                builder.setTitle("Please grant those permissions");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ActivityCompat.requestPermissions(LoginActivity.this,
                                new String[]{
                                        Manifest.permission.USE_SIP,
                                        Manifest.permission.RECORD_AUDIO
                                },
                                MY_PERMISSIONS_REQUEST_CODE
                        );
                    }
                });
                builder.setNeutralButton("Cancel", null);
                AlertDialog dialog = builder.create();
                dialog.show();
            } else {
                // Directly request for required permissions, without explanation
                ActivityCompat.requestPermissions(
                        this,
                        new String[]{
                                Manifest.permission.USE_SIP,
                                Manifest.permission.RECORD_AUDIO
                        },
                        MY_PERMISSIONS_REQUEST_CODE
                );
            }
        } else {
            if (isFromBtnClick) {
                validateAndSubmit();
            }
            Toast.makeText(this, "Permissions already granted", Toast.LENGTH_SHORT).show();
        }
    }
}
