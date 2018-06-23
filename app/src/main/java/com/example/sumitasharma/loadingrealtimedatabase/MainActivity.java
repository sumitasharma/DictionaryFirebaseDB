package com.example.sumitasharma.loadingrealtimedatabase;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        scheduleDbPopulatorJob(this);

    }

    private void scheduleDbPopulatorJob(Context context) {

        //Set job scheduling based on user preference
        ComponentName serviceComponent = new ComponentName(context, WordDbPopulatorJobService.class);
        JobInfo jobInfo = new JobInfo.Builder(1234, serviceComponent)
                .setMinimumLatency(5 * 60 * 1000) // wait at least
                .setOverrideDeadline(2 * 60 * 1000) // maximum delay
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                .build();
        JobScheduler jobService = (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);
        assert jobService != null;
        jobService.schedule(jobInfo);

    }
}