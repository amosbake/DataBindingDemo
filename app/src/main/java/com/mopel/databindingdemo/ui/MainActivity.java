package com.mopel.databindingdemo.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.mopel.databindingdemo.R;
import com.mopel.databindingdemo.model.Baby;
import com.mopel.databindingdemo.ui.adapter.BabyAdapter;
import com.mopel.databindingdemo.util.ModelUtil;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rvbaby;
    private List<Baby> mBabies;
    private BabyAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rvbaby= (RecyclerView) findViewById(R.id.rv_baby);
        mBabies=new ArrayList<>();
        mAdapter=new BabyAdapter(this);
        initData();
    }

    private void initData() {
        mBabies.add(ModelUtil.createBaby());
        mBabies.add(ModelUtil.createBaby());
        mBabies.add(ModelUtil.createBaby());
        mBabies.add(ModelUtil.createBaby());
        mBabies.add(ModelUtil.createBaby());
        mAdapter.setItems(mBabies);
    }
}
