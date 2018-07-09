package com.example.sumitasharma.loadingrealtimedatabase;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Choose authentication providers
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.GoogleBuilder().build()
               );

        // Create and launch sign-in intent
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .build(),
                RC_SIGN_IN);

        setContentView(R.layout.activity_main);

        //GetDataFromDictionary data = new GetDataFromDictionary(this);
      //  data.dataFromDictionary();
       // scheduleDbPopulatorJob(this);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if (resultCode == RESULT_OK) {
                // Successfully signed in
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                Log.i("Loading" , "user is:" + user.getUid());
                // ...
            } else {
                // Sign in failed. If response is null the user canceled the
                // sign-in flow using the back button. Otherwise check
                // response.getError().getErrorCode() and handle the error.
                // ...
            }
        }
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