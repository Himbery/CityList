package com.city.citylist.mvp;

import androidx.annotation.NonNull;

import com.city.citylist.model.AppartmentModel;
import com.city.citylist.model.CountryModel;
import java.util.List;

public class Presenter implements Contract.Presenter {
    public Contract.View mView;
    public Contract.Model model;

    public Presenter(Contract.View view){
        mView = view;
        model = new Model();
    }
    @Override
    public void selectCity(List<CountryModel> list) {
        mView.showCountry(list);
    }

    @Override
    public void onSuccess(@NonNull List<AppartmentModel> list) {
        mView.showCityList(list);
    }

    @Override
    public void onError(@NonNull Throwable throwable) {

    }

    @Override
    public void getCityList() {
        model.getCountry(this);
    }

    @Override
    public void getAppList(int id, int id2, int height, int width) {
        model.getFlatList(this, id, width, height, id2);
    }
}
