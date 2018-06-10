package com.example.sumitasharma.loadingrealtimedatabase;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {


    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private TextView mMeaningTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMeaningTextView = findViewById(R.id.word_meaning);
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference().child("dictionary");
        Dictionary dictionary = new Dictionary("hello", "Greeting");
        mDatabaseReference.push().setValue(dictionary);
//        'https://loadingrealtimedatabase.firebaseio.com/dictionary.json'
//        mMeaningTextView.setText();
        scheduleDbPopulatorJob(this);

    }

    private void scheduleDbPopulatorJob(Context context) {

        //Set job scheduling based on user preference
        ComponentName serviceComponent = new ComponentName(context, WordDbPopulatorJobService.class);
        JobInfo jobInfo = new JobInfo.Builder(1234, serviceComponent)
                .setMinimumLatency(60 * 60 * 1000) // wait at least
                .setOverrideDeadline(24 * 60 * 60 * 1000) // maximum delay
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                .build();
        JobScheduler jobService = (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);
        jobService.schedule(jobInfo);
    }
}