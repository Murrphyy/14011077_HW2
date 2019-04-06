package com.example.melih.mobilprog_hw;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

public class LessonList extends AppCompatActivity implements RecyclerViewClickListener {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    String[] lessonSet;
    Intent lessonDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson_list);
        lessonDetails=new Intent(LessonList.this,LessonDetails.class);
        recyclerView = (RecyclerView) findViewById(R.id.lessonsRV);
        recyclerView.setHasFixedSize(true);

        lessonSet=getResources().getStringArray(R.array.lessons);

        mAdapter = new MyRVAdapter(this,lessonSet,this);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        //recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);


    }

    @Override
    public void recyclerViewListClicked(View v, int position){
        lessonDetails.putExtra("position",position);
        startActivity(lessonDetails);
    }


}
