package com.example.vivekbhalodiya.railticket.feature.trainlivestatus;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.example.vivekbhalodiya.railticket.R;
import com.example.vivekbhalodiya.railticket.api.model.TrainLiveStatus.RouteItem;
import com.example.vivekbhalodiya.railticket.api.model.TrainLiveStatus.TrainLiveStatusResponse;
import com.example.vivekbhalodiya.railticket.databinding.ListOfLiveStationsBinding;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by vivekbhalodiya on 3/18/18.
 */

public class TrainLiveStatusAdapter extends RecyclerView.Adapter<TrainLiveStatusAdapter.ViewHolder> {

  private TrainLiveStatusResponse trainLiveStatusResponse;
  private List<RouteItem> routeItems = new ArrayList<>();

  @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    ListOfLiveStationsBinding binding = ListOfLiveStationsBinding.inflate
        (LayoutInflater.from(parent.getContext()), parent, false);
    return new ViewHolder(binding);
  }

  @Override public void onBindViewHolder(ViewHolder holder, int position) {
    holder.binding.actualArrival.setText("Actual Arrival : " + String.valueOf(routeItems.get(position).getActarr()));
    String hasDeaparted = String.valueOf(routeItems.get(position).getHasDeparted());
    if(hasDeaparted.toLowerCase().equals("true"))
      holder.binding.isTrainDeparted.setTextColor(ContextCompat.getColor(holder.binding.getRoot().getContext(), R.color.success_green));
    else
      holder.binding.isTrainDeparted.setTextColor(ContextCompat.getColor(holder.binding.getRoot().getContext(), R.color.light_orange));
    holder.binding.isTrainDeparted.setText(hasDeaparted);
    holder.binding.lateByMinute.setText("Late by minutes : " + String.valueOf(routeItems.get(position).getLatemin() + " mins"));
    holder.binding.stationName.setText(String.valueOf(routeItems.get(position).getStation().getName()));
    holder.binding.stationDistance.setText("Distance : " + String.valueOf(routeItems.get(position).getDistance())+" km");
    holder.binding.scheduledArrival.setText("Scheduled Arrival : "+ String.valueOf(routeItems.get(position).getScharr()));

  }

  @Override public int getItemCount() {
    return routeItems.size();
  }

  public void setData(TrainLiveStatusResponse trainLiveStatusResponse) {
    this.trainLiveStatusResponse = trainLiveStatusResponse;
    this.routeItems = trainLiveStatusResponse.getRoute();
    notifyDataSetChanged();
  }

  public class ViewHolder extends RecyclerView.ViewHolder {
    ListOfLiveStationsBinding binding;

    public ViewHolder(ListOfLiveStationsBinding itemView) {
      super(itemView.getRoot());
      binding = itemView;
    }
  }
}