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
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import retrofit2.Response;
import timber.log.Timber;

/**
 * Created by vivekbhalodiya on 2/15/18.
 */

public class TrainSearchViewModel extends BaseViewModel<TrainSearchView> {
  public static String journeyDate = "";
  public static String sourceCode = "";
  public static String destCode = "";
  public static String trainNumber = "";
  public static String preferenceCoach = "3A";
  public static String quota = "GN";
  private TrainApiInterface trainApi = new TrainApi().getClient().create(TrainApiInterface.class);
  private ArrayList<TrainsItem> listOfTrains = new ArrayList<>();
  private ArrayList<ArrayList<ClassesItem>> listsOfClassess = new ArrayList<>();
  private AtomicInteger count = new AtomicInteger();
  private Observable<Response<TrainResponse>> call = trainApi.getTrainBetweenStations(sourceCode, destCode, journeyDate, AppConstant.API_KEY);
  private Observable<Response<TrainFareResponse>> getClassesAvailCall;
  private ArrayList<TrainFareResponse> listOfTrainsAndClass = new ArrayList<>();
  private Observable<TrainSearchResultResponse> trainSearchCall;
  List<String> searchResultList = new ArrayList<>();
  List<SearchItem> searItemsList = new ArrayList<>();
  private SearchAdapter searchAdapter;
  private boolean isTrainsFound = false;
  public void OnClickSearchTrains(View view) {
    count.set(0);
    if (!sourceCode.equals(destCode) && !destCode.equals(sourceCode) && !destCode.isEmpty() && !sourceCode.isEmpty() && !journeyDate.isEmpty()) {
      getView().showProgress(AppConstant.SEARCH_TRAIN_MSG, true);
      trainApi.getTrainBetweenStations(sourceCode, destCode, journeyDate, AppConstant.API_KEY)
          .timeout(10, TimeUnit.SECONDS)
          .subscribeOn(Schedulers.io())
          .observeOn(AndroidSchedulers.mainThread())
          .doOnNext(tr -> {
            if(tr.body().getResponseCode() == 200 && tr.body().getTotal() == 0){
              isTrainsFound = false;
            }
            else if(tr.body().getResponseCode() != 200){
              isTrainsFound = false;
            }
            else{
              isTrainsFound = true;
            }
            listOfTrains = tr.body().getTrains();
          })
          .map(this::getTrainNumbers)
          .flatMap(Observable::fromIterable)
          .concatMap(trainNumber -> trainApi.getTrainFareWithAvailability(trainNumber, trainNumber, destCode, preferenceCoach, quota, journeyDate,
              AppConstant.API_KEY)
              .timeout(20, TimeUnit.SECONDS)
              .doOnError(Timber::e)
              .subscribeOn(Schedulers.io()))
          .subscribeWith(new DisposableObserver<Response<TrainFareResponse>>() {

            @Override public void onComplete() {
              //getView().showProgress("", false);
              getAvailableClasses(listOfTrains);

            }

            @Override public void onNext(Response<TrainFareResponse> trainFareResponseResponse) {
              listOfTrainsAndClass.add(count.get(), trainFareResponseResponse.body());
              listsOfClassess.add(count.getAndIncrement(), trainFareResponseResponse.body().getTrain().getClasses());
              Timber.i("ListSize %s", listsOfClassess.size());
            }

            @Override public void onError(Throwable e) {
              getView().showProgress("", false);
              listOfTrains = new ArrayList<>();
              Timber.e(e);
            }
          });
    } else {
      getView().showSneaker("Invalid Input");
    }
  }

  private List<String> getTrainNumbers(Response<TrainResponse> trainResponse) {
    ArrayList<String> trainList = new ArrayList<>();

    for (int i = 0; i < trainResponse.body().getTrains().size(); i++) {
      trainList.add(i, trainResponse.body().getTrains().get(i).getNumber());
      Timber.i("TrainNumbers %s", trainResponse.body().getTrains().get(i).getNumber());
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
    if(isTrainsFound)
      getView().triggerResultActivity(listOfTrains, finalList);
    else
      getView().showSneaker("No trains found. Please try a different day or a route.");
  }

  void searchStationNames(String query) {
    searchResultList.clear();
    getView().showProgress(AppConstant.SEARCH_STN_NAMES, true);
    trainSearchCall = trainApi.getSearchStationRsult(query, AppConstant.API_KEY);
    trainSearchCall
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new DisposableObserver<TrainSearchResultResponse>() {
          @Override public void onNext(TrainSearchResultResponse trainSearchResultResponseResponse) {
            for (StationsItem item : trainSearchResultResponseResponse.getStations()) {
              Timber.d("Station Name Search %s", item.getName());
              searchResultList.add(item.getName());
              SearchItem searchItem = new SearchItem();
              searchItem.set_text(item.getName() + "---" + item.getCode());
              searItemsList.add(searchItem);
            }
          }

          @Override public void onError(Throwable e) {
            getView().showProgress("", false);
            getView().showSneaker("An error occurred. Please try again.");
            Timber.e(e);
          }

          @Override public void onComplete() {
            Timber.d("SearchList Size %s", searchResultList.size());
           getView().showProgress("", false);
            searchAdapter.setSuggestionsList(searItemsList);
            if (searchResultList.size() > 10) {
              searchAdapter.notifyItemRangeChanged(0, 10);
            } else {
              searchAdapter.notifyDataSetChanged();
            }
          }
        });
  }

  void setSearchAdapter(SearchAdapter searchAdapter) {
    this.searchAdapter = searchAdapter;
  }
}
