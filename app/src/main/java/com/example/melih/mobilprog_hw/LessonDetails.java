package com.example.melih.mobilprog_hw;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class LessonDetails extends AppCompatActivity {
    TextView tvLecturer,tvLessonName,tvAvgGrades,tvNumOfStudents;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson_details);

        Bundle extras=getIntent().getExtras();
        if(extras!=null){
            int position = extras.getInt("position");
            tvLecturer=(TextView) findViewById(R.id.tvLecturer);
            tvLessonName=(TextView) findViewById(R.id.tvLessonName);
            tvAvgGrades=(TextView) findViewById(R.id.tvAvgGrade);
            tvNumOfStudents=(TextView) findViewById(R.id.tvNumOfStudents);
            String lecturer = getResources().getStringArray(R.array.lecturers)[position];
            String lessonName = getResources().getStringArray(R.array.lessonNames)[position];
            String avgGrades = getResources().getStringArray(R.array.avgGrades)[position];
            String numOfStudents = getResources().getStringArray(R.array.numOfStudents)[position];
            tvLecturer.setText(lecturer);
            tvLessonName.setText(lessonName);
            tvAvgGrades.setText(avgGrades);
            tvNumOfStudents.setText(numOfStudents);
        }
    }
}
