package com.city.citylist.mvp;

import com.city.citylist.model.AppartmentModel;
import com.city.citylist.model.CountryModel;
import com.city.citylist.model.DataItem;
import com.city.citylist.model.FlatsListModel;
import com.city.citylist.service.MyApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class Model implements Contract.Model {

    @Override
    public void getCountry(Contract.Presenter listener) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.beta.kvartirka.pro")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        MyApi api = retrofit.create(MyApi.class);

        Call<DataItem> call = api.call();
        call.enqueue(new Callback<DataItem>() {
            @Override
            public void onResponse(Call<DataItem> call, Response<DataItem> response) {
                if (response.isSuccessful()) {
                    DataItem item = response.body();
                    List<CountryModel> countries = item.countries;

                    listener.selectCity(countries);
                }
            }

            @Override
            public void onFailure(Call<DataItem> call, Throwable t) {
                listener.onError(t);
            }
        });
    }


    @Override
    public void getFlatList(Contract.Presenter listener, int id, int id2, int height, int width) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.beta.kvartirka.pro/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        MyApi api = retrofit.create(MyApi.class);

        Call<FlatsListModel> call = api.appart(id, id2, height, width);
        call.enqueue(new Callback<FlatsListModel>() {
            @Override
            public void onResponse(Call<FlatsListModel> call, Response<FlatsListModel> response) {
                FlatsListModel flatsListModel = response.body();
                List<AppartmentModel> appartmentModels = flatsListModel.flats;

                listener.onSuccess(appartmentModels);
            }

            @Override
            public void onFailure(Call<FlatsListModel> call, Throwable t) {

            }
        });
    }
}
