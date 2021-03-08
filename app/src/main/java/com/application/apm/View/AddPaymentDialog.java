package com.application.apm.View;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.application.apm.R;

public class AddPaymentDialog extends DialogFragment {

    public static final String KEY_TO_VALUE ="key_to_payment_value";
    private static final String KEY_TO_PREVIOUS_PAYMENT_VALUE_IN_ARGS="key_to_previous_payment_value_in_args";

    public static final int KEY_TO_PAYMENT_LAYOUT_CODE=123;

    private int layoutCode;

    public AddPaymentDialog(int layoutCode){
        this.layoutCode=layoutCode;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final EditText v;
        if (layoutCode==KEY_TO_PAYMENT_LAYOUT_CODE) v= (EditText) LayoutInflater.from(getContext()).inflate(R.layout.dialog_add_payment,null);
        else v= (EditText) LayoutInflater.from(getContext()).inflate(R.layout.login_by_id_dialog,null);
        if (getArguments()!=null){
            v.setText(getArguments().getString(KEY_TO_PREVIOUS_PAYMENT_VALUE_IN_ARGS));
        }
        AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
        return builder
                .setView(v)
                .setTitle(layoutCode==KEY_TO_PAYMENT_LAYOUT_CODE ? R.string.enter_payment_value : R.string.enter_id_value)
                .setPositiveButton(R.string.OK, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (!v.getText().toString().isEmpty()) {
                            sendResult(v.getText().toString());
                            return;
                        }
                        Toast.makeText(getContext(),getString(R.string.empty_reg),Toast.LENGTH_SHORT).show();
                    }
                })
                .create();
    }

    private void sendResult(String value){
        Intent intent=new Intent();
        intent.putExtra(KEY_TO_VALUE,value);
        getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK,intent);
    }

    public static AddPaymentDialog newInstance(int paymentValue,int layoutCode){
        AddPaymentDialog dialog=new AddPaymentDialog(layoutCode);
        Bundle args=new Bundle();
        args.putString(KEY_TO_PREVIOUS_PAYMENT_VALUE_IN_ARGS,String.valueOf(paymentValue));
        dialog.setArguments(args);
        return dialog;
    }
}
