package com.example.vivekbhalodiya.railticket.feature.trainlivestatus;

import com.example.vivekbhalodiya.railticket.api.model.TrainLiveStatus.RouteItem;
import com.example.vivekbhalodiya.railticket.api.model.TrainLiveStatus.TrainLiveStatusResponse;
import com.example.vivekbhalodiya.railticket.api.services.TrainApi;
import com.example.vivekbhalodiya.railticket.api.services.TrainApiInterface;
import com.example.vivekbhalodiya.railticket.constants.AppConstant;
import com.example.vivekbhalodiya.railticket.feature.base.BaseViewModel;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by vivekbhalodiya on 3/18/18.
 */

class TrainLiveStatusViewModel extends BaseViewModel<TrainLiveStatusSearchView> {
  private TrainApiInterface trainApi = new TrainApi().getClient().create(TrainApiInterface.class);

  void getTrainLiveStatus(String trainNumber,String date){
    Observable<TrainLiveStatusResponse> call = trainApi.getTrainLiveStatus(trainNumber, date, AppConstant.API_KEY);
    call
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new DisposableObserver<TrainLiveStatusResponse>() {
          @Override public void onNext(TrainLiveStatusResponse trainLiveStatusResponse) {
            if(trainLiveStatusResponse.getResponseCode() == 200)
              getView().triggerNextActivity(trainLiveStatusResponse);
            else
              getView().showError("Train doesn't run on selected date.");
          }

          @Override public void onError(Throwable e) {
            Timber.e(e);
          }

          @Override public void onComplete() {

          }
        });
  }
}
