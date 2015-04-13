package com.ameron32.apps.tapnotes._trial._demo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.ameron32.apps.tapnotes.frmk.fragment.AbsContentFragment;
import com.ameron32.apps.tapnotes.R;
import com.squareup.picasso.Picasso;

import butterknife.ButterKnife;
import butterknife.InjectView;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by klemeilleur on 3/3/2015.
 */
public class PhotoViewerTestFragment
    extends AbsContentFragment
{

  public static PhotoViewerTestFragment create() {
    final PhotoViewerTestFragment t = new PhotoViewerTestFragment();
    t.setArguments(new Bundle());
    return t;
  }

  @Override
  protected int getCustomLayoutResource() {
    return R.layout.trial_fragment_photo_viewer;
  }

  @InjectView(R.id.pv_photoview)
  PhotoView mPhotoView;

  private PhotoViewAttacher mAttacher;

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    ButterKnife.inject(this, view);

    // The MAGIC happens here!
    mAttacher = new PhotoViewAttacher(mPhotoView);

    Picasso.with(getActivity())
        .load("http://wallpapers.wallhaven.cc/wallpapers/full/wallhaven-70503.jpg")
        .into(mPhotoView);
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    mAttacher.cleanup();
  }
}