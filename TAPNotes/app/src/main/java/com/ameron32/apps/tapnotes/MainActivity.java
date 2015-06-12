package com.ameron32.apps.tapnotes;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.ameron32.apps.tapnotes._trial._demo.fragment.AnimatedPanesTestFragment;
import com.ameron32.apps.tapnotes._trial._demo.fragment.CollapsingToolbarFragment;
import com.ameron32.apps.tapnotes._trial._demo.fragment.ExpandableDraggableSwipeableTestFragment;
import com.ameron32.apps.tapnotes._trial._demo.fragment.ExpandableTestFragment;
import com.ameron32.apps.tapnotes._trial._demo.fragment.FlippableStackViewTestFragment;
import com.ameron32.apps.tapnotes._trial._demo.fragment.GridTestFragment;
import com.ameron32.apps.tapnotes._trial._demo.fragment.MaterialImageViewTestFragment;
import com.ameron32.apps.tapnotes._trial._demo.fragment.MaterialViewPagerTestFragment;
import com.ameron32.apps.tapnotes._trial._demo.fragment.ParseTestFragment;
import com.ameron32.apps.tapnotes._trial._demo.fragment.TableTestFragment;
import com.ameron32.apps.tapnotes._trial._demo.fragment.TestFragment;
import com.ameron32.apps.tapnotes._trial.ui.TuckawaySlidingPaneLayout;
import com.ameron32.apps.tapnotes.frmk.di.stabbed.mport.ForApplication;
import com.ameron32.apps.tapnotes.impl.activity.DecoratorActivity;
import com.ameron32.apps.tapnotes.impl.fragment.ActivityCallbacks;
import com.ameron32.apps.tapnotes.impl.fragment.EnhancedToolbarFragment;
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
    extends DecoratorActivity
    implements ActivityCallbacks {

  private static final int LOGIN_REQUEST_CODE = 4647;

  @InjectView(R.id.sliding_pane_layout) TuckawaySlidingPaneLayout tuckaway;

  @Inject @ForApplication SharedPreferences sharedPreferences;
  @Inject Bus bus;

  private ToolbarFragment mToolbarFragment;
  private DrawerLayout mDrawerLayout;

  @Override
  protected void onCreate(
      Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    ButterKnife.inject(this);

    setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
    getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_drawer);
    getSupportActionBar().setDisplayShowHomeEnabled(true);

    mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

    NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
    if (navigationView != null) {
      setupDrawerContent(navigationView);
    }
  }

  @Override
  protected int provideLayoutResource() {
    return R.layout.activity_main;
  }

  @Override
  protected void onFinishInject() {
    super.onFinishInject();
    bus.register(this);
  }

  @Override
  protected void onResume() {
    super.onResume();
    mDrawerLayout.openDrawer(GravityCompat.START);
  }

  @Subscribe
  public void hearMessage(
      String message) {
    // TODO: event receipt
  }

  private void setupDrawerContent(NavigationView navigationView) {
    navigationView.setNavigationItemSelectedListener(
        new NavigationView.OnNavigationItemSelectedListener() {
          @Override
          public boolean onNavigationItemSelected(MenuItem menuItem) {
            menuItem.setChecked(true);
            mDrawerLayout.closeDrawers();
            return true;
          }
        });
  }

  @Override
  protected void onDestroy() {
    bus.unregister(this);
    ButterKnife.reset(this);
    super.onDestroy();
  }

  public void changeFragment(
      Fragment newFragment) {
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
//    transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
    transaction.commit();
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

  private void inflateCoreMenu(
      Menu menu) {
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
      case android.R.id.home:
        mDrawerLayout.openDrawer(GravityCompat.START);
        return true;

      case R.id.action_settings:
        startSettingsActivity();
        return true;
      case R.id.action_toggle:
        tuckaway.toggleLayout();
        return true;
    }

    switch (id) {
      case R.id.basic_testfragment:
        changeFragment(TestFragment.create());
        break;
      case R.id.table_testfragment:
        changeFragment(TableTestFragment.create());
        break;
//      case 3:
//        changeFragment(PhotoViewerTestFragment.create());
//        break;
      case R.id.materialimageview_testfragment:
        changeFragment(MaterialImageViewTestFragment.create());
        break;
      case R.id.materialviewpager_testfragment:
        changeFragment(MaterialViewPagerTestFragment.create());
        break;
      case R.id.parse_testfragment:
        changeFragment(ParseTestFragment.create());
        break;
      case R.id.expandable_testfragment:
        changeFragment(ExpandableTestFragment.create());
        break;
      case R.id.collapsing_testfragment:
        changeFragment(CollapsingToolbarFragment.create());
        break;
      case R.id.grid_testfragment:
        changeFragment(GridTestFragment.create());
        break;
      case R.id.animatedpanes_testfragment:
        changeFragment(AnimatedPanesTestFragment.create());
        break;
      case R.id.expandabledraggableswipeable_testfragment:
        changeFragment(ExpandableDraggableSwipeableTestFragment.create());
        break;
      case R.id.flippablestackview_testfragment:
        changeFragment(FlippableStackViewTestFragment.create());
        break;
      case R.id.settings_menu:
        startSettingsActivity();
        break;
      case R.id.about_menu:
        startAbout();
        break;
    }

    return super.onOptionsItemSelected(item);
  }

  public void onLogoutClick() {
    ParseUser.logOut();
    startDispatchActivity();
    finish();
  }

  public void startDispatchActivity() {
    startActivity(MyDispatchMainActivity.makeIntent(getContext()));
  }

  public void startSettingsActivity() {
    startActivity(SettingsActivity.makeIntent(getContext()));
  }

  public void startAbout() {
    final Colors c = new Colors(R.color.myPrimaryColor, R.color.myPrimaryDarkColor);
    final Libs.Builder builder = new Libs.Builder()
        .withFields(R.string.class.getFields())
        .withVersionShown(true)
        .withLicenseShown(true)
        .withAnimations(false)
        .withActivityTitle(this.getResources().getString(R.string.action_about))
        .withActivityTheme(R.style.CustomTAPTheme)
        .withActivityColor(c);
    builder.start(this);
  }

  private Context getContext() {
    return MainActivity.this;
  }

  @Override
  protected void onActivityResult(
      int requestCode,
      int resultCode,
      Intent arg2) {
    // return from ParseLogin
    if (requestCode == LOGIN_REQUEST_CODE) {
      if (resultCode == RESULT_OK) {
        onLoginComplete();
      }
    }
  }

  protected void onLoginComplete() {
    // user is logged in
  }
}
