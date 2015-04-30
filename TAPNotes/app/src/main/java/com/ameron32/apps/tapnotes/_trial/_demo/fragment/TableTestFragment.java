package com.ameron32.apps.tapnotes._trial._demo.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.BackgroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ameron32.apps.tapnotes.frmk.fragment.AbsContentFragment;
import com.ameron32.apps.tapnotes.R;
import com.ameron32.apps.tapnotes.impl.di.controller.ActivityAlertDialogController;
import com.ameron32.apps.tapnotes.impl.di.controller.ActivityTitleController;
import com.ameron32.apps.tapnotes.parse.adapter.SimpleTableQueryAdapter;
import com.ameron32.apps.tapnotes.parse.object.TestObject;
import com.ameron32.apps.tapnotes.util.ViewUtil;
import com.jmpergar.awesometext.AwesomeTextHandler;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;



public class TableTestFragment
  extends AbsContentFragment
{

  public static TableTestFragment create() {
    final TableTestFragment t = new TableTestFragment();
    t.setArguments(new Bundle());
    return t;
  }

  @Inject
  ActivityTitleController mTitleController;
  private SimpleTableQueryAdapter<TestObject> mAdapter;
  private LinearLayoutManager mLayoutManager;

  @Override
  protected int getCustomLayoutResource() {
    return R.layout.trial_fragment_table;
  }

  @InjectView(R.id.recyclerView)
  RecyclerView mRecyclerView;

  @InjectView(R.id.textview)
  TextView mTextView;

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    ButterKnife.inject(this, view);

    final ParseQueryAdapter.QueryFactory<TestObject> factory
        = new ParseQueryAdapter.QueryFactory<TestObject>() {
      @Override
      public ParseQuery create() {
        final ParseQuery<TestObject> query = ParseQuery.getQuery(TestObject.class);
        query.orderByDescending("createdAt");
        return query;
      }
    };
    mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
    mRecyclerView.setLayoutManager(mLayoutManager);
    mAdapter = new SimpleTableQueryAdapter<TestObject>(factory, true);
    mRecyclerView.setAdapter(mAdapter);

    applyText();
  }

  private static final String HASHTAG_PATTERN = "(#[\\p{L}0-9-_]+)";
  private static final String MENTION_PATTERN = "(@[\\p{L}0-9-_]+)";
  private static final String SCRIPTURE_PATTERN = "(@)([0-9,A-Z])\\w+(\\s)(\\d)+([\\s,:])([\\d-,])+";

  private void applyText() {
    mTextView.setText(getText());

    AwesomeTextHandler awesomeTextViewHandler = new AwesomeTextHandler();
    awesomeTextViewHandler
        .addViewSpanRenderer(HASHTAG_PATTERN, new HashtagsSpanRenderer())
        .addViewSpanRenderer(SCRIPTURE_PATTERN, new MentionSpanRenderer())
        .setView(mTextView);
  }

  private String getText() {
//    final String greenString = "I am Green.";
//    final BackgroundColorSpan backgroundColorSpan = new BackgroundColorSpan(Color.GREEN);
//    final SpannableString spannableString = new SpannableString(greenString);
//    spannableString.setSpan(backgroundColorSpan, 0, greenString.length() - 3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//    return spannableString;

    return "@John 3 16,17 as the scripture says #Jesus and #Jehovah and the #Kingdom.";
  }

  @Inject
  ActivityAlertDialogController alertDialogController;

  @Override
  protected void onFinishInject() {
    super.onFinishInject();
    alertDialogController.showInformationDialog("Fragment Demo",
        "This fragment demonstrates..." + "\n" +
            "--TableQueryAdapter of Parse Objects from server" + "\n" +
            "--Title set with Controller" + "\n" +
            "--AlertDialog usage"
    );
  }

  @Override
  public void onResume() {
    super.onResume();
    mTitleController.setTitle(TableTestFragment.class.getSimpleName());
  }

  public class HashtagsSpanRenderer implements AwesomeTextHandler.ViewSpanRenderer {

    private final static int textSizeInDips = 18;
    private final static int backgroundResource = R.drawable.common_hashtags_background;
    private final static int textColorResource = android.R.color.white;

    @Override
    public View getView(String text, Context context) {
      TextView view = new TextView(context);
      view.setText(text.substring(1));
      view.setTextSize(ViewUtil.convertDpToPx(context, textSizeInDips));
      view.setBackgroundResource(backgroundResource);
      int textColor = context.getResources().getColor(textColorResource);
      view.setTextColor(textColor);
      return view;
    }
  }

  public class MentionSpanRenderer implements AwesomeTextHandler.ViewSpanRenderer, AwesomeTextHandler.ViewSpanClickListener {

    @Override
    public View getView(String text, Context context) {
      LayoutInflater inflater = LayoutInflater.from(context);
      TextView mentionView = (TextView) inflater.inflate(R.layout.awesometext_mention, null);
      mentionView.setText(text);
      return mentionView;
    }

    @Override
    public void onClick(String text, Context context) {
      Toast.makeText(context, "Hello " + text, Toast.LENGTH_SHORT).show();
    }
  }
}
