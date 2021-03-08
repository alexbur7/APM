package com.application.apm.Controller;

import android.content.Context;
import android.content.Intent;

import androidx.fragment.app.Fragment;

import com.application.apm.Model.User;
import com.application.apm.R;
import com.application.apm.View.AddUserFragment;
import com.application.apm.View.ListUserFragment;
import com.application.apm.View.UserDetailFragment;
import com.application.apm.View.ListPaymentFragment;

public class ListActivity extends SingleFragmentActivity implements ListUserFragment.UsersCallback, AddUserFragment.AddUserCallback, UserDetailFragment.CallBack, ListPaymentFragment.Callback {

    private static User client;


    @Override
    public int getResId() {
        return R.layout.activity_single_fragment;
    }

    @Override
    public Fragment getFragment() {
        if (client==null)
        return new ListUserFragment();
        else {
            User tempClient=client;
            client=null;
            return  UserDetailFragment.getInstance(tempClient);
        }
    }

    @Override
    public void addUser() {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AddUserFragment()).commit();
    }

    @Override
    public void openListUser() {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ListUserFragment()).commit();
    }

        @Override
        public void onUserClicked(User user) {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,UserDetailFragment.getInstance(user)).addToBackStack(null).commit();
    }

    @Override
    public void onPaymentListClicked(int code, String id) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, ListPaymentFragment.getInstance(code,id)).addToBackStack(null).commit();
    }

    @Override
    public void onPayValueChangePressed(int sum, int adapterNumber) {
        ListPaymentFragment fragment= (ListPaymentFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        fragment.showChangePaymentValueDialog(sum, adapterNumber);
    }

    public static Intent newInstance(Context context,User user){
        client=user;
        Intent intent=new Intent(context,ListActivity.class);
        return intent;
    }
}