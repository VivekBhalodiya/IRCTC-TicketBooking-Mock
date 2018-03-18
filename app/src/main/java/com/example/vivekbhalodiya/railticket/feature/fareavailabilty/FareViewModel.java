package com.example.vivekbhalodiya.railticket.feature.fareavailabilty;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import com.example.vivekbhalodiya.railticket.api.model.FareAndAvailabilityModel;
import com.example.vivekbhalodiya.railticket.api.model.TrainAvailability.AvailabilityItem;
import com.example.vivekbhalodiya.railticket.api.model.TrainAvailability.TrainAvailResponse;
import com.example.vivekbhalodiya.railticket.api.model.TrainBetween.TrainsItem;
import com.example.vivekbhalodiya.railticket.api.model.TrainFare.TrainFareResponse;
import com.example.vivekbhalodiya.railticket.api.services.TrainApi;
import com.example.vivekbhalodiya.railticket.api.services.TrainApiInterface;
import com.example.vivekbhalodiya.railticket.constants.AppConstant;
import com.example.vivekbhalodiya.railticket.feature.base.BaseViewModel;
import com.example.vivekbhalodiya.railticket.feature.trainsearch.TrainSearchView;
import com.example.vivekbhalodiya.railticket.feature.trainsearch.TrainSearchViewModel;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import java.util.List;
import java.util.concurrent.TimeUnit;
import retrofit2.Response;
import timber.log.Timber;

/**
 * Created by vivekbhalodiya on 2/5/18.
 */

public class FareViewModel extends BaseViewModel<FareView> {
  private TrainApiInterface trainApiInterface = new TrainApi().getClient().create(TrainApiInterface.class);
  private ProgressDialog progressDialog;
  private String trainNum;
  private String fromCode;
  private String toCode;
  private TrainsItem trainsItem;
  private String pref;
  private Context context;
  private RecyclerView recyclerView;
  private AvailabilityAdapter availabilityAdapter;
  private ClassessAdapter classessAdapter;

  public void OnClickTrainResult(TrainsItem trainsItem, String pref, Context context, AvailabilityAdapter adapter,
      ClassessAdapter classessAdapter) {
    this.trainNum = trainsItem.getNumber();
    this.fromCode = trainsItem.getFromStation().getCode();
    this.toCode = trainsItem.getToStation().getCode();
    this.trainsItem = trainsItem;
    this.pref = pref;
    this.context = context;
    this.availabilityAdapter = adapter;
    this.classessAdapter = classessAdapter;
    Observable<Response<TrainFareResponse>> call = trainApiInterface.getTrainFareWithAvailability(trainNum, fromCode.toLowerCase(), toCode, pref
        , TrainSearchViewModel.quota, TrainSearchViewModel.journeyDate, AppConstant.API_KEY);
    Observable<Response<TrainAvailResponse>> availCall =
        trainApiInterface.getAvailability(trainNum, fromCode, toCode, TrainSearchViewModel.journeyDate, pref, TrainSearchViewModel.quota, AppConstant.API_KEY);
    progressDialog = new ProgressDialog(context);
    progressDialog.setMessage("Loading..");
    progressDialog.show();

    call
        .timeout(5, TimeUnit.SECONDS)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread());
    availCall
        .timeout(5, TimeUnit.SECONDS)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread());

    Observable.zip(call, availCall,
        FareAndAvailabilityModel::new)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new DisposableObserver<FareAndAvailabilityModel>() {
          @Override public void onNext(FareAndAvailabilityModel fareAndAvailabilityModel) {
            setAdapterData(fareAndAvailabilityModel.getFareResponse().body().getFare(),
                fareAndAvailabilityModel.getAvailResponse().body().getAvailability());
          }

          @Override public void onError(Throwable e) {
            Timber.e(e);
            progressDialog.dismiss();
          }

          @Override public void onComplete() {
            progressDialog.dismiss();
          }
        });
  }

  void showProgress(boolean show) {
    progressDialog.show();
  }

  void setAdapterData(int fare, List<AvailabilityItem> availability) {
    Timber.i("Fare Model %s",trainsItem);
    availabilityAdapter.setData(availability,trainsItem,pref);
    classessAdapter.setData(fare);
  }
}
