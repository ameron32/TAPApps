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

package app.mosn.zdepthshadowlayout;


import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import com.ameron32.apps.tapnotes.R;


public class ZDepthShadowLayoutPlus extends FrameLayout {
  public static final String TAG = "ZDepthShadowLayout";

  protected static final int DEFAULT_ATTR_SHAPE = 0;
  protected static final int DEFAULT_ATTR_ZDEPTH = 1;
  protected static final int DEFAULT_ATTR_ZDEPTH_PADDING = 5;
  protected static final int DEFAULT_ATTR_ZDEPTH_ANIM_DURATION = 150;
  protected static final boolean DEFAULT_ATTR_ZDEPTH_DO_ANIMATION = true;

  protected static final int SHAPE_RECT = 0;
  protected static final int SHAPE_OVAL = 1;

  protected ShadowView mShadowView;

  protected int mAttrShape;
  protected int mAttrZDepth;
  protected int mAttrZDepthPaddingLeft;
  protected int mAttrZDepthPaddingTop;
  protected int mAttrZDepthPaddingRight;
  protected int mAttrZDepthPaddingBottom;
  protected long mAttrZDepthAnimDuration;
  protected boolean mAttrZDepthDoAnimation;

  public ZDepthShadowLayoutPlus(Context context) {
    this(context, null);
  }

  public ZDepthShadowLayoutPlus(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public ZDepthShadowLayoutPlus(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
    init(attrs, defStyle);
  }

  protected void init(AttributeSet attrs, int defStyle) {
    setClipToPadding(false);

    final TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.ZDepthShadowLayout, defStyle, 0);
    mAttrShape = typedArray.getInt(R.styleable.ZDepthShadowLayout_z_depth_shape, DEFAULT_ATTR_SHAPE);
    mAttrZDepth = typedArray.getInt(R.styleable.ZDepthShadowLayout_z_depth, DEFAULT_ATTR_ZDEPTH);
    mAttrZDepthAnimDuration = typedArray.getInt(R.styleable.ZDepthShadowLayout_z_depth_animDuration, DEFAULT_ATTR_ZDEPTH_ANIM_DURATION);
    mAttrZDepthDoAnimation = typedArray.getBoolean(R.styleable.ZDepthShadowLayout_z_depth_doAnim, DEFAULT_ATTR_ZDEPTH_DO_ANIMATION);

    // creates padding overrides for a given side
    isLeftAttached = typedArray.getBoolean(R.styleable.ZDepthShadowLayoutPlus_attachedToLeft, DEFAULT_ATTACHED_STATE);
    isTopAttached = typedArray.getBoolean(R.styleable.ZDepthShadowLayoutPlus_attachedToTop, DEFAULT_ATTACHED_STATE);
    isRightAttached = typedArray.getBoolean(R.styleable.ZDepthShadowLayoutPlus_attachedToRight, DEFAULT_ATTACHED_STATE);
    isBottomAttached = typedArray.getBoolean(R.styleable.ZDepthShadowLayoutPlus_attachedToBottom, DEFAULT_ATTACHED_STATE);

    int attrZDepthPadding = typedArray.getInt(R.styleable.ZDepthShadowLayout_z_depth_padding, -1);
    int attrZDepthPaddingLeft = typedArray.getInt(R.styleable.ZDepthShadowLayout_z_depth_paddingLeft, -1);
    int attrZDepthPaddingTop = typedArray.getInt(R.styleable.ZDepthShadowLayout_z_depth_paddingTop, -1);
    int attrZDepthPaddingRight = typedArray.getInt(R.styleable.ZDepthShadowLayout_z_depth_paddingRight, -1);
    int attrZDepthPaddingBottom = typedArray.getInt(R.styleable.ZDepthShadowLayout_z_depth_paddingBottom, -1);

    if (attrZDepthPadding > -1) {
      // TODO choose between 0 padding and attrZDepthPadding based on isXAttached
      mAttrZDepthPaddingLeft   = attrZDepthPadding;
      mAttrZDepthPaddingTop    = attrZDepthPadding;
      mAttrZDepthPaddingRight  = attrZDepthPadding;
      mAttrZDepthPaddingBottom = attrZDepthPadding;
    } else {
      // TODO choose between 0 padding and attrZDepthPadding based on isXAttached
      mAttrZDepthPaddingLeft   = attrZDepthPaddingLeft   > -1 ? attrZDepthPaddingLeft   : DEFAULT_ATTR_ZDEPTH_PADDING;
      mAttrZDepthPaddingTop    = attrZDepthPaddingTop    > -1 ? attrZDepthPaddingTop    : DEFAULT_ATTR_ZDEPTH_PADDING;
      mAttrZDepthPaddingRight  = attrZDepthPaddingRight  > -1 ? attrZDepthPaddingRight  : DEFAULT_ATTR_ZDEPTH_PADDING;
      mAttrZDepthPaddingBottom = attrZDepthPaddingBottom > -1 ? attrZDepthPaddingBottom : DEFAULT_ATTR_ZDEPTH_PADDING;
    }

    typedArray.recycle();
  }

