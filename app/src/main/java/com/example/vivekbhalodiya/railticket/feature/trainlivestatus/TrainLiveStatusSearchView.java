package com.example.vivekbhalodiya.railticket.feature.trainlivestatus;

import com.example.vivekbhalodiya.railticket.api.model.TrainLiveStatus.TrainLiveStatusResponse;
import com.example.vivekbhalodiya.railticket.feature.base.MvvmView;

/**
 * Created by vivekbhalodiya on 3/18/18.
 */

public interface TrainLiveStatusSearchView extends MvvmView {
  void triggerNextActivity(TrainLiveStatusResponse trainLiveStatusResponse);
}
