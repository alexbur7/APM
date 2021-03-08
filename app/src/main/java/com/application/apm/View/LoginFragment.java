package com.application.apm.View;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.application.apm.Model.Admin;
import com.application.apm.Model.RoomDBSingleton;
import com.application.apm.Model.User;
import com.application.apm.R;

public class LoginFragment extends Fragment {

    private static final String KEY_TO_EMAIL="email";
    private static final String KEY_TO_PASS="pass";

    private static final int CODE_TO_LOGIN_BY_ID_REQUEST=1010;


    private Button mLoginButton;
    private Button mRegButton;
    private Button mLoginByIdButton;
    private EditText mEmailInput;
    private EditText mPasswordInput;
    private CallBack mActivityCallback;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mActivityCallback= (CallBack) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=LayoutInflater.from(getContext()).inflate(R.layout.fragment_login,container,false);
        init(v);
        return v;
    }

    private void init(View v) {
        mLoginButton=v.findViewById(R.id.login_log_button);
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkLogin(mEmailInput.getText().toString(),mPasswordInput.getText().toString());
            }
        });
        mRegButton=v.findViewById(R.id.reg_log_button);
        mRegButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivityCallback.register();
            }
        });

        mLoginByIdButton=v.findViewById(R.id.login_user_button);
        mLoginByIdButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddPaymentDialog dialog=new AddPaymentDialog(0);
                dialog.setTargetFragment(LoginFragment.this,CODE_TO_LOGIN_BY_ID_REQUEST);
                dialog.show(getFragmentManager(),null);
            }
        });

        mEmailInput=v.findViewById(R.id.email_log_editText);
        mPasswordInput=v.findViewById(R.id.password_log_editText);
        String email=getArguments().getString(KEY_TO_EMAIL);
        String pass=getArguments().getString(KEY_TO_PASS);
        if (email!=null) mEmailInput.setText(email);
        if (pass!=null) mPasswordInput.setText(pass);
    }

    private void checkLogin(String login,String pass) {
        //SharedPreferences sp =getActivity().getPreferences(Context.MODE_PRIVATE);
        //String loginAtRegistration = sp.getString(RegisterAdminFragment.KEY_TO_EMAIL_IN_PREFS," ");
        //String passAtRegistration = sp.getString(RegisterAdminFragment.KEY_TO_PASS_IN_PREFS," ");
        Admin admin = RoomDBSingleton.getInstance(getContext()).getAdminDao().getAdmin(login,pass);
        Log.e("admin==null?", String.valueOf(admin==null));
        //if (login.equals(loginAtRegistration) && pass.equals(passAtRegistration)){
        if (admin!=null){
            mActivityCallback.login();
        }else
        Toast.makeText(getContext(),getString(R.string.wrong_login),Toast.LENGTH_SHORT).show();
    }


    public static Fragment newInstance(String email,String pass){
        Fragment fragment=new LoginFragment();
        Bundle args=new Bundle();
        args.putString(KEY_TO_EMAIL,email);
        args.putString(KEY_TO_PASS,pass);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode== Activity.RESULT_OK){
            if (requestCode==CODE_TO_LOGIN_BY_ID_REQUEST){
                User user= RoomDBSingleton.getInstance(getContext()).getUserDao().getUserById((String) data.getExtras().get(AddPaymentDialog.KEY_TO_VALUE));
                mActivityCallback.loginById(user);
            }
        }
    }

    public interface CallBack{
        void login();
        void register();
        void loginById(User user);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mActivityCallback=null;
    }
}
