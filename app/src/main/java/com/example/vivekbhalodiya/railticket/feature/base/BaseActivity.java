package com.example.vivekbhalodiya.railticket.feature.base;

import android.app.ProgressDialog;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import com.example.vivekbhalodiya.railticket.BR;
import com.example.vivekbhalodiya.railticket.R;
import com.irozon.sneaker.Sneaker;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.pnikosis.materialishprogress.ProgressWheel;
import timber.log.Timber;

/**
 * Created by vivekbhalodiya on 1/30/18.
 */
public abstract class BaseActivity<B extends ViewDataBinding, V extends MvvmViewModel, T extends MvvmView> extends AppCompatActivity
    implements MvvmView {

  protected V viewModel;
  protected B binding;
  protected T view;
  ProgressWheel mProgressBar;
  ProgressDialog progressDialog;



  @SuppressWarnings("unchecked") @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    view = (T) this;
    onComponentCreated();
    bindContentView();
    progressDialog = new ProgressDialog(this);

  }

  @SuppressWarnings("unchecked") public void bindContentView() {

    binding = DataBindingUtil.setContentView(this, getLayoutId());
    viewModel.attach(view);
    binding.setVariable(BR.viewModel,viewModel);
    Timber.i("VIew is attached");
  }

  public void showPreogressDialog(String message,boolean show){
   progressDialog.setMessage(message);
   if(show)
     progressDialog.show();
   else
     progressDialog.dismiss();
  }

  public void showSneakerError(String message){
    Sneaker.with(this)
        .setTitle("Error !!")
        .setMessage(message)
        .sneakError();
  }

  public void showSneakerSucccess(String message){
    Sneaker.with(this)
        .setMessage("Yey!!")
        .setMessage(message)
        .sneak(ContextCompat.getColor(getApplicationContext(), R.color.success_green));
  }

  protected abstract int getLayoutId();

  protected abstract void onComponentCreated();

}
