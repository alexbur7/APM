package com.application.apm.View;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.application.apm.R;

public class EditUserDialog extends DialogFragment {

    public static final String KEY_TO_NAME="key_to_name";
    public static final String KEY_TO_SECOND_NAME="key_to_second_name";
    public static final String KEY_TO_AGE="key_to_age";

    private EditText nameEditText;
    private EditText secondNameEditText;
    private EditText ageEditText;

    private String name;
    private String secondName;
    private String age;

    public EditUserDialog(String name,String secondName,String age){
        this.name=name;
        this.secondName=secondName;
        this.age=age;
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View v= LayoutInflater.from(getContext()).inflate(R.layout.dialog_edit_user,null);
        init(v);
        AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
        return builder
                .setView(v)
                .setPositiveButton(R.string.OK, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (!age.isEmpty() && !name.isEmpty() && !secondName.isEmpty()) {
                            sendResult(name, secondName, age);
                            dismiss();
                            return;
                        }
                        Toast.makeText(getContext(),getString(R.string.empty_reg),Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dismiss();
                    }
                })
                .create();
    }

    private void sendResult(String name, String secondName, String age) {
        Intent intent=new Intent();
        intent.putExtra(KEY_TO_NAME,name);
        intent.putExtra(KEY_TO_SECOND_NAME,secondName);
        intent.putExtra(KEY_TO_AGE,age);
        getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK,intent);
    }

    private void init(View v) {
        nameEditText=v.findViewById(R.id.name_edit_text);
        secondNameEditText=v.findViewById(R.id.second_name_edit_text);
        ageEditText=v.findViewById(R.id.age_edit_text);

        nameEditText.setText(name);
        secondNameEditText.setText(secondName);
        ageEditText.setText(age);

        nameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                name=s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        secondNameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                secondName=s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        ageEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                age=s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}
