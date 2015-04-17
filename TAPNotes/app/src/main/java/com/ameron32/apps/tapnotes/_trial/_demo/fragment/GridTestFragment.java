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

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ameron32.apps.tapnotes.R;
import com.ameron32.apps.tapnotes.frmk.fragment.AbsContentFragment;
import com.ameron32.apps.tapnotes.impl.di.controller.ActivitySnackBarController;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.lifecycle.LifecycleEvent;
import rx.schedulers.Schedulers;

/**
 * Created by klemeilleur on 4/17/2015.
 */
public class GridTestFragment extends AbsContentFragment implements Observer<_ImageResult> {

  public static GridTestFragment create() {
    final GridTestFragment t = new GridTestFragment();
    t.setArguments(new Bundle());
    return t;
  }

  @Inject
  ActivitySnackBarController snackBarController;

  @Override
  protected int getCustomLayoutResource() {
    return R.layout.trial_fragment_grid;
  }

  @InjectView(R.id.recyclerview)
  RecyclerView mRecyclerView;

  @Override
  public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    ButterKnife.inject(this, view);

    mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
//    mRecyclerView.setAdapter(new ColorfulAdapter(null));
    final Observable<_ImageResult> cache = bindLifecycle(getImageResultObservable(), LifecycleEvent.DESTROY).cache();
    addToCompositeSubscription(cache.subscribe(this));
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    unsubscribeAll();
  }

  private Observable<_ImageResult> getImageResultObservable() {
    return Observable.create(new Observable.OnSubscribe<_ImageResult>() {
      @Override
      public void call(Subscriber<? super _ImageResult> subscriber) {
        if (mResults == null) {
          mResults = new ArrayList<>();
        }
        mResults.clear();
        try {
          Thread.sleep(1000);
          final AssetManager assetManager = getActivity().getAssets();
          for (int i = 0; i < strings.length; i++) {
            final String s = strings[i];
            final Bitmap bitmap = getBitmapFromAsset(assetManager, s);
            final Palette colors = Palette.generate(bitmap);
            if (bitmap != null) {
              subscriber.onNext(new _ImageResult(i, bitmap, colors));
            }
          }
          subscriber.onCompleted();
        } catch (InterruptedException e) {
          subscriber.onError(e);
        }
      }
    }).subscribeOn(Schedulers.io());
  }

  List<_ImageResult> mResults;

  @Override
  public void onNext(_ImageResult result) {
    mResults.add(result);
  }

  @Override
  public void onError(Throwable e) {
    snackBarController.toast("Observable failed: " + e.getLocalizedMessage());
  }

  @Override
  public void onCompleted() {
    mRecyclerView.setAdapter(new ColorfulAdapter(mResults));
    snackBarController.toast("Observable complete!");
  }

  // png files excluded from source control
  private static final String[] strings = { "2015ProgramConventionOptimized_1.png",
      "2015ProgramConventionOptimized_2.png", "2015ProgramConventionOptimized_3.png" };

  private Bitmap getBitmapFromAsset(AssetManager assetManager, String pathName) {
    try {
      final InputStream is = assetManager.open(pathName);
      final Bitmap bitmap = BitmapFactory.decodeStream(is);
      return bitmap;
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }



  public static class ColorfulAdapter extends RecyclerView.Adapter<ColorfulAdapter.ViewHolder> {

    private static final int DEFAULT_IMAGE_RESOURCE = R.drawable.ic_launcher;
    private static final int DEFAULT_TEXT_COLOR = Color.BLACK;

    private final List<_ImageResult> mResults;

    public ColorfulAdapter(List<_ImageResult> results) {
      if (results == null) {
        results = new ArrayList<>();
      }
      mResults = results;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
      final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_grid_text_with_image, parent, false);
      return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
      final _ImageResult item = getItem(position);
      final Palette.Swatch mutedSwatch = item.palette.getMutedSwatch();

      holder.textView.setBackgroundColor(mutedSwatch.getRgb());
      holder.textView.setTextColor(mutedSwatch.getTitleTextColor());
      holder.textView.setText("Item @ " + position);
      holder.imageView.setImageBitmap(item.image);
    }

    private _ImageResult getItem(int position) {
      if (position < mResults.size()) {
        return mResults.get(position);
      }
      return null;
    }

    @Override
    public int getItemCount() {
      return mResults.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

      @InjectView(R.id.imageView)
      ImageView imageView;

      @InjectView(R.id.textView)
      TextView textView;

      public ViewHolder(View itemView) {
        super(itemView);
        ButterKnife.inject(this, itemView);
      }
    }
  }
}
