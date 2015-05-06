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

package com.ameron32.apps.tapnotes._trial._demo.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.ameron32.apps.tapnotes.R;
import com.ameron32.apps.tapnotes.frmk.fragment.AbsContentFragment;
import com.ameron32.apps.tapnotes.impl.di.controller.ActivityAlertDialogController;
import com.ameron32.apps.tapnotes.impl.di.controller.ActivitySnackBarController;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import it.moondroid.coverflow.components.ui.containers.FeatureCoverFlow;

/**
 * Created by klemeilleur on 5/5/2015.
 */
public class MaterialViewPagerTestFragment
  extends AbsContentFragment {

  private ArrayList<Entity> mData;

  public static MaterialViewPagerTestFragment create() {
    final MaterialViewPagerTestFragment t = new MaterialViewPagerTestFragment();
    t.setArguments(new Bundle());
    return t;
  }

  @Override
  protected int getCustomLayoutResource() {
    return R.layout.trial_fragment_materialviewpager;
  }

  @Inject
  ActivitySnackBarController snackBarController;

//  @InjectView(R.id.materialViewPager)
//  MaterialViewPager mViewPager;

  @InjectView(R.id.coverflow)
  FeatureCoverFlow mCoverFlow;

  @InjectView(R.id.title)
  TextSwitcher mTitle;

  private CoverFlowAdapter mAdapter;

  @Override
  public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    ButterKnife.inject(this, view);

    initCoverFlow();
  }

  private void initCoverFlow() {
    if (mData == null) {
      mData = new ArrayList<>();
      mData.add(new Entity(R.drawable.fantasy_dwarves, R.string.title_activity_main2));
      mData.add(new Entity(R.drawable.fantasy_elves, R.string.title_activity_login));
      mData.add(new Entity(R.drawable.fantasy_humans, R.string.title_activity_settings));
      mData.add(new Entity(R.drawable.fantasy_rohan, R.string.title_activity_welcome));
      mData.add(new Entity(R.drawable.fantasy_troll, R.string.title_section1));
    }

    mTitle.setFactory(new ViewSwitcher.ViewFactory() {
      @Override
      public View makeView() {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        TextView textView = (TextView) inflater.inflate(R.layout.trial_coverflow_item_title, null);
        return textView;
      }
    });

    Animation in = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_in_right);
    Animation out = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_out_left);
    mTitle.setInAnimation(in);
    mTitle.setOutAnimation(out);

    mAdapter = new CoverFlowAdapter(getActivity());
    mAdapter.setData(mData);
    mCoverFlow.setAdapter(mAdapter);

    mCoverFlow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //TODO CoverFlow item clicked
        snackBarController.toast(getResources().getString(mData.get(position).titleResId));
      }
    });

    mCoverFlow.setOnScrollPositionListener(new FeatureCoverFlow.OnScrollPositionListener() {
      @Override
      public void onScrolledToPosition(int position) {
        //TODO CoverFlow stopped to position
        mTitle.setText(getResources().getString(mData.get(position).titleResId));
      }

      @Override
      public void onScrolling() {
        //TODO CoverFlow began scrolling
        mTitle.setText("");
      }
    });
  }

  @Inject
  ActivityAlertDialogController alertDialogController;

  @Override
  protected void onFinishInject() {
    super.onFinishInject();
    alertDialogController.showInformationDialog("Fragment Demo",
        "This fragment is incomplete." + "\n" +
            "--Really Fancy Header" + "\n"
    );
  }



  public static class CoverFlowAdapter extends BaseAdapter {

    private ArrayList<Entity> mData = new ArrayList<>(0);
    private Context mContext;

    public CoverFlowAdapter(Context context) {
      mContext = context;
    }

    public void setData(ArrayList<Entity> data) {
      mData = data;
    }

    @Override
    public int getCount() {
      return mData.size();
    }

    @Override
    public Object getItem(int pos) {
      return mData.get(pos);
    }

    @Override
    public long getItemId(int pos) {
      return pos;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

      View rowView = convertView;

      if (rowView == null) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        rowView = inflater.inflate(R.layout.trial_coverflow_item_coverflow, parent, false);
        // FIXME does it need? (layout, null)

        ViewHolder viewHolder = new ViewHolder();
        viewHolder.text = (TextView) rowView.findViewById(R.id.label);
        viewHolder.image = (ImageView) rowView
            .findViewById(R.id.image);
        rowView.setTag(viewHolder);
      }

      ViewHolder holder = (ViewHolder) rowView.getTag();

      holder.image.setImageResource(mData.get(position).imageResId);
      holder.text.setText(mData.get(position).titleResId);

      return rowView;
    }


    static class ViewHolder {
      public TextView text;
      public ImageView image;
    }
  }



  public class Entity {
    public int imageResId;
    public int titleResId;

    public Entity(int imageResId, int titleResId){
      this.imageResId = imageResId;
      this.titleResId = titleResId;
    }
  }
}
