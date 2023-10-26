package com.example.btvn_buoi5_andrroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements IItemSwipListener {

    private ArrayList<User> mListUsers;
    private UserAdapter mUserAdapter;
    private RecyclerView rvUserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
    }

    private void initData() {
        mListUsers = new ArrayList<>();

        for (int i = 0; i < 20; i++) {
            User user = new User();
            user.setUserName("User name " + i);
            mListUsers.add(user);
        }
    }

    private void initView() {
        rvUserName = findViewById(R.id.rvUserName);

        mUserAdapter = new UserAdapter(mListUsers);
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,true);
//        rvDemo.setLayoutManager(linearLayoutManager);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvUserName.setLayoutManager(linearLayoutManager);
        SwipItemCallback swipeItemCallBack = new SwipItemCallback(this, mUserAdapter);
        swipeItemCallBack.setItemSwipeListener(this);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(swipeItemCallBack);

        itemTouchHelper.attachToRecyclerView(rvUserName);
        rvUserName.setAdapter(mUserAdapter);
    }

    @Override
    public void onItemSwiped(int pos) {
        mUserAdapter.deleteItem(pos);
    }
}