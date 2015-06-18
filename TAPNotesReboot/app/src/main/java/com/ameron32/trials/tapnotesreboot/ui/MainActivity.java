package com.ameron32.trials.tapnotesreboot.ui;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;

import com.ameron32.trials.tapnotesreboot.R;
import com.ameron32.trials.tapnotesreboot.ui.EditorFragment;
import com.ameron32.trials.tapnotesreboot.ui.NotesFragment;
import com.ameron32.trials.tapnotesreboot.ui.ProgramFragment;
import com.ameron32.trials.tapnotesreboot.ui.TAPActivity;
import com.ameron32.trials.tapnotesreboot.ui.view.AnimatingPaneLayout;

import butterknife.InjectView;


public class MainActivity extends TAPActivity
    implements ProgramFragment.Callbacks, NotesFragment.TestCallbacks {

  @InjectView(R.id.drawer_layout)
  DrawerLayout mDrawerLayout;
  @InjectView(R.id.nav_view)
  NavigationView mNavigationView;
  @InjectView(R.id.pane_layout)
  AnimatingPaneLayout mAnimatingPaneLayout;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setupDrawer();
    addNotesFragment();
    addProgramFragment();
    addEditorFragment();
  }

  @Override
  protected @LayoutRes int getLayoutResource() {
    return R.layout.activity_tap;
  }

  private void setupDrawer() {
    setupDrawerContent(mNavigationView);
  }

  private void addNotesFragment() {
    // TODO consider replacing the default NOTES with explanatory fragment
    getSupportFragmentManager().beginTransaction()
        .replace(R.id.notes_container, NotesFragment.create("Blank", "", ""), "notes")
        .commit();
  }

  private void addProgramFragment() {
    getSupportFragmentManager().beginTransaction()
        .replace(R.id.program_container, ProgramFragment.create(), "program")
        .commit();
  }

  private void addEditorFragment() {
    getSupportFragmentManager().beginTransaction()
        .replace(R.id.editor_container, EditorFragment.create(), "editor")
        .commit();
  }

  private void setupDrawerContent(NavigationView navigationView) {
    navigationView.setNavigationItemSelectedListener(
        new NavigationView.OnNavigationItemSelectedListener() {
          @Override
          public boolean onNavigationItemSelected(MenuItem menuItem) {
            menuItem.setChecked(true);
            mDrawerLayout.closeDrawers();
            return true;
          }
        });
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_tap, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    switch (id) {
      case android.R.id.home:
        toggleProgramPane();
        return true;
      case R.id.action_settings:

        return true;
    }

    return super.onOptionsItemSelected(item);
  }

  private void openNavigationDrawer() {
    mDrawerLayout.openDrawer(GravityCompat.START);
  }

  private void toggleAnimatingLayout() {
    mAnimatingPaneLayout.toggleLayout();
  }

  @Override
  public void toggleProgramPane() {
    toggleAnimatingLayout();
  }

  @Override
  public void itemClicked(int position) {
    final Fragment notes = NotesFragment.create("Item " + position, "Text1: " + position, "http://i.imgur.com/ofKKBCG.jpg");
    getSupportFragmentManager().beginTransaction()
        .setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left)
        .replace(R.id.notes_container, notes)
        .addToBackStack(Integer.toString(position))
        .commit();
  }
}
