package com.application.apm.Controller;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.application.apm.R;
import com.application.apm.View.RegisterAdminFragment;
import com.application.apm.View.RegisterUserFragment;
import com.google.android.material.tabs.TabLayout;

public class ViewPagerFragment extends Fragment {

    private ViewPager mViewPager;
    private TabLayout mTabLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=LayoutInflater.from(getContext()).inflate(R.layout.fragment_view_pager,null);
        init(v);
        return v;
    }

    private void init(View v) {
        mViewPager=v.findViewById(R.id.view_pager);
        mViewPager.setAdapter(new FragmentPagerAdapter(getChildFragmentManager(),FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

            @NonNull
            @Override
            public Fragment getItem(int position) {
                switch (position){
                    case 0:{
                        return new RegisterAdminFragment();
                    }
                    case 1:{
                        return new RegisterUserFragment();
                    }
                }
                return null;
            }

            @Override
            public int getCount() {
                return 2;
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                switch (position){
                    case 0:{
                        return getActivity().getString(R.string.admin);
                    }
                    case 1:{
                        return getActivity().getString(R.string.client);
                    }
                }
                return null;
            }
        });
        mTabLayout=v.findViewById(R.id.tab_layout);
        mTabLayout.setupWithViewPager(mViewPager);
    }

}
