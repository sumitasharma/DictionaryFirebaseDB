package com.example.sumitasharma.loadingrealtimedatabase;

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

    }
}
