package com.recluse.oclient.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.TextView;

import com.recluse.base.view.activity.BaseAppCompatActivity;
import com.recluse.base.view.fragment.BaseFragment;
import com.recluse.oclient.R;
import com.recluse.oclient.ui.fragment.EmptyListFragment;
import com.recluse.oclient.ui.fragment.HomePageListFragment;
import com.recluse.oclient.ui.fragment.SubscribePageListFragment;

import java.util.Arrays;
import java.util.List;

import butterknife.BindViews;
import butterknife.OnClick;

public class MainActivity extends BaseAppCompatActivity {

    private static final String TAG = "MainActivity";

    public static final int HOME_TAB_INDEX = 0;
    public static final int SUBSCRIBE_TAB_INDEX = 1;
    public static final int BREAK_TAB_INDEX = 2;
    public static final int ME_TAB_INDEX = 3;

    private static final List<String> TAB_FRAGMENT_TAG_LIST =
            Arrays.asList("home_tab", "subscribe_tab", "break_tab", "me_tab");

    private static final int TAB_NUM = 4;

    @BindViews({
            R.id.tab_home_layout,
            R.id.tab_subscribe_layout,
            R.id.tab_break_layout,
            R.id.tab_me_layout})
    View[] mTabViews;

    FragmentManager mFragmentManager;

    private int mCurrentTabIndex = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        updateTab(HOME_TAB_INDEX);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_main;
    }

    private void initView() {

    }

    public void updateTab(int index) {
        if (index == mCurrentTabIndex) {
            return;
        }
        updateTabView(mCurrentTabIndex, false);
        updateTabView(index, true);
        updateFragment(mCurrentTabIndex, false);
        updateFragment(index, true);
        mCurrentTabIndex = index;
    }

    private void updateTabView(int index, boolean chosen) {
        if (index < 0 || index > TAB_NUM) {
            return;
        }
        int color = chosen ?
                super.getResources().getColor(R.color.tab_text_chosen_color)
                :
                super.getResources().getColor(R.color.tab_text_normal_color);
        View tabView = mTabViews[index];
        tabView.findViewById(R.id.tab_item_image).setSelected(chosen);
        ((TextView) tabView.findViewById(R.id.tab_item_text)).setTextColor(color);
    }

    private void updateFragment(int index, boolean show) {
        if (index < 0 || index >= TAB_NUM) {
            return;
        }
        if (mFragmentManager == null) {
            mFragmentManager = super.getSupportFragmentManager();
        }
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        Fragment fragment = mFragmentManager.findFragmentByTag(TAB_FRAGMENT_TAG_LIST.get(index));
        if (show) {
            if (fragment == null) {
                fragment = createTabFragment(index);
                transaction.add(
                        R.id.main_activity_fragment_container,
                        fragment,
                        TAB_FRAGMENT_TAG_LIST.get(index));
            }
            transaction.show(fragment);
        } else {
            if (fragment != null) {
                transaction.hide(fragment);
            }
        }
        if (!transaction.isEmpty()) {
            transaction.commitAllowingStateLoss();
        }
    }

    private BaseFragment createTabFragment(int index) {
        switch (index) {
            case HOME_TAB_INDEX:
                return HomePageListFragment.newInstance(null);
            case SUBSCRIBE_TAB_INDEX:
                return SubscribePageListFragment.newInstance(null);
            default:
                return EmptyListFragment.newInstance();
        }
    }

    @OnClick({
            R.id.tab_home_layout,
            R.id.tab_subscribe_layout,
            R.id.tab_break_layout,
            R.id.tab_me_layout})
    public void onFooterBarItemClick(View view) {
        int index = mCurrentTabIndex;
        switch (view.getId()) {
            case R.id.tab_home_layout:
                index = HOME_TAB_INDEX;
                break;
            case R.id.tab_subscribe_layout:
                index = SUBSCRIBE_TAB_INDEX;
                break;
            case R.id.tab_break_layout:
                index = BREAK_TAB_INDEX;
                break;
            case R.id.tab_me_layout:
                index = ME_TAB_INDEX;
                break;
        }
        updateTab(index);
    }
}
