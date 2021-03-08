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

import com.application.apm.Model.RoomDBSingleton;
import com.application.apm.Model.User;
import com.application.apm.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

public class RegisterUserFragment extends Fragment {

    private EditText mNameInput;
    private EditText mSecondNameInput;
    private EditText mAgeInput;

    private Button mRegister;

    private Callback mActivityCallback;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mActivityCallback= (Callback) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=LayoutInflater.from(getActivity()).inflate(R.layout.fragment_register_user,null);

        init(v);

        return v;
    }

    private void init(View v) {
        mNameInput=v.findViewById(R.id.name_user_edit_text);
        mSecondNameInput=v.findViewById(R.id.second_name_edit_text);
        mAgeInput=v.findViewById(R.id.age_user_edit_text);

        mRegister=v.findViewById(R.id.reg_user_button);
        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mNameInput.getText().toString().isEmpty() && !mSecondNameInput.getText().toString().isEmpty() &&
                !mAgeInput.getText().toString().isEmpty()){
                    addUserToDB();
                    Toast.makeText(getContext(),R.string.user_registered,Toast.LENGTH_SHORT).show();
                    mActivityCallback.onUserRegistered();
                }
            }
        });
    }

    private void addUserToDB(){
        User user = new User();
        user.setId(UUID.randomUUID().toString());
        user.setName(mNameInput.getText().toString());
        user.setSecondName(mSecondNameInput.getText().toString());
        user.setAge(Integer.parseInt(mAgeInput.getText().toString()));
        SimpleDateFormat sdf = new SimpleDateFormat("EE MMM dd HH:mm:ss z yyyy",
                Locale.ENGLISH);
        try {
            user.setDate(sdf.parse(new Date().toString()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        RoomDBSingleton.getInstance(getContext()).getUserDao().insertUser(user);
    }

    public interface Callback{
        void onUserRegistered();
    }
}
