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

package com.ameron32.apps.tapnotes._trial._demo.fragment.expandable;

/**
 * Created by klemeilleur on 5/21/2015.
 */
public abstract class AbstractExpandableDataProvider {
  public static abstract class BaseData {


    public abstract int getSwipeReactionType();


    public abstract String getText();


    public abstract void setPinnedToSwipeLeft(boolean pinned);


    public abstract boolean isPinnedToSwipeLeft();
  }


  public static abstract class GroupData extends BaseData {
    public abstract boolean isSectionHeader();
    public abstract long getGroupId();
  }


  public static abstract class ChildData extends BaseData {
    public abstract long getChildId();
  }


  public abstract int getGroupCount();
  public abstract int getChildCount(int groupPosition);


  public abstract GroupData getGroupItem(int groupPosition);
  public abstract ChildData getChildItem(int groupPosition, int childPosition);


  public abstract void moveGroupItem(int fromGroupPosition, int toGroupPosition);
  public abstract void moveChildItem(int fromGroupPosition, int fromChildPosition, int toGroupPosition, int toChildPosition);


  public abstract void removeGroupItem(int groupPosition);
  public abstract void removeChildItem(int groupPosition, int childPosition);


  public abstract long undoLastRemoval();
}