  @Override
  protected void onAttachedToWindow() {
    super.onAttachedToWindow();

    mShadowView = new ShadowView(getContext());
    mShadowView.setShape(mAttrShape);
    mShadowView.setZDepth(mAttrZDepth);
    mShadowView.setZDepthPaddingLeft(mAttrZDepthPaddingLeft);
    mShadowView.setZDepthPaddingTop(mAttrZDepthPaddingTop);
    mShadowView.setZDepthPaddingRight(mAttrZDepthPaddingRight);
    mShadowView.setZDepthPaddingBottom(mAttrZDepthPaddingBottom);
    mShadowView.setZDepthAnimDuration(mAttrZDepthAnimDuration);
    mShadowView.setZDepthDoAnimation(mAttrZDepthDoAnimation);
    addView(mShadowView, 0);

    int paddingLeft = mShadowView.getZDepthPaddingLeft();
    int paddingTop = mShadowView.getZDepthPaddingTop();
    int paddingRight = mShadowView.getZDepthPaddingRight();
    int paddingBottom = mShadowView.getZDepthPaddingBottom();
    setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);

//    attachToSides();
  }

  @Override
  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    final int childCount = getChildCount();

    int maxChildViewWidth = 0;
    int maxChildViewHeight = 0;

    for (int i = 0; i < childCount; i++) {
      View child = getChildAt(i);
      if (maxChildViewWidth  < child.getMeasuredWidth())  maxChildViewWidth  = child.getMeasuredWidth();
      if (maxChildViewHeight < child.getMeasuredHeight()) maxChildViewHeight = child.getMeasuredHeight();
    }

    // ???? View ???????????? padding ???? measure ?????
    int paddingLeft = mShadowView.getZDepthPaddingLeft();
    int paddingTop = mShadowView.getZDepthPaddingTop();
    int paddingRight = mShadowView.getZDepthPaddingRight();
    int paddingBottom = mShadowView.getZDepthPaddingBottom();
    maxChildViewWidth  += paddingLeft + paddingRight; // ??? padding ?????
    maxChildViewHeight += paddingTop + paddingBottom; // ??? padding ?????
    mShadowView.measure(
        MeasureSpec.makeMeasureSpec(maxChildViewWidth, MeasureSpec.EXACTLY),
        MeasureSpec.makeMeasureSpec(maxChildViewHeight, MeasureSpec.EXACTLY)
    );
  }

  @Override
  protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
    super.onLayout(changed, left, top, right, bottom);

    int width  = right - left;
    int height = bottom - top;
    mShadowView.layout(0, 0, width, height);
  }

  public int getWidthExceptShadow() {
    return getWidth() - getPaddingLeft() - getPaddingRight();
  }

  public int getHeightExceptShadow() {
    return getHeight() - getPaddingTop() - getPaddingBottom();
  }

  public void changeZDepth(ZDepth zDepth) {
    mShadowView.changeZDepth(zDepth);
  }




  @Override
  protected void onDetachedFromWindow() {
    super.onDetachedFromWindow();
    removeViewAt(0);
  }

  private static final boolean DEFAULT_ATTACHED_STATE = false;
  boolean isLeftAttached, isTopAttached, isRightAttached, isBottomAttached;

  public void setAttachedToSides(boolean left, boolean top, boolean right, boolean bottom) {
    isLeftAttached = left;
    isTopAttached = top;
    isRightAttached = right;
    isBottomAttached = bottom;
  }

  public void attachToSides() {
    final ShadowView shadowView = mShadowView;
    setPadding(isLeftAttached ? 0 : shadowView.getZDepthPaddingLeft(),
        isTopAttached ? 0 : shadowView.getZDepthPaddingTop(),
        isRightAttached ? 0 : shadowView.getZDepthPaddingRight(),
        isBottomAttached ? 0 : shadowView.getZDepthPaddingBottom());
    requestLayout();
    invalidate();
  }

  public void detachFromSides() {
    final ShadowView shadowView = mShadowView;
    setPadding(shadowView.getZDepthPaddingLeft(),
        shadowView.getZDepthPaddingTop(),
        shadowView.getZDepthPaddingRight(),
        shadowView.getZDepthPaddingBottom());
    requestLayout();
    invalidate();
  }
}
