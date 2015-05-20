package com.ameron32.apps.tapnotes.frmk.di.stabbed;

/*
 * Copyright 2013 Philip Schiffer
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import dagger.ObjectGraph;

import com.ameron32.apps.tapnotes.frmk.di.stabbed.mport.StabbedContext;

import java.util.List;

public abstract class AbsStabbedActionBarActivity
    extends FragmentActivity
    implements StabbedContext, ActivityWithDelegate {

  private AppCompatDelegate mDelegate;


  private final ExtendedGraphHelper mExtendedGraphHelper = new ExtendedGraphHelper();

  @Override
  protected void onCreate(final Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    onCreateD(savedInstanceState);
    mExtendedGraphHelper.onCreate(this, getModules(), this);
    onFinishInject();
  }

  protected void onFinishInject() {}

  @Override
  protected void onDestroy() {
    mExtendedGraphHelper.onDestroy();
    onDestroyD();
    super.onDestroy();
  }

  /**
   * A list of modules to use for the individual activity graph. Subclasses can override this
   * method to provide additional modules provided they call and include the modules returned by
   * calling {@code super.getModules()}.
   */
  protected abstract List<Object> getModules();

  @Override
  public void inject(final Object object) {
    mExtendedGraphHelper.inject(object);
  }

  @Override
  public ObjectGraph getObjectGraph() {
    return mExtendedGraphHelper.getExtendedGraph();
  }



  protected void onCreateD(Bundle savedInstanceState) {
    getDelegate().installViewFactory();
    getDelegate().onCreate(savedInstanceState);
  }

  @Override
  protected void onPostCreate(Bundle savedInstanceState) {
    super.onPostCreate(savedInstanceState);
    getDelegate().onPostCreate(savedInstanceState);
  }

  public ActionBar getSupportActionBar() {
    return getDelegate().getSupportActionBar();
  }

  public void setSupportActionBar(@Nullable Toolbar toolbar) {
    getDelegate().setSupportActionBar(toolbar);
  }

  @Override
  public MenuInflater getMenuInflater() {
    return getDelegate().getMenuInflater();
  }

  @Override
  public void setContentView(@LayoutRes int layoutResID) {
    getDelegate().setContentView(layoutResID);
  }

  @Override
  public void setContentView(View view) {
    getDelegate().setContentView(view);
  }

  @Override
  public void setContentView(View view, ViewGroup.LayoutParams params) {
    getDelegate().setContentView(view, params);
  }

  @Override
  public void addContentView(View view, ViewGroup.LayoutParams params) {
    getDelegate().addContentView(view, params);
  }

  @Override
  protected void onPostResume() {
    super.onPostResume();
    getDelegate().onPostResume();
  }

  @Override
  protected void onTitleChanged(CharSequence title, int color) {
    super.onTitleChanged(title, color);
    getDelegate().setTitle(title);
  }

  @Override
  public void onConfigurationChanged(Configuration newConfig) {
    super.onConfigurationChanged(newConfig);
    getDelegate().onConfigurationChanged(newConfig);
  }

  @Override
  protected void onStop() {
    super.onStop();
    getDelegate().onStop();
  }

  protected void onDestroyD() {
    getDelegate().onDestroy();
  }

  public void invalidateOptionsMenu() {
    getDelegate().invalidateOptionsMenu();
  }

  public Activity getActivity() {
    return this;
  }

  public AppCompatDelegate getDelegate() {
    if (mDelegate == null) {
      mDelegate = AppCompatDelegate.create(this, null);
    }
    return mDelegate;
  }
}
