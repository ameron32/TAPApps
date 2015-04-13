package com.ameron32.apps.tapnotes._unused;

/*********************************************************************
 * *******************************************************************
 * *******************************************************************
 *
 * WARNING: OBJECT NOT NECESSARY AT THE MOMENT
 *
 * *******************************************************************
 * *******************************************************************
 * *******************************************************************
 */

import android.support.v4.app.Fragment;

import com.ameron32.apps.tapnotes.frmk.fragment.AbsContentFragment;
import com.ameron32.apps.tapnotes.R;
import com.ameron32.apps.tapnotes._trial._demo.fragment.MaterialImageViewTestFragment;
import com.ameron32.apps.tapnotes._trial._demo.fragment.TestFragment;

import java.util.ArrayList;
import java.util.List;

public class ContentManager extends AbsManager implements FragmentProvider {

  private static ContentManager contentManager;

  public static ContentManager get() {
    if (contentManager == null) {
      contentManager = new ContentManager();
      contentManager.initialize();
    }
    return contentManager;
  }


  public static void destroy() {
    contentManager = null;
  }

  private List<ContentItem> contentItems;

  private ContentManager() {
    listeners = new ArrayList<OnContentChangeListener>();
    listeners2 = new ArrayList<>();
    contentItems = createContentItems();
  }

  public void initialize() {
    setInitialized(true);
  }

  private List<ContentItem> createContentItems() {
    final List<ContentItem> items = new ArrayList<ContentItem>();

    items.add(new ContentItem("Item 1", R.drawable.ic_construction, new TestFragment()));
    items.add(new ContentItem("Item 2", R.drawable.ic_construction, new TestFragment()));
    items.add(new ContentItem("Item 3", R.drawable.ic_construction, new TestFragment()));
    items.add(new ContentItem("Item 4", R.drawable.ic_construction, new TestFragment()));
    items.add(new ContentItem("Item 5: MaterialImageView",
        R.drawable.ic_construction, new MaterialImageViewTestFragment()));

//    items.add(new ContentItem("Test:Game", R.drawable.ic_construction,
//        GameFragment.newInstance(R.layout.section_game)));
//
//
////  TODO: instantiate GridPagerFragment correctly
////    items.add(new ContentItem("GridTest", R.drawable.ic_construction,
////        GridPagerFragment.newInstance(3)));
//
//    items.add(new ContentItem("Test:Stats", R.drawable.ic_construction,
//        SectionContainerTestFragment.newInstance(StatsTestFragment.class, R.layout.section_character_stats)));
////    items.add(new ContentItem("Test:Equipment2", R.drawable.ic_construction,
////        SectionContainerTestFragment.newInstance(EquipmentHeadersTestFragment.class, R.layout.section_character_equipment_headers)));
//    items.add(new ContentItem("Test:Equipment3", R.drawable.ic_construction,
//        SectionContainerTestFragment.newInstance(EquipmentRecyclerTestFragment.class, R.layout.section_character_equipment_3)));
////    items.add(new ContentItem("Test:Inventory2", R.drawable.ic_construction,
////        SectionContainerTestFragment.newInstance(InventoryHeadersTestFragment.class, R.layout.section_character_inventory_headers)));
//    items.add(new ContentItem("Test:Inventory3", R.drawable.ic_construction,
//        SectionContainerTestFragment.newInstance(InventoryRecyclerTestFragment.class, R.layout.section_character_inventory_3)));
//
//
////  TODO: reenable later
////    items.add(new ContentItem("Test:Characters", R.drawable.ic_construction,
////        TableTestFragment.create("Character", R.layout.fragment_default_table_layout)));
//
//    if (GameManager.get().isCurrentUserGM()) {
//
//      items.add(new ContentItem("RxJava DEMO", R.drawable.ic_gm, new DEMORxJavaFragment()));
//      items.add(new ContentItem("SlidingPaneTest", R.drawable.ic_gm, new PartialPaneFragment()));
//      items.add(new ContentItem("HtmlTextView DEMO", R.drawable.ic_gm, new HtmlTextViewTestFragment()));
//
//
////      items.add(new ContentItem("GM:Demo:blank", R.drawable.ic_gm,
////          AbsContentFragment.newInstance(/*1*/)));
//      items.add(new ContentItem("GM:Chat", R.drawable.ic_gm,
//          ChatManagerFragment.newInstance(0, null)));
//      items.add(new ContentItem("GM:RETIRED:Equipment", R.drawable.ic_gm,
//          SectionContainerTestFragment.newInstance(EquipmentTestFragment.class, R.layout.section_character_equipment)));
//      items.add(new ContentItem("GM:RETIRED:Inventory", R.drawable.ic_gm,
//          SectionContainerTestFragment.newInstance(InventoryTestFragment.class, R.layout.section_character_inventory)));
//
//      items.add(new ContentItem("GM:Test:Advantages2", R.drawable.ic_gm,
//          new AdvantageCheckerFragment()));
//      items.add(new ContentItem("GM:Test:Skills2", R.drawable.ic_gm,
//          new SkillCheckerFragment()));
//
//      items.add(new ContentItem("GM:Test:Skills", R.drawable.ic_gm,
//          SectionContainerTestFragment.newInstance(SkillsTestFragment.class, R.layout.section_skills)));
//      items.add(new ContentItem("GM:Test:Table Character", R.drawable.ic_gm,
//          TableTestFragment.create("Character", R.layout.section_)));
//      items.add(new ContentItem("GM:Test:Table CInventory", R.drawable.ic_gm,
//          TableTestFragment.create("CInventory", R.layout.section_)));
//      items.add(new ContentItem("GM:Test:Table Item", R.drawable.ic_gm,
//          TableTestFragment.create("Item", R.layout.section_)));
//
//      items.add(new ContentItem("GM:Test:TileView", R.drawable.ic_gm,
//          MyTileViewFragment.newInstance()));
//
//
//
//      items.add(new ContentItem("GM: Add Players", R.drawable.ic_gm, new AddPlayersFragment()));
//      items.add(new ContentItem("GM: Add Characters", R.drawable.ic_gm, new AddCharactersFragment()));
//      items.add(new ContentItem("GM: Create Item", R.drawable.ic_gm, new CreateItemFragment()));
//      items.add(new ContentItem("GM: Issue Item", R.drawable.ic_gm, new IssueItemFragment()));
//      items.add(new ContentItem("GM: Roll Dice", R.drawable.ic_gm, new RollDiceFragment()));
//
//      items.add(new ContentItem("Database: Create Item Set", R.drawable.ic_gm, new CreateSetItemsFragment()));
//      items.add(new ContentItem("Database: Attach Relation", R.drawable.ic_gm, new RelationAttacherFragment()));
//    }

    return items;
  }

