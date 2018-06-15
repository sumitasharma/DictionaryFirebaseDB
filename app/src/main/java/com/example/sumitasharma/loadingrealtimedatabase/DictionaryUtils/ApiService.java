package com.example.sumitasharma.loadingrealtimedatabase.DictionaryUtils;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService {

    /*
    Retrofit get annotation with our URL
    And our method that will return us the List of ContactList
    */
    @GET("?sp={word_id}&md=d&max=1")
    // @Headers({"app_id: " + APP_ID, "app_key: " + API_KEY})
    Call<Example> getMyJSON(@Path("word_id") String wordId);
}
