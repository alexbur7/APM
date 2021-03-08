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

public class AddUserFragment extends Fragment {

    private EditText nameEditText;
    private EditText secondNameEditText;
    private EditText ageEditText;
    private Button addButton;
    private Button backButton;
    private AddUserCallback callback;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        callback = (AddUserCallback) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add_user,container,false);
        initView(v);
        setClickButtons();
        return v;
    }


    @Override
    public void onDetach() {
        super.onDetach();
        callback = null;
    }

    private void initView(View v){
        nameEditText = v.findViewById(R.id.name_edit_text);
        secondNameEditText = v.findViewById(R.id.secondName_edit_text);
        ageEditText = v.findViewById(R.id.age_edit_text);
        addButton = v.findViewById(R.id.add_user_button);
        backButton = v.findViewById(R.id.back_button);
    }

    private void setClickButtons() {
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nameEditText.getText().toString()!=null && secondNameEditText.getText().toString()!=null && ageEditText.getText().toString()!=null) {
                    addUserInDatabase();
                    callback.openListUser();
                }
                else {
                    Toast.makeText(getActivity(),R.string.invalid_add, Toast.LENGTH_SHORT).show();
                }
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.openListUser();
            }
        });
    }

    private void addUserInDatabase(){
        User user = new User();
        user.setId(UUID.randomUUID().toString());
        user.setName(nameEditText.getText().toString());
        user.setSecondName(secondNameEditText.getText().toString());
        user.setAge(Integer.parseInt(ageEditText.getText().toString()));
        SimpleDateFormat sdf = new SimpleDateFormat("EE MMM dd HH:mm:ss z yyyy",
                Locale.ENGLISH);
        try {
            user.setDate(sdf.parse(new Date().toString()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        RoomDBSingleton.getInstance(getContext()).getUserDao().insertUser(user);
    }

    public interface AddUserCallback{
        void openListUser();
    }
}
