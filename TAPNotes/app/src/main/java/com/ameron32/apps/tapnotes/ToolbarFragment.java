package com.ameron32.apps.tapnotes;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ToolbarFragment
    extends Fragment
{

  private Toolbar mToolbar;
  private RecyclerView.Adapter mAdapter;
  private RecyclerView mCharacterRecyclerView;
  private OnToolbarFragmentCallbacks mCallbacks;

  public static ToolbarFragment newInstance() {
    ToolbarFragment f = new ToolbarFragment();
    return f;
  }

  @Override public View onCreateView(
      LayoutInflater inflater,
      ViewGroup container,
      Bundle savedInstanceState) {
    final Context context = getActivity();
    View v = inflater.inflate(R.layout.fragment_toolbar_default, container, false);
    mToolbar = (Toolbar) v.findViewById(R.id.toolbar_actionbar);

    // implement custom slider
//    loadCustomViews();

    return v;
  }

  @Override public void onViewCreated(
      View view,
      Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    mCallbacks.onToolbarCreated(mToolbar);
    init();
  }

//  private void loadCustomViews() {
//    final Context context = getActivity();
//    View layout = LayoutInflater.from(context).inflate(R.layout.view_toolbar_character_recyclerview, mToolbar, false);
//    mCharacterRecyclerView = (RecyclerView) layout.findViewById(R.id.recyclerview);
//    mToolbar.addView(mCharacterRecyclerView);
//  }

  private void init() {
    // load custom slider
//    addCharacterIcons_v2(mToolbar);
  }

//  private void addCharacterIcons_v2(
//      Toolbar toolbar) {
//    final Context context = getActivity();
//    mCharacterRecyclerView.setHasFixedSize(true);
//
//    LinearLayoutManager mLayoutManager = new LinearLayoutManager(context);
//    mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
//    mCharacterRecyclerView.setLayoutManager(mLayoutManager);
//    // mRecyclerView.setItemAnimator(new DefaultItemAnimator());
//
//    mAdapter = createAdapter();
//    mCharacterRecyclerView.setAdapter(mAdapter);
//    mCharacterRecyclerView.addOnItemTouchListener(new CharacterClickListener(context, new OnCharacterClickListener() {
//
//      @Override public void onCharacterClick(
//          View view, int position) {
//        mAdapter.setSelection(position);
//      }
//    }));
//  }

//  private void createAdapter() {
//    final Context context = getActivity();
//    return new CharacterSelectorAdapter(context);
//  }

  @Override public void onAttach(
      Activity activity) {
    super.onAttach(activity);
    if (activity instanceof OnToolbarFragmentCallbacks) {
      this.mCallbacks = (OnToolbarFragmentCallbacks) activity;
    } else {
      throw new IllegalStateException("activity must implement OnToolbarFragmentCallbacks");
    }
  }

  @Override public void onDetach() {
    mCallbacks = null;
    super.onDetach();
  }

  public interface OnToolbarFragmentCallbacks {
    public void onToolbarCreated(Toolbar toolbar);
  }

  @Override public void onResume() {
    super.onResume();
//    CharacterManager.get().addOnCharacterChangeListener(this);
  }

  @Override public void onPause() {
    super.onPause();
//    CharacterManager.get().removeOnCharacterChangeListener(this);
  }

//  @Override public void onCharacterChange(
//      CharacterManager manager,
//      com.ameron32.apps.projectbanditv3.object.Character newCharacter) {
//    // TODO When Characters change, Toolbar doesn't know
//
//  }
//
//  @Override public void onChatCharacterChange(
//      CharacterManager manager,
//      Character newCharacter) {
//    // TODO Auto-generated method stub
//
//  }
}

