package com.example.sumitasharma.loadingrealtimedatabase;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.content.SharedPreferences;
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
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.sumitasharma.loadingrealtimedatabase.WordUtil.LAST_SAVED_POSITION;

class GetDataFromDictionary {
    private final Context mContext;
    private final JobService mJobService;
    private final JobParameters mJobParameters;
    private HashMap<String, String> words = new HashMap<>();
    //    private FirebaseDatabase mFirebaseDatabase;
//    private DatabaseReference mDatabaseReference;
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
            Call<Example> call = api.getMyJSON(word);
            Log.i("GetDataFromDictionary", "called api");


            // Enqueue Callback will be call when get response...

            call.enqueue(new Callback<Example>() {
                @Override
                public void onResponse(Call<Example> call, Response<Example> response) {
                    if (response.isSuccessful()) {
                        Log.i("GetDataFromDictionary", "Got response");

                        Example example = response.body();
                        String meaning = example.getDefs().get(0);
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

//                            mDatabaseReference = mFirebaseDatabase.getReference().child("dictionary");
//                            Dictionary dictionary = new Dictionary(word, meaning);
//                            mDatabaseReference.push().setValue(dictionary);
//
//                            // Create new empty ContentValues object
//                            ContentValues contentValues = new ContentValues();
//
//                            // Put the task description and selected mPriority into the ContentValues
//                            contentValues.put(WordContract.WordsEntry.COLUMN_WORD, word);
//                            contentValues.put(WordContract.WordsEntry.COLUMN_WORD_MEANING, meaning);
//                            contentValues.put(WordContract.WordsEntry.COLUMN_WORD_LEVEL, words.get(word));
//                            contentValues.put(WordContract.WordsEntry.COLUMN_WORD_PRACTICED, false);
//                            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//                            String date = dateFormat.format(new Date());
//                            contentValues.put(WordContract.WordsEntry.COLUMN_LAST_UPDATED, date);
//                            // Insert the content values via a ContentResolver
//
//                            mContext.getContentResolver().insert(WordContract.WordsEntry.CONTENT_URI, contentValues);
                    }
                    mJobService.jobFinished(mJobParameters, true);
                }

                @Override
                public void onFailure(Call<Example> call, Throwable t) {

                }
            });

        }

    }

    public void dataFromDictionary() {
        int last_saved_position;
        SharedPreferences sharedPref = mContext.getSharedPreferences(LAST_SAVED_POSITION, Context.MODE_PRIVATE);
        last_saved_position = sharedPref.getInt(LAST_SAVED_POSITION, 0);
        HashMap<String, String> words = new HashMap<>();

        AssetManager assetManager = mContext.getAssets();
        // To load text file
        BufferedReader bufferedReader = null;

        try {
            bufferedReader = new BufferedReader(
                    new InputStreamReader(mContext.getAssets().open("words_easy"), "UTF-8"));

            // do reading, usually loop until end of file reading
            String mLine;
            // skipping till last_saved_position which we get from the shared preferences
            for (int i = 0; i < last_saved_position; i++)
                bufferedReader.readLine();
            for (int i = 0; i < 10 && (mLine = bufferedReader.readLine()) != null; i++) {

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
            // skipping till last_saved_position which we get from the shared preferences
            for (int i = 0; i < last_saved_position; i++)
                bufferedReader.readLine();
            for (int i = 0; i < 10 && (mLine = bufferedReader.readLine()) != null; i++) {
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
            // skipping till last_saved_position which we get from the shared preferences
            for (int i = 0; i < last_saved_position; i++)
                bufferedReader.readLine();
            for (int i = 0; i < 10 && (mLine = bufferedReader.readLine()) != null; i++) {

                words.put(mLine, "Difficult");
            }
            sharedPref = mContext.getSharedPreferences(LAST_SAVED_POSITION, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putInt(LAST_SAVED_POSITION, last_saved_position + 10);
            editor.apply();
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