  public List<OnContentChangeListener> listeners;
  public List<OnFragmentChangeListener> listeners2;

  public boolean addOnContentChangeListener(
      OnContentChangeListener listener) {
    return listeners.add(listener);
  }

  public boolean removeOnContentChangeListener(
      OnContentChangeListener listener) {
    return listeners.remove(listener);
  }

  @Override
  public void addFragmentChangeListener(OnFragmentChangeListener listener) {
    listeners2.add(listener);
  }

  @Override
  public void removeFragmentChangeListener(OnFragmentChangeListener listener) {
    listeners2.remove(listener);
  }

  private void notifyListenersOfContentChange(int position) {
    for (OnContentChangeListener listener : listeners) {
      listener.onContentChange(this, position);
    }
  }

  private void notifyListenersOfFragmentChange(int position) {
    for (OnFragmentChangeListener listener : listeners2) {
      listener.onFragmentChange(position);
    }
  }

  public interface OnContentChangeListener {
    public void onContentChange(
        ContentManager manager,
        int position);
  }

  private int mCurrentSelectedFragment = 0;

  public void setCurrentSelectedFragmentPosition(
      int position) {
    mCurrentSelectedFragment = position;
//    notifyListenersOfContentChange(position);
    notifyListenersOfFragmentChange(position);
  }

  public void setSelectedFragment(int position) {
    setCurrentSelectedFragmentPosition(position);
  }

  public int getCurrentSelectedFragment() {
    return mCurrentSelectedFragment;
  }

  public String getTitleForPosition(
      int position) {
    return contentItems.get(position).title;
  }

  public AbsContentFragment getNewFragmentForPosition(
      int position) {
    return contentItems.get(position).fragment;
  }

  public Fragment getFragment(int position) {
    return getNewFragmentForPosition(position);
  }

  public String[] getTitles() {
    String[] titles = new String[contentItems.size()];
    for (int i = 0; i < titles.length; i++) {
      titles[i] = contentItems.get(i).title;
    }
    return titles;
  }

  public int[] getImageIcons() {
    int[] icons = new int[contentItems.size()];
    for (int i = 0; i < icons.length; i++) {
      icons[i] = contentItems.get(i).imageResource;
    }
    return icons;
  }

  public static class ContentItem {
    public String title;
    public int imageResource;
    AbsContentFragment fragment;

    public ContentItem(String title,
                       int imageResource,
                       AbsContentFragment fragment) {
      super();
      this.title = title;
      this.imageResource = imageResource;
      this.fragment = fragment;
    }
  }

  public ContentAdapter getAdapter() {
    return new ContentAdapter(contentItems);
  }
}

