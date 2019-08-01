package com.sense.naoto.sense.view_helper;

import android.support.design.internal.BaselineLayout;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;

public class BottomNavigationViewHelper {

    public static void adjustNavigation(BottomNavigationView navigation){
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) navigation.getChildAt(0);

        for (int i = 0; i < menuView.getChildCount(); i++) {

            //テキストの無効化
            final View smallLabel = menuView.getChildAt(i).findViewById(android.support.design.R.id.smallLabel);
            BaselineLayout baselineLayout = (BaselineLayout) smallLabel.getParent();
            baselineLayout.setVisibility(View.GONE);

            //iconのサイズの調整
            final View iconView = menuView.getChildAt(i).findViewById(android.support.design.R.id.icon);
            final ViewGroup.LayoutParams layoutParams = iconView.getLayoutParams();
            final DisplayMetrics displayMetrics = navigation.getResources().getDisplayMetrics();
            layoutParams.height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 36, displayMetrics);
            layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 36, displayMetrics);
            iconView.setLayoutParams(layoutParams);

        }
    }
}
