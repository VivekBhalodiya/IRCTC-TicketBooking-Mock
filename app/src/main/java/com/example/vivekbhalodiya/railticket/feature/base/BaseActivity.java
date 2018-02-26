package com.example.vivekbhalodiya.railticket.feature.base;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import com.example.vivekbhalodiya.railticket.BR;
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


  @SuppressWarnings("unchecked") @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    view = (T) this;
    onComponentCreated();
    bindContentView();
    mProgressBar=new ProgressWheel(this);

  }

  @SuppressWarnings("unchecked") public void bindContentView() {

    binding = DataBindingUtil.setContentView(this, getLayoutId());
    viewModel.attach(view);
    binding.setVariable(BR.viewModel,viewModel);
    Timber.i("VIew is attached");
  }

  public void showProgressWheel(boolean show){

      if (mProgressBar != null) {
        mProgressBar.setVisibility(show ? View.VISIBLE : View.GONE);
      }
  }

  protected abstract int getLayoutId();

  protected abstract void onComponentCreated();

}
