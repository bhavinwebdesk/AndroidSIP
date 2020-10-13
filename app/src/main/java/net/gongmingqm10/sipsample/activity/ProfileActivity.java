package net.gongmingqm10.sipsample.activity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import net.gongmingqm10.sipsample.R;
import net.gongmingqm10.sipsample.SipApplication;
import net.gongmingqm10.sipsample.data.Account;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProfileActivity extends BaseActivity {

     @BindView(R.id.userName)
    TextView userName;
     @BindView(R.id.domain)
    TextView domain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        ButterKnife.bind(this);
        Account account = SipApplication.getInstance().getAccount();
        userName.setText("Username：" + account.getUsername());
        domain.setText("Domain：" + account.getDomain());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);

    }
}
