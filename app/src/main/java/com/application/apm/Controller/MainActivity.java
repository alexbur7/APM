package com.application.apm.Controller;

import android.content.Intent;

import androidx.fragment.app.Fragment;

import com.application.apm.Model.User;
import com.application.apm.View.LoginFragment;
import com.application.apm.R;
import com.application.apm.View.RegisterAdminFragment;
import com.application.apm.View.RegisterUserFragment;
import com.application.apm.View.UserDetailFragment;

public class MainActivity extends SingleFragmentActivity implements LoginFragment.CallBack, RegisterAdminFragment.CallBack, RegisterUserFragment.Callback {

    @Override
    public int getResId() {
        return R.layout.activity_single_fragment;
    }

    @Override
    public Fragment getFragment() {
        return LoginFragment.newInstance(null, null);
    }

    @Override
    public void login() {
        Intent intent = new Intent(this,ListActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void register() {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new ViewPagerFragment()).addToBackStack(null).commit();
    }

    @Override
    public void loginById(User user) {
        Intent intent=ListActivity.newInstance(this,user);
        startActivity(intent);
    }

    @Override
    public void onRegistered(String email,String pass) {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,LoginFragment.newInstance(email, pass)).commit();
    }

    @Override
    public void onUserRegistered() {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, LoginFragment.newInstance(null, null)).commit();
    }
}