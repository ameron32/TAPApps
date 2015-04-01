/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015. ameron32
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.ameron32.apps.tapnotes;

import android.app.Activity;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.Toolbar;

import com.ameron32.apps.tapnotes._trial._demo.MaterialImageViewTestFragment;
import com.ameron32.apps.tapnotes._trial._demo.ParseTestFragment;
import com.ameron32.apps.tapnotes._trial._demo.PhotoViewerTestFragment;
import com.ameron32.apps.tapnotes._trial._demo.TableTestFragment;
import com.ameron32.apps.tapnotes._trial._demo.TestFragment;
import com.ameron32.apps.tapnotes.di.controller.ActivitySharedPreferencesController;
import com.mikepenz.iconics.typeface.FontAwesome;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import javax.inject.Inject;

/**
 * Created by klemeilleur on 3/27/2015.
 */
public class MainToolbarFragment extends ToolbarFragment {

  public static MainToolbarFragment newInstance() {
    MainToolbarFragment f = new MainToolbarFragment();
    return f;
  }

  private static final String TEACH_DRAWER_PREF_KEY = "TeachDrawer";

  @Inject
  ActivitySharedPreferencesController prefController;

  private ActivityCallbacks mMainActivity;
  private Drawer.Result mDrawer;

  @Override
  public void onAttach(Activity activity) {
    super.onAttach(activity);
    // TODO: replace with interface rather than explicit Activity reference
    if (activity instanceof ActivityCallbacks) {
      this.mMainActivity = (ActivityCallbacks) activity;
    } else {
      throw new IllegalStateException("activity should inherit ActivityCallbacks");
    }
  }

  @Override
  public void onDetach() {
    mMainActivity = null;
    super.onDetach();
  }

  @Override
  public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    mDrawer = new Drawer().withActivity(getActivity())
        .withTranslucentStatusBar(true).withToolbar(getToolbar())
        .withHeader(getHeaderView(R.layout.trial_header_only))
        .withActionBarDrawerToggle(true)
        .withDrawerWidthRes(R.dimen.navigation_drawer_width)
        .addDrawerItems(getDrawerItems())
        .withOnDrawerItemClickListener(getDrawerOnItemClickListener())
        .build();
  }

  @Override
  public void onResume() {
    super.onResume();

    prefController.runOnce(TEACH_DRAWER_PREF_KEY,
        new SuccessfulRunnable() {
      @Override
      public boolean run() {
        mDrawer.openDrawer();
        return mDrawer.isDrawerOpen();
      }
    });
  }

  private IDrawerItem[] getDrawerItems() {
    return new IDrawerItem[]{
        new PrimaryDrawerItem().withIdentifier(1).withName("TestFragment").withIcon(FontAwesome.Icon.faw_coffee),
        new PrimaryDrawerItem().withIdentifier(2).withName("TableTestFragment").withIcon(FontAwesome.Icon.faw_coffee),
        new PrimaryDrawerItem().withIdentifier(3).withName("PhotoViewerTestFragment").withIcon(FontAwesome.Icon.faw_photo),
        new PrimaryDrawerItem().withIdentifier(4).withName("MaterialImageViewTestFragment").withIcon(FontAwesome.Icon.faw_image),
        new PrimaryDrawerItem().withIdentifier(7).withName("RxParseTestFragment").withIcon(FontAwesome.Icon.faw_image),
        new DividerDrawerItem(),
        new SecondaryDrawerItem().withIdentifier(5).withName("Settings").withIcon(FontAwesome.Icon.faw_cog),
        new SecondaryDrawerItem().withIdentifier(6).withName("About...").withIcon(FontAwesome.Icon.faw_cog)
    };
  }

  private Drawer.OnDrawerItemClickListener getDrawerOnItemClickListener() {
    return new Drawer.OnDrawerItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id, IDrawerItem iDrawerItem) {
        switch (iDrawerItem.getIdentifier()) {
          case 1:
            mMainActivity.changeFragment(new TestFragment());
            break;
          case 2:
            mMainActivity.changeFragment(new TableTestFragment());
            break;
          case 3:
            mMainActivity.changeFragment(new PhotoViewerTestFragment());
            break;
          case 4:
            mMainActivity.changeFragment(new MaterialImageViewTestFragment());
            break;
          case 7:
            mMainActivity.changeFragment(new ParseTestFragment());
            break;
          case 5:
            mMainActivity.startSettingsActivity();
            break;
          case 6:
            mMainActivity.startAbout();
            break;
          default:
            mMainActivity.changeFragment(new TestFragment());
        }
      }
    };
  }

  private View getHeaderView(@LayoutRes int inflateLayout) {
    final ViewGroup rootView = (ViewGroup) getView();

    final View view = LayoutInflater
        .from(rootView.getContext())
        .inflate(inflateLayout, rootView, false);
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
        mMainActivity.onLogoutClick();
      }
    });
  }

  private void closeDrawer() {
    mDrawer.closeDrawer();
  }

  public interface ActivityCallbacks {
    public void changeFragment(Fragment f);
    public void startAbout();
    public void startSettingsActivity();
    public void onLogoutClick();
  }
}