package com.example.sumitasharma.loadingrealtimedatabase;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.content.res.AssetManager;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.TextView;

import com.example.sumitasharma.loadingrealtimedatabase.DictionaryUtils.ApiService;
import com.example.sumitasharma.loadingrealtimedatabase.DictionaryUtils.Example;
import com.example.sumitasharma.loadingrealtimedatabase.DictionaryUtils.RetroClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class GetDataFromDictionary {
    private final Context mContext;
    private final JobService mJobService;
    private final JobParameters mJobParameters;
    private HashMap<String, String> words = new HashMap<>();
    private TextView mMeaningTextView;

    GetDataFromDictionary(WordDbPopulatorJobService wordDbPopulatorService, Context context, JobParameters jobParameters) {
        mContext = context;
        mJobParameters = jobParameters;
        mJobService = wordDbPopulatorService;
    }


    private void populateDatabase(final HashMap<String, String> words, final Context context) {

        //Creating an object of our api interface
        ApiService api = RetroClient.getApiService();
        Log.i("GetDataFromDictionary", "Inside populateDatabase");

        final FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Calling JSON

        for (final String word : words.keySet()) {
            Call<List<Example>> call = api.getMyJSON(word);
            Log.i("GetDataFromDictionary", "called api");


            // Enqueue Callback will be call when get response...

            call.enqueue(new Callback<List<Example>>() {
                @Override
                public void onResponse(Call<List<Example>> call, Response<List<Example>> response) {
                    String meaning;
                    try {
                        if (response.isSuccessful()) {
                            Log.i("GetDataFromDictionary", "Got response" + response.body());

                            List<Example> exampleList = response.body();
                            Example example = exampleList.get(0);
                            List<String> meanings = example.getDefs();


                            meaning = meanings.get(0);
                            meaning = meaning.split("\t", 2)[1];

                            //   mMeaningTextView = findViewById(R.id.word_meaning);
                            // Create a new user with a first and last name
                            Map<String, String> dictionary = new HashMap<>();
                            dictionary.put("word", word);
                            dictionary.put("wordMeaning", meaning);
                            dictionary.put("wordLevel", words.get(word));

                            // Add a new document with a generated ID
                            db.collection("dictionary")
                                    .add(dictionary)
                                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                        @Override
                                        public void onSuccess(DocumentReference documentReference) {
                                            Log.d("", "DocumentSnapshot added with ID: " + documentReference.getId());
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.w("", "Error adding document", e);
                                        }
                                    });

                        } else {
                            Log.w("", "Response not successful" + response);
                        }
                        mJobService.jobFinished(mJobParameters, true);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<List<Example>> call, Throwable t) {
                    Log.w("GetDataFromDictionary", "Error getting response" + t);
                }
            });

        }

    }

    public void dataFromDictionary() {
        HashMap<String, String> words = new HashMap<>();

        AssetManager assetManager = mContext.getAssets();
        // To load text file
        BufferedReader bufferedReader = null;

        try {
            bufferedReader = new BufferedReader(
                    new InputStreamReader(mContext.getAssets().open("words_easy"), "UTF-8"));

            // do reading, usually loop until end of file reading
            String mLine;
            for (int i = 0; (mLine = bufferedReader.readLine()) != null; i++) {

                words.put(mLine, "Easy");
            }
        } catch (IOException e) {
            //log the exception

        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    //log the exception
                }
            }
        }
        try {
            bufferedReader = new BufferedReader(
                    new InputStreamReader(mContext.getAssets().open("words_moderate"), "UTF-8"));

            // do reading, usually loop until end of file reading
            String mLine;
            for (int i = 0; i < 100 && (mLine = bufferedReader.readLine()) != null; i++) {
                words.put(mLine, "Moderate");

            }
        } catch (IOException e) {
            //log the exception

        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    //log the exception

                }
            }
        }
        try {
            bufferedReader = new BufferedReader(
                    new InputStreamReader(mContext.getAssets().open("words_difficult"), "UTF-8"));

            // do reading, usually loop until end of file reading
            String mLine;
            for (int i = 0; i < 100 && (mLine = bufferedReader.readLine()) != null; i++) {

                words.put(mLine, "Difficult");
            }
        } catch (IOException e) {
            //log the exception

        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    //log the exception

                }
            }
        }
        populateDatabase(words, mContext);
    }
}

