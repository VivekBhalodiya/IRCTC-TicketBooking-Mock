package com.example.vivekbhalodiya.railticket.feature.trainresult;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.example.vivekbhalodiya.railticket.R;

import com.example.vivekbhalodiya.railticket.api.model.TrainBetween.TrainsItem;
import com.example.vivekbhalodiya.railticket.api.model.TrainFare.ClassesItem;
import com.example.vivekbhalodiya.railticket.databinding.ActivityTrainResultBinding;
import com.example.vivekbhalodiya.railticket.feature.base.BaseActivity;
import com.yarolegovich.lovelydialog.LovelyCustomDialog;
import java.util.ArrayList;
import timber.log.Timber;

public class TrainResultActivity extends BaseActivity<ActivityTrainResultBinding, TrainResultViewModel, TrainResultView>
    implements TrainResultView {
  private ArrayList<TrainsItem> trainsItemArrayList;
  private RecyclerView recyclerView;
  private TrainResultAdapter adapter;
  private ArrayList<ArrayList<ClassesItem>> avaiableClassessList;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    trainsItemArrayList = (ArrayList<TrainsItem>) getIntent().getBundleExtra("intent").getSerializable("trains");
    avaiableClassessList = (ArrayList<ArrayList<ClassesItem>>) getIntent().getBundleExtra("intent").getSerializable("classes");

    recyclerView = findViewById(R.id.recycler_view);
    adapter = new TrainResultAdapter(trainsItemArrayList,avaiableClassessList, viewModel,this);
    recyclerView.setLayoutManager(new LinearLayoutManager(this));
    recyclerView.setItemAnimator(new DefaultItemAnimator());
    recyclerView.setAdapter(adapter);
  }

  @Override protected int getLayoutId() {
    return R.layout.activity_train_result;
  }

  @Override protected void onComponentCreated() {
    viewModel = new TrainResultViewModel();
  }

  @Override public void initiateDialogBox() {
    new LovelyCustomDialog(this)
        .setTitle("Hello")
        .show();
  }

  @Override public void printHello() {
    Timber.i("Hello");
  }
}