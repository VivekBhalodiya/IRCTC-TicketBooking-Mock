package com.example.vivekbhalodiya.railticket.feature.fareavailabilty;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.example.vivekbhalodiya.railticket.api.model.TrainBetween.TrainsItem;
import com.example.vivekbhalodiya.railticket.api.model.TrainFare.ClassesItem;
import com.example.vivekbhalodiya.railticket.databinding.AvailableClassesBinding;
import java.util.ArrayList;
import timber.log.Timber;

/**
 * Created by vivekbhalodiya on 2/18/18.
 */

public class ClassessAdapter extends RecyclerView.Adapter<ClassessAdapter.ViewHolder> {
  private ArrayList<ClassesItem> listOfClasses;
  private TrainsItem trainsItem;
  private Context context;
  private AvailabilityAdapter availabilityAdapter;
  private FareViewModel viewModel;
  private int classFare;

  public ClassessAdapter(ArrayList<ClassesItem> listOfClasses, TrainsItem trainsItem, Context context,
      AvailabilityAdapter availabilityAdapter) {
    this.listOfClasses = listOfClasses;
    this.trainsItem = trainsItem;
    this.context = context;
    this.availabilityAdapter = availabilityAdapter;
    viewModel = new FareViewModel();
    Timber.i("Size of List %s", listOfClasses.size());
  }

  void setData(int fare) {
    Timber.i("Adapter Fare %s", fare);
    this.classFare = fare;
  }

  @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    AvailableClassesBinding binding = AvailableClassesBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
    return new ViewHolder(binding);
  }

  @Override public void onBindViewHolder(final ViewHolder holder, final int position) {
    holder.binding.className.setText(listOfClasses.get(holder.getAdapterPosition()).getCode());
    holder.binding.classessCardview.setOnClickListener(v -> {
      viewModel.OnClickTrainResult(trainsItem,
          holder.binding.className.getText().toString(), context,
          availabilityAdapter, this);
      notifyDataSetChanged();
    });
  }

  @Override public int getItemCount() {
    return listOfClasses.size();
  }

  public class ViewHolder extends RecyclerView.ViewHolder {
    private AvailableClassesBinding binding;

    public ViewHolder(AvailableClassesBinding binding) {
      super(binding.getRoot());
      this.binding = binding;
    }
  }
}
