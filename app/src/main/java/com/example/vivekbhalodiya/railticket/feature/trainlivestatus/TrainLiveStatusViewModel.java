package com.example.vivekbhalodiya.railticket.feature.trainlivestatus;

import com.example.vivekbhalodiya.railticket.api.model.TrainLiveStatus.TrainLiveStatusResponse;
import com.example.vivekbhalodiya.railticket.api.services.TrainApi;
import com.example.vivekbhalodiya.railticket.api.services.TrainApiInterface;
import com.example.vivekbhalodiya.railticket.constants.AppConstant;
import com.example.vivekbhalodiya.railticket.feature.base.BaseViewModel;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by vivekbhalodiya on 3/18/18.
 */

class TrainLiveStatusViewModel extends BaseViewModel<TrainLiveStatusView> {
  private TrainApiInterface trainApi = new TrainApi().getClient().create(TrainApiInterface.class);
  private Observable<TrainLiveStatusResponse> call;

  void getTrainLiveStatus(String trainNumber,String date){
    call = trainApi.getTrainLiveStatus(trainNumber,date, AppConstant.API_KEY);
    call
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new DisposableObserver<TrainLiveStatusResponse>() {
          @Override public void onNext(TrainLiveStatusResponse trainLiveStatusResponse) {

          }

          @Override public void onError(Throwable e) {

          }

          @Override public void onComplete() {

          }
        });
  }
}
