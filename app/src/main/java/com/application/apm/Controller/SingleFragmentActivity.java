package com.application.apm.Controller;

import android.os.Bundle;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.application.apm.R;

public abstract class SingleFragmentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getResId());
        Fragment fragment=getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        if (fragment==null){
            fragment=getFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container,fragment).commit();
            return;
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).commit();
    }

    @LayoutRes
    public abstract int getResId();

    public abstract Fragment getFragment();
}
