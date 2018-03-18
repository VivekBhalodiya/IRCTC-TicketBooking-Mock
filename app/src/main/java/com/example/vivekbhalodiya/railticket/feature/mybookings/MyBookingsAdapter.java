package com.example.vivekbhalodiya.railticket.feature.mybookings;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.example.vivekbhalodiya.railticket.databinding.ListOfBookingsBinding;
import com.example.vivekbhalodiya.railticket.feature.passsengerdetail.PassengerViewModel;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by vivekbhalodiya on 3/14/18.
 */

public class MyBookingsAdapter extends RecyclerView.Adapter<MyBookingsAdapter.ViewHolder> {
  private List<PassengerViewModel> passengerViewModelsList = new ArrayList<>();

  void setBookingsAdapterData(List<PassengerViewModel> passengerViewModelsList){
    this.passengerViewModelsList = passengerViewModelsList;
    notifyDataSetChanged();
  }

  @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return new ViewHolder(ListOfBookingsBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
  }

  @Override public void onBindViewHolder(ViewHolder holder, int position) {
    holder.binding.bookingDstName.setText(passengerViewModelsList.get(position).getToStationName());
    holder.binding.bookingSrcName.setText(passengerViewModelsList.get(position).getFromStationName());
    holder.binding.bookingJourneyDate.setText(passengerViewModelsList.get(position).getPassengerJourneyDate());
    holder.binding.bookingPnr.setText(passengerViewModelsList.get(position).getPnr());
    holder.binding.bookingDstTime.setText(passengerViewModelsList.get(position).getDestArrivalTime());
    holder.binding.bookingSrcTime.setText(passengerViewModelsList.get(position).getSrcDeptTime());
  }

  @Override public int getItemCount() {
    return passengerViewModelsList.size();
  }

  public class ViewHolder extends RecyclerView.ViewHolder {
    ListOfBookingsBinding binding;

    public ViewHolder(ListOfBookingsBinding itemView) {
      super(itemView.getRoot());
      binding = itemView;
    }
  }
}
