package com.city.citylist.mvp;

import android.app.Activity;
import android.location.Location;

import androidx.annotation.NonNull;

import com.city.citylist.model.AppartmentModel;
import com.city.citylist.model.CountryModel;

import java.util.List;

public interface Contract {

    interface View{
        void showCityList(List list);
        void showCountry(List<CountryModel> list);
    }
    interface Presenter {
        void selectCity(List<CountryModel> list);
        void onSuccess(@NonNull List<AppartmentModel> list);
        void onError(@NonNull Throwable throwable);

        void getCityList();
        void getAppList(int id, int id2, int height, int width);
    }
    interface Model {
        void getCountry(Presenter listener);
        void getFlatList(Presenter listener, int id, int id2, int height, int width);
    }
}
