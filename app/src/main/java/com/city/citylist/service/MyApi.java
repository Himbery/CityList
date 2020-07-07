package com.city.citylist.service;

import com.city.citylist.model.DataItem;
import com.city.citylist.model.FlatsListModel;

import retrofit2.http.GET;
import retrofit2.Call;
import retrofit2.http.Query;

public interface MyApi {

    @GET("/client/1.4/country/?pretty")
    Call<DataItem> call();

    @GET("/client/1.4/flats/")
    Call<FlatsListModel> appart(@Query("city_id") int city_id,@Query("device_screen_width") int width, @Query("device_screen_height") int height, @Query("currency_id") int country_id);

}
