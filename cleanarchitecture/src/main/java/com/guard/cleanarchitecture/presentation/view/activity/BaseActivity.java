package com.guard.cleanarchitecture.presentation.view.activity;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import com.guard.cleanarchitecture.presentation.AndroidApplication;
import com.guard.cleanarchitecture.presentation.internal.di.components.ApplicationComponent;
import com.guard.cleanarchitecture.presentation.internal.di.modules.ActivityModule;
import com.guard.cleanarchitecture.presentation.navigation.Navigator;
import javax.inject.Inject;

/**
 * Base {@link Activity} class for every Activity in this application.
 */
public abstract class BaseActivity extends Activity {

  @Inject Navigator navigator;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    this.getApplicationComponent().inject(this);
  }

  /**
   * Adds a {@link Fragment} to this activity's layout.
   *
   * @param containerViewId The container view to where add the fragment.
   * @param fragment The fragment to be added.
   */
  protected void addFragment(int containerViewId, Fragment fragment) {
    FragmentTransaction fragmentTransaction = this.getFragmentManager().beginTransaction();
    fragmentTransaction.add(containerViewId, fragment);
    fragmentTransaction.commit();
  }

  /**
   * Get the Main Application component for dependency injection.
   *
   * @return {@link com.guard.cleanarchitecture.presentation.internal.di.components.ApplicationComponent}
   */
  protected ApplicationComponent getApplicationComponent() {
    return ((AndroidApplication)getApplication()).getApplicationComponent();
  }

  /**
   * Get an Activity module for dependency injection.
   *
   * @return {@link com.guard.cleanarchitecture.presentation.internal.di.modules.ActivityModule}
   */
  protected ActivityModule getActivityModule() {
    return new ActivityModule(this);
  }
}
