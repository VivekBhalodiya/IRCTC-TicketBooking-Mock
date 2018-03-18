package com.example.vivekbhalodiya.railticket.feature.trainsearch;

import android.view.View;
import com.example.vivekbhalodiya.railticket.api.model.TrainBetween.TrainResponse;
import com.example.vivekbhalodiya.railticket.api.model.TrainBetween.TrainsItem;
import com.example.vivekbhalodiya.railticket.api.model.TrainFare.ClassesItem;
import com.example.vivekbhalodiya.railticket.api.model.TrainFare.TrainFareResponse;
import com.example.vivekbhalodiya.railticket.api.model.TrainSearchResult.StationsItem;
import com.example.vivekbhalodiya.railticket.api.model.TrainSearchResult.TrainSearchResultResponse;
import com.example.vivekbhalodiya.railticket.api.services.TrainApi;
import com.example.vivekbhalodiya.railticket.api.services.TrainApiInterface;
import com.example.vivekbhalodiya.railticket.constants.AppConstant;
import com.example.vivekbhalodiya.railticket.feature.base.BaseViewModel;
import com.lapism.searchview.SearchAdapter;
import com.lapism.searchview.SearchItem;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import retrofit2.Response;
import timber.log.Timber;

/**
 * Created by vivekbhalodiya on 2/15/18.
 */

public class TrainSearchViewModel extends BaseViewModel<TrainSearchView> {
  public static String journeyDate = "15-04-2018";
  public static String sourceCode = "hyb";
  public static String destCode = "cstm";
  private static String trainNumber = "";
  public static String preferenceCoach = "3A";
  public static String quota = "GN";
  private TrainApiInterface trainApi = new TrainApi().getClient().create(TrainApiInterface.class);
  private ArrayList<TrainsItem> listOfTrains = new ArrayList<>();
  private ArrayList<ArrayList<ClassesItem>> listsOfClassess = new ArrayList<>();
  private AtomicInteger count = new AtomicInteger();
  private Observable<TrainResponse> call = trainApi.getTrainBetweenStations(sourceCode, destCode, journeyDate, AppConstant.API_KEY);
  private Observable<Response<TrainFareResponse>> getClassesAvailCall;
  private ArrayList<TrainFareResponse> listOfTrainsAndClass = new ArrayList<TrainFareResponse>();
  private Observable<TrainSearchResultResponse> trainSearchCall;
  List<String> searchResultList = new ArrayList<>();
  List<SearchItem> searItemsList =  new ArrayList<>();
  private SearchAdapter searchAdapter;

  public String getJourneyDate() {
    return journeyDate;
  }

  public void OnDatePickerClick(View view) {

  }

  public void setJourneyDate(String journeyDate) {
  }

  public void OnClickSearchTrains(View view) {
    count.set(0);
    getView().showProgress(true);
    trainApi.getTrainBetweenStations(sourceCode, destCode, journeyDate, AppConstant.API_KEY)
        .timeout(10, TimeUnit.SECONDS)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .doOnNext(tr -> {
          listOfTrains = tr.getTrains();
        })
        .map(this::getTrainNumbers)
        .flatMap(Observable::fromIterable)
        .concatMap(trainNumber -> trainApi.getTrainFareWithAvailability(trainNumber, trainNumber, destCode, preferenceCoach, quota, journeyDate,
            AppConstant.API_KEY)
            .timeout(20,TimeUnit.SECONDS)
            .subscribeOn(Schedulers.io()))
        .subscribeWith(new DisposableObserver<Response<TrainFareResponse>>() {

          @Override public void onComplete() {
            getAvailableClasses(listOfTrains);
          }

          @Override public void onNext(Response<TrainFareResponse> trainFareResponseResponse) {
            listOfTrainsAndClass.add(count.get(), trainFareResponseResponse.body());
            listsOfClassess.add(count.getAndIncrement(), trainFareResponseResponse.body().getTrain().getClasses());
            Timber.i("ListSize %s", listsOfClassess.size());
          }

          @Override public void onError(Throwable e) {
            getView().showProgress(false);
            listOfTrains = new ArrayList<>();
            Timber.e(e);
          }
        });
  }

  private List<String> getTrainNumbers(TrainResponse trainResponse) {
    ArrayList<String> trainList = new ArrayList<>();

    for (int i = 0; i < trainResponse.getTrains().size(); i++) {
      trainList.add(i, trainResponse.getTrains().get(i).getNumber());
      Timber.i("TrainNumbers %s", trainResponse.getTrains().get(i).getNumber());
    }
    return trainList;
  }

  private void getAvailableClasses(ArrayList<TrainsItem> listOfTrains) {
    ArrayList<ArrayList<ClassesItem>> finalList = new ArrayList<>();

  /*  for (int i = 0; i < listOfTrainsAndClass.size(); i++) {
      for (int j = 0; j < listOfTrainsAndClass.get(i).getTrain().getClasses().size(); j++)
        Timber.i("TrainResponse Train name = %s Train Num = %s, Fare Response Num= %s Class %s = %s",
            listOfTrains.get(i).getName(),
            listOfTrains.get(i).getNumber(),
            listOfTrainsAndClass.get(i).getTrain().getNumber(),
            listOfTrainsAndClass.get(i).getTrain().getClasses().get(j).getCode(),
            listOfTrainsAndClass.get(i).getTrain().getClasses().get(j).getAvailable());
    }*/

    for (ArrayList<ClassesItem> list : listsOfClassess) {
      ArrayList<ClassesItem> tmpList = new ArrayList<>();
      for (ClassesItem item : list) {
        Timber.i("ClassDetails %s = %s", item.getCode(), item.getAvailable());
        if (item.getAvailable().equals("Y")) {
          tmpList.add(item);
        }
      }
      finalList.add(tmpList);
    }
    getView().triggerResultActivity(listOfTrains, finalList);
  }

  void searchStationNames(String query){
    searchResultList.clear();
    getView().showProgress(true);
    trainSearchCall = trainApi.getSearchStationRsult(query,AppConstant.API_KEY);
    trainSearchCall
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new DisposableObserver<TrainSearchResultResponse>() {
          @Override public void onNext(TrainSearchResultResponse trainSearchResultResponseResponse) {
            for (StationsItem item : trainSearchResultResponseResponse.getStations()){
              Timber.d("Station Name Search %s",item.getName());
              searchResultList.add(item.getName() + "::" + item.getCode());
              SearchItem searchItem = new SearchItem();
              searchItem.set_text(item.getName());
              searItemsList.add(searchItem);

            }
            getView().showProgress(false);
          }

          @Override public void onError(Throwable e) {
            Timber.e(e);
          }

          @Override public void onComplete() {
            Timber.d("SearchList Size %s",searchResultList.size());
            searchAdapter.setSuggestionsList(searItemsList);
            if(searchResultList.size() > 10)
              searchAdapter.notifyItemRangeChanged(0,10);
            else
              searchAdapter.notifyDataSetChanged();
          }
        });
  }

  void setSearchAdapter(SearchAdapter searchAdapter) {
    this.searchAdapter = searchAdapter;
  }
}
