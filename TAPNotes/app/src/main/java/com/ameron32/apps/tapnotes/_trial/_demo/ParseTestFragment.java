package com.ameron32.apps.tapnotes._trial._demo;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.view.View;
import android.widget.TextView;

import com.ameron32.apps.tapnotes.AbsContentFragment;
import com.ameron32.apps.tapnotes.R;
import com.ameron32.apps.tapnotes.parse.object.TestObject;
import com.ameron32.apps.tapnotes.parse.rx.ParseObservable;
import com.parse.ParseQuery;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectViews;
import butterknife.OnClick;

/**
 * Created by Kris on 3/31/2015.
 */
public class ParseTestFragment extends AbsContentFragment {
  @Override
  protected @LayoutRes int getCustomLayoutResource() {
    return R.layout.fragment_parse_test;
  }

  @InjectViews({ R.id.textView1, R.id.textView2, R.id.textView3, R.id.textView4 })
  List<TextView> textViews;

  @Override
  public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    ButterKnife.inject(this, view);
  }

  @OnClick(R.id.button_start_parse_test)
  void onStartParseTest() {
    performTest();
  }

  int count;
  private void performTest() {
    count = -1;
    ParseObservable.find(ParseQuery.getQuery(TestObject.class))
        .take(4)
        .doOnNext(testObject -> count++)
        .subscribe(testObject -> textViews.get(count).setText(testObject.getString("key")));
  }
}
