package com.example.vivekbhalodiya.railticket.feature.fareavailabilty;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import com.example.vivekbhalodiya.railticket.api.model.FareAndAvailabilityModel;
import com.example.vivekbhalodiya.railticket.api.model.TrainAvailability.AvailabilityItem;
import com.example.vivekbhalodiya.railticket.api.model.TrainAvailability.TrainAvailResponse;
import com.example.vivekbhalodiya.railticket.api.model.TrainFare.TrainFareResponse;
import com.example.vivekbhalodiya.railticket.api.services.TrainApi;
import com.example.vivekbhalodiya.railticket.api.services.TrainApiInterface;
import com.example.vivekbhalodiya.railticket.constants.AppConstant;
import com.example.vivekbhalodiya.railticket.feature.base.BaseViewModel;
import com.yarolegovich.lovelydialog.LovelyInfoDialog;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import java.util.List;
import retrofit2.Response;
import timber.log.Timber;

/**
 * Created by vivekbhalodiya on 2/5/18.
 */

public class FareViewModel extends BaseViewModel<FareView> {
  private TrainApiInterface trainApiInterface = new TrainApi().getClient().create(TrainApiInterface.class);
  private ProgressDialog progressDialog;
  private Context context;
  private RecyclerView recyclerView;
  private AvailabilityAdapter availabilityAdapter;

  public void OnClickTrainResult(String trainNum, String fromCode, String toCode, String pref, Context context,
      AvailabilityAdapter availabilityAdapter) {
    this.context = context;
    this.availabilityAdapter=availabilityAdapter;

    Observable<Response<TrainFareResponse>> call = trainApiInterface.getTrainFareWithAvailability(trainNum, fromCode.toLowerCase(), toCode, pref
        , "GN", "27-02-2018", AppConstant.API_KEY);
    Observable<Response<TrainAvailResponse>> availCall =
        trainApiInterface.getAvailability(trainNum, fromCode, toCode, "27-02-2018", pref, "GN", AppConstant.API_KEY);
    progressDialog = new ProgressDialog(context);
    progressDialog.setMessage("Loading..");
    progressDialog.show();

    call
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread());
    availCall
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
  void showProgress(boolean show){
    progressDialog.show();
  }

  void setAdapterData(int fare, List<AvailabilityItem> availability) {
    //availabilityAdapter.setData(availability);
  }
}
