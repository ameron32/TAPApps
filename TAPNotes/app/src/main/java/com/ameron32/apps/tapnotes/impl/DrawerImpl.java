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

package com.ameron32.apps.tapnotes.impl;

import android.app.Activity;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;

import com.ameron32.apps.tapnotes.R;
import com.ameron32.apps.tapnotes._trial._demo.fragment.CollapsingToolbarFragment;
import com.ameron32.apps.tapnotes._trial._demo.fragment.ExpandableTestFragment;
import com.ameron32.apps.tapnotes._trial._demo.fragment.MaterialImageViewTestFragment;
import com.ameron32.apps.tapnotes._trial._demo.fragment.ParseTestFragment;
import com.ameron32.apps.tapnotes._trial._demo.fragment.PhotoViewerTestFragment;
import com.ameron32.apps.tapnotes._trial._demo.fragment.TableTestFragment;
import com.ameron32.apps.tapnotes._trial._demo.fragment.TestFragment;
import com.ameron32.apps.tapnotes.frmk.di.stabbed.ExtendedGraphHelper;
import com.ameron32.apps.tapnotes.impl.fragment.MainToolbarFragment;
import com.mikepenz.iconics.typeface.FontAwesome;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by klemeilleur on 4/7/2015.
 */
public class DrawerImpl {

  private final Activity activity;
  private final Toolbar toolbar;
  private final ViewGroup rootView;
  private final MainToolbarFragment.ActivityCallbacks mActivityCallbacks;
  private final Drawer.Result mDrawer;

  public DrawerImpl(final Activity activity, final Toolbar toolbar, final ViewGroup rootView,
                    final MainToolbarFragment.ActivityCallbacks mActivityCallbacks) {

    this.activity = activity;
    this.toolbar = toolbar;
    this.rootView = rootView;
    this.mActivityCallbacks = mActivityCallbacks;

    mDrawer = new Drawer()
        .withActivity(activity)
        .withTranslucentStatusBar(true)
        .withToolbar(toolbar)
        .withHeader(getHeaderView(R.layout.trial_header_only))
        .withActionBarDrawerToggle(true)
        .withDrawerWidthRes(R.dimen.navigation_drawer_width)
        .addDrawerItems(getDrawerItems())
        .withOnDrawerItemClickListener(getDrawerOnItemClickListener())
        .build();
  }

  @InjectView(R.id.imagebutton_navigation_drawer_up_arrow)
  ImageButton upButton;

  @InjectView(R.id.imagebutton_navigation_drawer_logout)
  ImageButton logoutButton;

  private View getHeaderView(@LayoutRes int inflateLayout) {
    final View view = LayoutInflater
        .from(rootView.getContext())
        .inflate(inflateLayout, rootView, false);

    ButterKnife.inject(this, view);

    tintDrawerArrow(view);
    setDrawerButtonListeners(view);
    return view;
  }

  private void tintDrawerArrow(View view) {
//    ImageButton upButton = (ImageButton) view.findViewById(R.id.imagebutton_navigation_drawer_up_arrow);
    final Drawable upArrow = activity.getResources().getDrawable(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
    upArrow.setColorFilter(activity.getResources().getColor(android.R.color.black), PorterDuff.Mode.SRC_ATOP);
    upButton.setImageDrawable(upArrow);
  }

  private void setDrawerButtonListeners(View view) {
//    ImageButton upButton = (ImageButton) view.findViewById(R.id.imagebutton_navigation_drawer_up_arrow);
    upButton.setOnClickListener(new View.OnClickListener() {

      @Override public void onClick(
          View v) {
        closeDrawer();
      }
    });

//    ImageButton logoutButton = (ImageButton) view.findViewById(R.id.imagebutton_navigation_drawer_logout);
    logoutButton.setOnClickListener(new View.OnClickListener() {

      @Override public void onClick(
          View v) {
        mActivityCallbacks.onLogoutClick();
      }
    });
  }

  private IDrawerItem[] getDrawerItems() {
    return new IDrawerItem[] {
        new PrimaryDrawerItem().withIdentifier(1).withName(TestFragment.class.getSimpleName()).withIcon(FontAwesome.Icon.faw_coffee),
        new PrimaryDrawerItem().withIdentifier(2).withName(TableTestFragment.class.getSimpleName()).withIcon(FontAwesome.Icon.faw_coffee),
//        new PrimaryDrawerItem().withIdentifier(3).withName("PhotoViewerTestFragment").withIcon(FontAwesome.Icon.faw_photo),
        new PrimaryDrawerItem().withIdentifier(4).withName(MaterialImageViewTestFragment.class.getSimpleName()).withIcon(FontAwesome.Icon.faw_image),
        new PrimaryDrawerItem().withIdentifier(7).withName(ParseTestFragment.class.getSimpleName()).withIcon(FontAwesome.Icon.faw_image),
        new PrimaryDrawerItem().withIdentifier(8).withName(ExpandableTestFragment.class.getSimpleName()).withIcon(FontAwesome.Icon.faw_image),
        new PrimaryDrawerItem().withIdentifier(9).withName(CollapsingToolbarFragment.class.getSimpleName()).withIcon(FontAwesome.Icon.faw_image),
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
            mActivityCallbacks.changeFragment(TestFragment.create());
            break;
          case 2:
            mActivityCallbacks.changeFragment(TableTestFragment.create());
            break;
          case 3:
            mActivityCallbacks.changeFragment(PhotoViewerTestFragment.create());
            break;
          case 4:
            mActivityCallbacks.changeFragment(MaterialImageViewTestFragment.create());
            break;
          case 7:
            mActivityCallbacks.changeFragment(ParseTestFragment.create());
            break;
          case 8:
            mActivityCallbacks.changeFragment(ExpandableTestFragment.create());
            break;
          case 9:
            mActivityCallbacks.changeFragment(CollapsingToolbarFragment.create());
            break;
          case 5:
            mActivityCallbacks.startSettingsActivity();
            break;
          case 6:
            mActivityCallbacks.startAbout();
            break;
          default:
            mActivityCallbacks.changeFragment(TestFragment.create());
        }
      }
    };
  }

  public boolean isDrawerOpen() {
    return mDrawer.isDrawerOpen();
  }

  public void openDrawer() {
    mDrawer.openDrawer();
  }

  private void closeDrawer() {
    mDrawer.closeDrawer();
  }
}
