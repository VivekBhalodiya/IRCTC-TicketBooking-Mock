package com.example.vivekbhalodiya.railticket.feature.fareavailabilty;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.vivekbhalodiya.railticket.api.model.TrainFare.ClassesItem;
import com.example.vivekbhalodiya.railticket.databinding.AvailableClassesBinding;
import java.util.ArrayList;
import timber.log.Timber;

/**
 * Created by vivekbhalodiya on 2/18/18.
 */

public class ClassessAdapter extends RecyclerView.Adapter<ClassessAdapter.ViewHolder> {
  private ArrayList<ClassesItem> listOfClasses;
  private String number;
  private String fromCode;
  private String toCode;
  private Context context;
  private FareViewModel viewModel;
  private AvailabilityAdapter availabilityAdapter;
  private int mExpandedPosition = -1;

  public ClassessAdapter(ArrayList<ClassesItem> listOfClasses, String number, String fromCode, String toCode, Context context) {
    this.listOfClasses = listOfClasses;
    this.number = number;
    this.fromCode = fromCode;
    this.toCode = toCode;
    this.context = context;
    viewModel=new FareViewModel();
    Timber.i("Size of List %s", listOfClasses.size());
  }

  @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    AvailableClassesBinding binding = AvailableClassesBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
    return new ViewHolder(binding);
  }

  @Override public void onBindViewHolder(final ViewHolder holder, final int position) {
    final boolean isExpanded = position==mExpandedPosition;

    holder.binding.className.setText(listOfClasses.get(holder.getAdapterPosition()).getCode());
    holder.binding.className.setActivated(isExpanded);
    /*

    availabilityAdapter=new AvailabilityAdapter();
    holder.binding.fareAvailabilityRecyclerview.setLayoutManager(new LinearLayoutManager(holder.binding.getRoot().getContext()));
    holder.binding.fareAvailabilityRecyclerview.setHasFixedSize(true);
    holder.binding.fareAvailabilityRecyclerview.setAdapter(availabilityAdapter);
    */

    holder.binding.fareAvailabilityRecyclerview.setVisibility(isExpanded?View.VISIBLE:View.GONE);
    holder.binding.className.setOnClickListener(v -> {
     /*mExpandedPosition = isExpanded ? -1:position;
      TransitionManager.beginDelayedTransition(holder.binding.fareAvailabilityRecyclerview);
      notifyDataSetChanged();*/
      viewModel.OnClickTrainResult(number,fromCode,toCode,holder.binding.className.getText().toString(),context,availabilityAdapter);
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
