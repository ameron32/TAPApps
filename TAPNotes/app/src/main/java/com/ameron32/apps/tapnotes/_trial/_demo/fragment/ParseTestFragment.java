package com.ameron32.apps.tapnotes._trial._demo.fragment;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.view.View;
import android.widget.TextView;

import com.ameron32.apps.tapnotes.frmk.fragment.AbsContentFragment;
import com.ameron32.apps.tapnotes.R;
import com.ameron32.apps.tapnotes.impl.di.controller.ActivityAlertDialogController;
import com.ameron32.apps.tapnotes.impl.di.controller.ActivitySnackBarController;
import com.ameron32.apps.tapnotes.parse.object.TestObject;
import com.ameron32.apps.tapnotes.parse.rx.ParseObservable;
import com.parse.ParseQuery;

import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectViews;
import butterknife.OnClick;
import icepick.Icepick;
import icepick.Icicle;

/**
 * Created by Kris on 3/31/2015.
 */
public class ParseTestFragment extends AbsContentFragment {

  public static ParseTestFragment create() {
    final ParseTestFragment t = new ParseTestFragment();
    t.setArguments(new Bundle());
    return t;
  }

  @Inject
  ActivitySnackBarController snackBarController;

  @Override
  protected @LayoutRes int getCustomLayoutResource() {
    return R.layout.trial_fragment_parse_test;
  }

  @InjectViews({ R.id.textView1, R.id.textView2, R.id.textView3, R.id.textView4 })
  List<TextView> textViews;

  @Override
  public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    ButterKnife.inject(this, view);
  }
  @Inject
  ActivityAlertDialogController alertDialogController;

  @Override
  protected void onFinishInject() {
    super.onFinishInject();
    alertDialogController.showInformationDialog("Fragment Demo",
        "This fragment demonstrates..." + "\n" +
            "--Object query from server using Parse" + "\n" +
            "--Parse query via RxJava by ParseObservable" + "\n" +
            "--Save text state with IcePick" + "\n" +
            "--Custom Fonts with Calligraphy" + "\n" +
            "--SnackBar usage" + "\n" +
            "--AlertDialog usage" + "\n" +
            "--Code contains lambdas"
    );
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    ButterKnife.reset(this);
  }

  @OnClick(R.id.button_start_parse_test)
  void onStartParseTest() {
    performTest();
  }

  int count;
  private void performTest() {
//    getBus().post("Event start.");
    count = -1;
    ParseObservable.find(ParseQuery.getQuery(TestObject.class).orderByDescending("updatedAt"))
        .take(textViews.size())
        .doOnNext(testObject -> count++)
        .subscribe(testObject -> textViews.get(count).setText(testObject.getString("key")));
  }

//  private static final String TEXTVIEWS_PARCEL_KEY = "TextViews Parcelable ArrayList Key";
  @Icicle
  String text1;
  @Icicle
  String text2;
  @Icicle
  String text3;
  @Icicle
  String text4;

  @Override
  protected void onSaveState(Bundle outState) {
    super.onSaveState(outState);

    text1 = textViews.get(0).getText().toString();
    text2 = textViews.get(1).getText().toString();
    text3 = textViews.get(2).getText().toString();
    text4 = textViews.get(3).getText().toString();

    Icepick.saveInstanceState(this, outState);
//
//    ArrayList<Parcelable> textViewContents = new ArrayList<>();
//    for (int i = 0; i < textViews.size(); i++) {
//      TextView t = textViews.get(i);
//      Parcelable parcelable = t.onSaveInstanceState();
//      textViewContents.add(parcelable);
//    }
//    outState.putParcelableArrayList(TEXTVIEWS_PARCEL_KEY, textViewContents);
  }

  @Override
  protected void onRestoreState(Bundle savedInstanceState) {
    super.onRestoreState(savedInstanceState);
    Icepick.restoreInstanceState(this, savedInstanceState);

    textViews.get(0).setText(text1);
    textViews.get(1).setText(text2);
    textViews.get(2).setText(text3);
    textViews.get(3).setText(text4);
//
//    ArrayList<Parcelable> textViewContents = savedInstanceState.getParcelableArrayList(TEXTVIEWS_PARCEL_KEY);
//    for (int i = 0; i < textViews.size(); i++) {
//      TextView t = textViews.get(i);
//      Parcelable textViewContent = textViewContents.get(i);
//      t.onRestoreInstanceState(textViewContent);
//    }
  }

//  @Subscribe
//  public void hearMessage(String message) {
//    snackBarController.toast("Otto message: " + message);
//  }
}
