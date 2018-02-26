package com.example.vivekbhalodiya.railticket.feature.fareavailabilty;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.vivekbhalodiya.railticket.api.model.TrainAvailability.AvailabilityItem;
import com.example.vivekbhalodiya.railticket.databinding.ListOfAvailabilityBinding;
import java.util.ArrayList;
import java.util.List;
import timber.log.Timber;

/**
 * Created by vivekbhalodiya on 2/21/18.
 */

public class AvailabilityAdapter extends RecyclerView.Adapter<AvailabilityAdapter.ViewHolder> {
  private List<AvailabilityItem> availability = new ArrayList<>();

  public AvailabilityAdapter(List<AvailabilityItem> availability) {
    this.availability=availability;
  }

  public void setData(List<AvailabilityItem> availability){
    this.availability.clear();

    this.availability.addAll(availability);
    notifyDataSetChanged();
  }
  @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    ListOfAvailabilityBinding binding=ListOfAvailabilityBinding.inflate(LayoutInflater.from(parent.getContext()));
    return new ViewHolder(binding);
  }

  @Override public void onBindViewHolder(ViewHolder holder, int position) {
    holder.binding.dateOfAvailability.setText(availability.get(holder.getAdapterPosition()).getDate());
    holder.binding.availabilityTextview.setText(availability.get(holder.getAdapterPosition()).getStatus());
  }

  @Override public int getItemCount() {
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
