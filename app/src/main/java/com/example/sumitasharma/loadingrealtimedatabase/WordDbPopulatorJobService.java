package com.example.sumitasharma.loadingrealtimedatabase;


import android.app.job.JobParameters;
import android.app.job.JobService;

public class WordDbPopulatorJobService extends JobService {
    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        new GetDataFromDictionary(this, this, jobParameters).dataFromDictionary();
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return false;
    }
}
