package com.example.vivekbhalodiya.railticket.feature.fareavailabilty;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.vivekbhalodiya.railticket.R;
import com.example.vivekbhalodiya.railticket.api.model.TrainAvailability.AvailabilityItem;
import com.example.vivekbhalodiya.railticket.api.model.TrainBetween.TrainsItem;
import com.example.vivekbhalodiya.railticket.databinding.ListOfAvailabilityBinding;
import com.example.vivekbhalodiya.railticket.feature.passsengerdetail.PassengerDetailsActivity;
import com.example.vivekbhalodiya.railticket.feature.trainresult.TrainResultActivity;
import com.example.vivekbhalodiya.railticket.feature.trainsearch.TrainSearchActivity;
import com.irozon.sneaker.Sneaker;
import com.yarolegovich.lovelydialog.LovelyInfoDialog;
import com.yarolegovich.lovelydialog.LovelyStandardDialog;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import timber.log.Timber;

/**
 * Created by vivekbhalodiya on 2/21/18.
 */

public class AvailabilityAdapter extends RecyclerView.Adapter<AvailabilityAdapter.ViewHolder> {
  private  List<AvailabilityItem> availability= new ArrayList<>();
  private TrainsItem trainsItem;
  private String pref;
  private Context context;
  private TrainResultActivity trainResultActivity;

  public AvailabilityAdapter(Context context, TrainResultActivity trainResultActivity) {
    this.context = context;
    this.trainResultActivity = trainResultActivity;
  }

  public void setData(List<AvailabilityItem> availability, TrainsItem trainsItem, String pref){
    this.availability=availability;
    this.trainsItem = trainsItem;
    this.pref = pref;
    notifyDataSetChanged();
  }
  @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    ListOfAvailabilityBinding binding=ListOfAvailabilityBinding.inflate(LayoutInflater.from(context));
    return new ViewHolder(binding);
  }

  @Override public void onBindViewHolder(ViewHolder holder, int position) {
    final String tmpStatus = availability.get(position).getStatus();
    if(tmpStatus.toLowerCase().contains("available"))
      holder.binding.availabilityTextview.setTextColor(context.getResources().getColor(R.color.success_green));
    else if (tmpStatus.toLowerCase().contains("rac"))
      holder.binding.availabilityTextview.setTextColor(context.getResources().getColor(R.color.light_orange));
    else
      holder.binding.availabilityTextview.setTextColor(context.getResources().getColor(R.color.red));

    holder.binding.dateOfAvailability.setText(availability.get(position).getDate());
    holder.binding.availabilityTextview.setText(tmpStatus);

    holder.binding.availabilityCardview.setOnClickListener(v -> {
      if(checkAvailability(tmpStatus)){
        Bundle bundle=new Bundle();
        Intent intent = new Intent(trainResultActivity, PassengerDetailsActivity.class);
        intent.putExtra("date", availability.get(position).getDate());
        intent.putExtra("avail", availability.get(position).getStatus());
        intent.putExtra("pref",pref);
        bundle.putSerializable("train",trainsItem);
        intent.putExtra("bundle",bundle);
        context.startActivity(intent);
      }
      else {
        showNoSeatsAvailable();
      }

    });
  }

  private void showNoSeatsAvailable() {
    new LovelyInfoDialog(context)
        .setMessage("No Seats Available on selected day!!")
        .setTitle("Sorry..")
        .show();
  }

  private boolean checkAvailability(String tmpStatus) {
    if (tmpStatus.toLowerCase().contains("not available"))
      return false;
    else if(tmpStatus.toLowerCase().contains("available"))
      return true;
    else
      return false;
  }

  @Override public int getItemCount(){
    Timber.i("Holi Size %s",availability.size());
       return availability.size();
  }

  public class ViewHolder extends RecyclerView.ViewHolder {
    ListOfAvailabilityBinding binding;
    public ViewHolder(ListOfAvailabilityBinding itemView) {
      super(itemView.getRoot());
      binding=itemView;
    }
  }
}
