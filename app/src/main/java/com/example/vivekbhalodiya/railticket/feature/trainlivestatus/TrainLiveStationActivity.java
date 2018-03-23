package com.example.vivekbhalodiya.railticket.feature.trainlivestatus;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import com.example.vivekbhalodiya.railticket.R;
import com.example.vivekbhalodiya.railticket.api.model.TrainLiveStatus.TrainLiveStatusResponse;

public class TrainLiveStationActivity extends AppCompatActivity {

  TrainLiveStatusResponse trainLiveStatusResponse;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_train_live_station);
    trainLiveStatusResponse = (TrainLiveStatusResponse) getIntent().getExtras().getSerializable("response");

    TextView trainStatusTextView = findViewById(R.id.train_status);
    trainStatusTextView.setText(trainLiveStatusResponse.getPosition());
    TrainLiveStatusAdapter adapter = new TrainLiveStatusAdapter();
    RecyclerView recyclerView = findViewById(R.id.train_live_status_recyclerview);
    recyclerView.setLayoutManager(new LinearLayoutManager(this));
    adapter.setData(trainLiveStatusResponse);
    recyclerView.setAdapter(adapter);
  }
}
