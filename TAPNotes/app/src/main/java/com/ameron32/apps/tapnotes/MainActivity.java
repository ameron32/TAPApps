package com.ameron32.apps.tapnotes;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;

import com.ameron32.apps.tapnotes._trial._demo.MaterialImageViewTestFragment;
import com.ameron32.apps.tapnotes._trial._demo.PhotoViewerTestFragment;
import com.ameron32.apps.tapnotes._trial._demo.TableTestFragment;
import com.ameron32.apps.tapnotes._trial._demo.TestFragment;
import com.ameron32.apps.tapnotes.di.controller.ActivitySharedPreferencesController;
import com.ameron32.apps.tapnotes.di.stabbed.AbsRxActionBarActivity;
import com.ameron32.apps.tapnotes.parse.MyDispatchMainActivity;
import com.crashlytics.android.Crashlytics;
import com.mikepenz.aboutlibraries.Libs;
import com.mikepenz.aboutlibraries.util.Colors;
import com.mikepenz.iconics.typeface.FontAwesome;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.parse.ParseUser;

import javax.inject.Inject;

import butterknife.ButterKnife;
import de.psdev.stabbedandroid.ForActivity;
import de.psdev.stabbedandroid.ForApplication;
import io.fabric.sdk.android.Fabric;


public class MainActivity
    extends
    AbsRxActionBarActivity
    implements
      ToolbarFragment.OnToolbarFragmentCallbacks,
      MainToolbarFragment.ActivityCallbacks
{

  private ToolbarFragment mToolbarFragment;

  @Override
  protected void onCreate(
      Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Fabric.with(this, new Crashlytics());
    setThisTheme();
    setContentView(R.layout.activity_main);
    onLoginComplete();
  }

  private void onLoginComplete() {
    loadToolbarFragment();
    ButterKnife.inject(this);
  }

    /*
   * RETURN from LoginActivity
   */

  private static final int LOGIN_REQUEST_CODE = 4647;

  @Override protected void onActivityResult(
      int requestCode, int resultCode,
      Intent arg2) {
    // return from ParseLogin
    if (requestCode == LOGIN_REQUEST_CODE) {
      if (resultCode == RESULT_OK) {
        onLoginComplete();
      }
    }
  }

  /*
   * TEACH DRAWER then never open automatically again
   */

  @Inject
  @ForApplication
  SharedPreferences sharedPreferences;

  @Override
  public void onToolbarCreated(
      Toolbar toolbar) {

    setSupportActionBar(toolbar);
    getSupportActionBar().setDisplayShowHomeEnabled(true);


//    final PreparingRunner oneTimeRunner
//        = new PreparingRunner(new PreparingRunner.PreparingRunnable() {
//      public boolean hasRun() {
//        return sharedPreferences.getBoolean(TEACH_DRAWER_PREF_KEY, false);
//      }
//
//      public boolean runWithResult() {
//        mDrawer.openDrawer();
//        return mDrawer.isDrawerOpen();
//      }
//
//      @Override
//      public void onRunComplete(boolean runFinishedSuccessfully) {
//        if (runFinishedSuccessfully) {
//          sharedPreferences.edit().putBoolean(TEACH_DRAWER_PREF_KEY, true).commit();
//        }
//      }
//    });
//    oneTimeRunner.run();

//    prefController.runOnce(TEACH_DRAWER_PREF_KEY, new SuccessfulRunnable() {
//      @Override
//      public boolean run() {
//        mDrawer.openDrawer();
//        return mDrawer.isDrawerOpen();
//      }
//    });

//    mNavigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);

//    mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
    // drawerLayout.setStatusBarBackgroundColor(getResources().getColor(R.color.colorPrimary));
//    mNavigationDrawerFragment.setup(R.id.navigation_drawer, mDrawerLayout, mToolbar);
  }

  private void loadToolbarFragment() {
    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
    transaction.replace(R.id.toolbar_actionbar_container, MainToolbarFragment.newInstance());
    transaction.commit();
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    ButterKnife.reset(this);
  }

  public void changeFragment(Fragment fragment) {
    FragmentManager fragmentManager = getSupportFragmentManager();
    FragmentTransaction transaction = fragmentManager.beginTransaction();
    transaction.replace(R.id.container, fragment);
    transaction.commit();
  }

  public void onSectionAttached(
      int number) {
    supportInvalidateOptionsMenu();
  }

  @Override
  public boolean onCreateOptionsMenu(
      Menu menu) {
//    if (!mDrawer.isDrawerOpen()) {
      // Only show items in the action bar relevant to this screen
      // if the drawer is not showing. Otherwise, let the drawer
      // decide what to show in the action bar.

//      return true;
//    }
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
    if (id == R.id.action_settings) {
      startSettingsActivity();
      return true;
    }
    return super.onOptionsItemSelected(item);
  }

//  @Inject
//  @ForApplication
//  Context mContext;

//  TODO: fails to @Inject. likely module hasn't loaded at the time @Inject calls
//  @Inject
//  ActivitySnackBarController snackBarController;

  public void onLogoutClick() {
//    snackBarController.toast("Logout");
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


  private void setThisTheme() {
//    final Random r = new Random();
//    final boolean custom = r.nextBoolean();
//    if (custom) {
//      setTheme(R.style.Theme_AppCompat);
//    } else {
      setTheme(R.style.CustomTheme);
//    }
  }
}