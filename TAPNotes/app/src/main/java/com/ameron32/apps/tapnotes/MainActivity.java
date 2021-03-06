package com.ameron32.apps.tapnotes;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.ameron32.apps.tapnotes._trial.ui.TuckawaySlidingPaneLayout;
import com.ameron32.apps.tapnotes.frmk.di.stabbed.mport.ForApplication;
import com.ameron32.apps.tapnotes.impl.activity.DecoratorActivity;
import com.ameron32.apps.tapnotes.impl.fragment.MainToolbarFragment;
import com.ameron32.apps.tapnotes.impl.fragment.ToolbarFragment;
import com.ameron32.apps.tapnotes.parse.MyDispatchMainActivity;
import com.mikepenz.aboutlibraries.Libs;
import com.mikepenz.aboutlibraries.util.Colors;
import com.parse.ParseUser;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.io.IOException;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class MainActivity
    extends
    DecoratorActivity
    implements
    ToolbarFragment.OnToolbarFragmentCallbacks,
    MainToolbarFragment.ActivityCallbacks {

  private static final int LOGIN_REQUEST_CODE = 4647;
  @InjectView(R.id.sliding_pane_layout)
  TuckawaySlidingPaneLayout tuckaway;
  @Inject
  @ForApplication
  SharedPreferences sharedPreferences;
  @Inject
  Bus bus;
  private ToolbarFragment mToolbarFragment;

  @Override
  protected void onCreate(
      Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    onLoginComplete();
  }

  @Override
  protected int provideLayoutResource() {
    return R.layout.activity_main;
  }

  @Override
  protected Bitmap providePaletteImage() {
    final AssetManager assets = getResources().getAssets();
    try {
      return BitmapFactory.decodeStream(assets.open("2015ProgramConventionOptimized_1.png"));
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  @Override
  protected void attachBaseContext(Context newBase) {
    super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
  }

  @Override
  protected void onFinishInject() {
    super.onFinishInject();
    bus.register(this);
  }

  private void onLoginComplete() {
    loadToolbarFragment();
    ButterKnife.inject(this);
  }

  @Override
  protected void onActivityResult(
      int requestCode, int resultCode,
      Intent arg2) {
    // return from ParseLogin
    if (requestCode == LOGIN_REQUEST_CODE) {
      if (resultCode == RESULT_OK) {
        onLoginComplete();
      }
    }
  }

  @Subscribe
  public void hearMessage(String message) {
    // TODO: event receipt
  }

  @Override
  public void onToolbarCreated(
      Toolbar toolbar) {

    setSupportActionBar(toolbar);
    getSupportActionBar().setDisplayShowHomeEnabled(true);
  }

  private void loadToolbarFragment() {
    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
    transaction.replace(R.id.toolbar_actionbar_container, MainToolbarFragment.create());
    transaction.commit();
  }

  @Override
  protected void onDestroy() {
    bus.unregister(this);
    ButterKnife.reset(this);
    super.onDestroy();
  }

  public void changeFragment(Fragment newFragment) {
    final int container = R.id.container;
    final FragmentManager fm = getSupportFragmentManager();
    final FragmentTransaction transaction = fm.beginTransaction();
    final String newTag = newFragment.getClass().getName();

    Fragment fragment = fm.findFragmentByTag(newTag);
    if (fragment == null) {
      fragment = newFragment;
    }

    transaction.replace(container, fragment, newTag);
    transaction.addToBackStack(newTag);
    transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
    transaction.commit();
  }

  @Override
  public void onAttachFragment(Fragment fragment) {
    super.onAttachFragment(fragment);
    supportInvalidateOptionsMenu();
  }

  @Override
  public boolean onCreateOptionsMenu(
      Menu menu) {
    // Only show items in the action bar relevant to this screen
    // if the drawer is not showing. Otherwise, let the drawer
    // decide what to show in the action bar.
    inflateCoreMenu(menu);
    return super.onCreateOptionsMenu(menu);
  }

  private void inflateCoreMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.main, menu);
  }

  @Override
  public boolean onOptionsItemSelected(
      MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();
    switch (id) {
      case R.id.action_settings:
        startSettingsActivity();
        return true;
      case R.id.action_toggle:
        tuckaway.toggleLayout();
        return true;
    }
    return super.onOptionsItemSelected(item);
  }

  public void onLogoutClick() {
    ParseUser.logOut();
    startDispatchActivity();
    finish();
  }

  public void startDispatchActivity() {
    startActivity(new Intent(this, MyDispatchMainActivity.class));
  }

  public void startSettingsActivity() {
    startActivity(new Intent(MainActivity.this, SettingsActivity.class));
  }

  public void startAbout() {
    Colors c = new Colors(R.color.myPrimaryColor, R.color.myPrimaryDarkColor);
    new Libs.Builder()
        .withFields(R.string.class.getFields())
        .withVersionShown(true)
        .withLicenseShown(true)
        .withAnimations(false)
        .withActivityTitle(this.getResources().getString(R.string.action_about))
        .withActivityTheme(R.style.CustomTheme)
        .withActivityColor(c)
        .start(this);
  }
}
