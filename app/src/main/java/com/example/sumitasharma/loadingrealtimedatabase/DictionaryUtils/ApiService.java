package com.example.sumitasharma.loadingrealtimedatabase.DictionaryUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    /*
    Retrofit get annotation with our URL
    And our method that will return us the List of ContactList
    */
    @GET("words?md=d&max=1")
    // @Headers({"app_id: " + APP_ID, "app_key: " + API_KEY})
    Call<List<Example>> getMyJSON(@Query("sp") String wordId);
}
