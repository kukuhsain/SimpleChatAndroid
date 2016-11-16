package com.kukuhsain.simple.chat.view;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.kukuhsain.simple.chat.R;
import com.kukuhsain.simple.chat.model.local.PreferencesHelper;
import com.kukuhsain.simple.chat.model.pojo.Chat;
import com.kukuhsain.simple.chat.view.adapter.ChatAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by kukuh on 14/11/16.
 */

public class ChatActivity extends AppCompatActivity {
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.rv_samples) RecyclerView rvSamples;

    private ProgressDialog progressDialog;
    private ActionBar actionBar;
    private RecyclerView.LayoutManager layoutManager;
    private ChatAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        actionBar.setTitle("Chat Activity");

        layoutManager = new LinearLayoutManager(this);
        rvSamples.setLayoutManager(layoutManager);
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter = new ChatAdapter(getDummySamples());
        rvSamples.setAdapter(adapter);
    }

    private List<Chat> getDummySamples() {
        List<Chat> chats = new ArrayList<>();
        int total = 10;
        for (int i=0; i<total; i++) {
            chats.add(new Chat(i, "Chat title "+i, "Chat description, Lorem ipsum "+i));
        }
        return chats;
    }

    public void onItemClicked(Chat chat) {
        Toast.makeText(this, chat.getName(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_chat_activity, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_sign_out:
                PreferencesHelper.getInstance().clearData();
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showLoading() {
        if (progressDialog != null) {
            progressDialog.show();
        } else {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Please wait...");
            progressDialog.show();
        }
    }

    private void dismissLoading() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }
}
