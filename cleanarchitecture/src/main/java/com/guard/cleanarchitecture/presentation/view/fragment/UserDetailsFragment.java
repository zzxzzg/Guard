/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package com.guard.cleanarchitecture.presentation.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.guard.cleanarchitecture.presentation.R;
import com.guard.cleanarchitecture.presentation.internal.di.components.UserComponent;
import com.guard.cleanarchitecture.presentation.model.UserModel;
import com.guard.cleanarchitecture.presentation.presenter.UserDetailsPresenter;
import com.guard.cleanarchitecture.presentation.view.UserDetailsView;
import com.guard.cleanarchitecture.presentation.view.component.AutoLoadImageView;
import javax.inject.Inject;

/**
 * Fragment that shows details of a certain user.
 */
public class UserDetailsFragment extends BaseFragment implements UserDetailsView {

  @Inject UserDetailsPresenter userDetailsPresenter;

  @BindView(R.id.iv_cover) AutoLoadImageView iv_cover;
  @BindView(R.id.tv_fullname) TextView tv_fullname;
  @BindView(R.id.tv_email) TextView tv_email;
  @BindView(R.id.tv_followers) TextView tv_followers;
  @BindView(R.id.tv_description) TextView tv_description;
  @BindView(R.id.rl_progress) RelativeLayout rl_progress;
  @BindView(R.id.rl_retry) RelativeLayout rl_retry;
  @BindView(R.id.bt_retry) Button bt_retry;

  public UserDetailsFragment() {
    setRetainInstance(true);
  }

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    this.getComponent(UserComponent.class).inject(this);
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    final View fragmentView = inflater.inflate(R.layout.fragment_user_details, container, false);
    ButterKnife.bind(this, fragmentView);
    return fragmentView;
  }

  @Override public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    this.userDetailsPresenter.setView(this);
    if (savedInstanceState == null) {
      this.loadUserDetails();
    }
  }

  @Override public void onResume() {
    super.onResume();
    this.userDetailsPresenter.resume();
  }

  @Override public void onPause() {
    super.onPause();
    this.userDetailsPresenter.pause();
  }

  @Override public void onDestroyView() {
    super.onDestroyView();
  }

  @Override public void onDestroy() {
    super.onDestroy();
    this.userDetailsPresenter.destroy();
  }

  @Override public void renderUser(UserModel user) {
    if (user != null) {
      this.iv_cover.setImageUrl(user.getCoverUrl());
      this.tv_fullname.setText(user.getFullName());
      this.tv_email.setText(user.getEmail());
      this.tv_followers.setText(String.valueOf(user.getFollowers()));
      this.tv_description.setText(user.getDescription());
    }
  }

  @Override public void showLoading() {
    this.rl_progress.setVisibility(View.VISIBLE);
    this.getActivity().setProgressBarIndeterminateVisibility(true);
  }

  @Override public void hideLoading() {
    this.rl_progress.setVisibility(View.GONE);
    this.getActivity().setProgressBarIndeterminateVisibility(false);
  }

  @Override public void showRetry() {
    this.rl_retry.setVisibility(View.VISIBLE);
  }

  @Override public void hideRetry() {
    this.rl_retry.setVisibility(View.GONE);
  }

  @Override public void showError(String message) {
    this.showToastMessage(message);
  }

  @Override public Context context() {
    return getActivity().getApplicationContext();
  }

  /**
   * Loads all users.
   */
  private void loadUserDetails() {
    if (this.userDetailsPresenter != null) {
      this.userDetailsPresenter.initialize();
    }
  }

  @OnClick(R.id.bt_retry)
  void onButtonRetryClick() {
    UserDetailsFragment.this.loadUserDetails();
  }
}
