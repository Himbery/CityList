package com.city.citylist.service;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.city.citylist.FlatActivity;
import com.city.citylist.R;
import com.city.citylist.model.AppartmentModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CityAdapter extends RecyclerView.Adapter<CityAdapter.ViewHolder> {
    private LayoutInflater inflater;
    private List<AppartmentModel> list = new ArrayList<>();

    public CityAdapter(Context context, List<AppartmentModel> list){
        this.inflater = LayoutInflater.from(context);
        this.list = list;
    }
    @NonNull
    @Override
    public CityAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.item_layout, parent, false);
        return new CityAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final AppartmentModel appartmentModel = list.get(position);

        holder.cityView.setText(appartmentModel.address);
        holder.priceView.setText(String.valueOf(appartmentModel.prices.day));
        Glide.with(holder.imageView.getContext()).load(appartmentModel.photo_default.url).into(holder.imageView);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = holder.itemView.getContext();
                Intent intent = new Intent(context, FlatActivity.class);
                intent.putExtra("address", appartmentModel.address);
                intent.putExtra("title", appartmentModel.title);
                intent.putExtra("price_day", appartmentModel.prices.day);
                intent.putExtra("price_hour", appartmentModel.prices.hour);
                intent.putExtra("price_night", appartmentModel.prices.night);
                intent.putExtra("description", appartmentModel.description);
                intent.putExtra("photos", (Serializable) appartmentModel.photos);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView c;
        final ImageView imageView;
        final TextView cityView, priceView;
        ViewHolder(View view){
            super(view);
            c = (CardView) view.findViewById(R.id.body);
            imageView = (ImageView)view.findViewById(R.id.photo);
            cityView = (TextView) view.findViewById(R.id.address);
            priceView = (TextView) view.findViewById(R.id.price);
        }
    }
}
