package com.application.apm.View;

import android.content.Context;
import android.os.Bundle;
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
import com.application.apm.R;

public class RegisterAdminFragment extends Fragment {

    private Button mRegButton;
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
        View v=LayoutInflater.from(getActivity()).inflate(R.layout.fragment_register_admin,container,false);
        init(v);
        return v;
    }

    private void init(View v) {
        mRegButton=v.findViewById(R.id.reg_reg_button);
        mEmailInput=v.findViewById(R.id.email_reg_editText);
        mPasswordInput=v.findViewById(R.id.password_reg_editText);
        mRegButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });
    }

    private void register() {
        if (mPasswordInput.getText().length()<10){
            Toast.makeText(getContext(), R.string.password_short,Toast.LENGTH_SHORT).show();
            return;
        }
        else if (!mEmailInput.getText().toString().isEmpty() && !mPasswordInput.getText().toString().isEmpty()){
            Admin admin = new Admin(mEmailInput.getText().toString(),mPasswordInput.getText().toString());
            RoomDBSingleton.getInstance(getContext()).getAdminDao().insertAdmin(admin);
            Toast.makeText(getContext(),getString(R.string.reg_completed),Toast.LENGTH_SHORT).show();
            mActivityCallback.onRegistered(mEmailInput.getText().toString(),mPasswordInput.getText().toString());
            return;
        }
        Toast.makeText(getContext(),getString(R.string.empty_reg),Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mActivityCallback=null;
    }

    public interface CallBack{
        void onRegistered(String email,String pass);
    }
}
