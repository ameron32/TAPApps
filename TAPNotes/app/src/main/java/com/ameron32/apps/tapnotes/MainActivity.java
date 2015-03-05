package com.ameron32.apps.tapnotes;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.location.LocationManager;
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
import com.ameron32.apps.tapnotes._trial._demo.TestFragment;
import com.ameron32.apps.tapnotes.di.ActivitySnackBarController;
import com.ameron32.apps.tapnotes.di.stabbed.AbsActionBarActivity;
import com.crashlytics.android.Crashlytics;
import com.mikepenz.iconics.typeface.FontAwesome;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import javax.inject.Inject;

import butterknife.ButterKnife;
import io.fabric.sdk.android.Fabric;


public class MainActivity
    extends
      AbsActionBarActivity
    implements
      ToolbarFragment.OnToolbarFragmentCallbacks
//      ,ContentManager.OnContentChangeListener
//      ,FragmentProvider.OnFragmentChangeListener
//      , NavigationDrawerFragment.NavigationDrawerCallbacks
{
//
//  @Inject
//  FragmentProvider mContentManager;

//  @Inject
//  LocationManager mLocation;

  /**
   * Fragment managing the behaviors, interactions and presentation of the
   * navigation drawer.
   */
//  private NavigationDrawerFragment mNavigationDrawerFragment;

  private ToolbarFragment mToolbarFragment;

  @Override
  protected void onCreate(
      Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Fabric.with(this, new Crashlytics());
    setContentView(R.layout.activity_main);
    loadToolbarFragment();
    ButterKnife.inject(this);
  }

  private Drawer.Result mDrawer;

  @Override
  public void onToolbarCreated(
      Toolbar toolbar) {

    setSupportActionBar(toolbar);
    getSupportActionBar().setDisplayShowHomeEnabled(true);

    mDrawer = new Drawer().withActivity(this)
        .withTranslucentStatusBar(true).withToolbar(toolbar)
        .withHeader(getHeaderView(R.layout.trial_header_only))
        .withActionBarDrawerToggle(true)
        .addDrawerItems(getDrawerItems())
        .withOnDrawerItemClickListener(getDrawerOnItemClickListener())
        .build();

    mDrawer.openDrawer();

//    mNavigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);

//    mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
    // drawerLayout.setStatusBarBackgroundColor(getResources().getColor(R.color.colorPrimary));
//    mNavigationDrawerFragment.setup(R.id.navigation_drawer, mDrawerLayout, mToolbar);
  }

  private IDrawerItem[] getDrawerItems() {
    return new IDrawerItem[]{
        new PrimaryDrawerItem().withName("TestFragment").withIcon(FontAwesome.Icon.faw_coffee),
        new PrimaryDrawerItem().withName("PhotoViewerTestFragment").withIcon(FontAwesome.Icon.faw_photo),
        new PrimaryDrawerItem().withName("MaterialImageViewTestFragment").withIcon(FontAwesome.Icon.faw_image),
        new DividerDrawerItem(),
        new SecondaryDrawerItem().withName("Settings").withIcon(FontAwesome.Icon.faw_cog)
    };
  }

  private Drawer.OnDrawerItemClickListener getDrawerOnItemClickListener() {
    return new Drawer.OnDrawerItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id, IDrawerItem iDrawerItem) {
        switch (position) {
          case 1:
            changeFragment(new TestFragment());
            break;
          case 2:
            changeFragment(new PhotoViewerTestFragment());
            break;
          case 3:
            changeFragment(new MaterialImageViewTestFragment());
            break;
          default:
            changeFragment(new TestFragment());
        }
      }
    };
  }

  @Inject
  LayoutInflater mInflater;

  private View getHeaderView(@LayoutRes int inflateLayout) {
    final ViewGroup rootView = (ViewGroup) getWindow().getDecorView().getRootView();

    final View view = mInflater.inflate(inflateLayout, rootView, false);
    tintDrawerArrow(view);
    setDrawerButtonListeners(view);

    return view;
  }


  private void tintDrawerArrow(View view) {
    ImageButton upButton = (ImageButton) view.findViewById(R.id.imagebutton_navigation_drawer_up_arrow);
    final Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
    upArrow.setColorFilter(getResources().getColor(android.R.color.black), PorterDuff.Mode.SRC_ATOP);
    upButton.setImageDrawable(upArrow);
  }

  private void setDrawerButtonListeners(View view) {
    ImageButton upButton = (ImageButton) view.findViewById(R.id.imagebutton_navigation_drawer_up_arrow);
    upButton.setOnClickListener(new View.OnClickListener() {

      @Override public void onClick(
          View v) {
        closeDrawer();
      }
    });

    ImageButton logoutButton = (ImageButton) view.findViewById(R.id.imagebutton_navigation_drawer_logout);
    logoutButton.setOnClickListener(new View.OnClickListener() {

      @Override public void onClick(
          View v) {
        onLogoutClick();
      }
    });
  }

  private void closeDrawer() {
    mDrawer.closeDrawer();
  }

  private void loadToolbarFragment() {
    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
    transaction.replace(R.id.toolbar_actionbar_container, ToolbarFragment.newInstance());
    transaction.commit();
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    ButterKnife.reset(this);
  }

  @Override
  protected void onResume() {
    super.onResume();
//    mContentManager.addOnContentChangeListener(this);
//    mContentManager.addFragmentChangeListener(this);
  }

  @Override
  protected void onPause() {
    super.onPause();
//    mContentManager.removeOnContentChangeListener(this);
//    mContentManager.removeFragmentChangeListener(this);
  }

//  @Override public void onContentChange(
//      final ContentManager manager,
//      final int position) {
  // update the main content by replacing fragments
  // changeFragment(mContentManager.getFragment(position));
//  }

  private void changeFragment(Fragment fragment) {
    FragmentManager fragmentManager = getSupportFragmentManager();
    FragmentTransaction transaction = fragmentManager.beginTransaction();
    transaction.replace(R.id.container, fragment);
    transaction.commit();
  }

//  @Override
//  public void onFragmentChange(int position) {
//    onContentChange(null, position);
//  }

  public void onSectionAttached(
      int number) {
    supportInvalidateOptionsMenu();
  }

  @Override
  public boolean onCreateOptionsMenu(
      Menu menu) {
    if (!mDrawer.isDrawerOpen()) {
      // Only show items in the action bar relevant to this screen
      // if the drawer is not showing. Otherwise, let the drawer
      // decide what to show in the action bar.

      return true;
    }
    inflateCoreMenu(menu);
//    inflateGlobalMenu(menu);
    return super.onCreateOptionsMenu(menu);
  }

  private void inflateCoreMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.main, menu);
  }

//  private void inflateGlobalMenu(Menu menu) {
//    getMenuInflater().inflate(R.menu.global, menu);
//  }

  @Override
  public boolean onOptionsItemSelected(
      MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();
    if (id == R.id.action_settings) {
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
  }
}

