package com.application.apm.View;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.application.apm.Model.Payment;
import com.application.apm.Model.RoomDBSingleton;
import com.application.apm.Model.User;
import com.application.apm.R;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class UserDetailFragment extends Fragment  {

    private User mUser;

    private TextView nameText;
    private TextView secondNameText;
    private TextView ageText;
    private TextView idText;
    private TextView dateText;

    private CallBack mCallBack;

    private TextView numberOfPaymentsText;

    private static final int CODE_TO_EDIT_USER=1;
    private static final int CODE_TO_ADD_PAYMENT=2;


    private static final String KEY_TO_NAME_IN_ARGUMENTS="key_to_name_in_arguments";
    private static final String KEY_TO_SECOND_NAME_IN_ARGUMENTS="key_to_second_name_in_arguments";
    private static final String KEY_TO_AGE_IN_ARGUMENTS="key_to_age_in_arguments";
    private static final String KEY_TO_DATE_IN_ARGUMENTS="key_to_date_in_arguments";
    private static final String KEY_TO_ID_IN_ARGUMENTS="key_to_id_in_arguments";
    private static final String KEY_TO_PRIMARY_KEY_IN_ARGS = "key_to_primary_key_in_args";

    private Button editUserButton;
    private Button viewPaymentsButton;
    private Button addPaymentButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_user_detail,container,false);
        init(v);

        return v;
    }

    private void init(View v) {
        mUser=createUserFromArgs();
        nameText=v.findViewById(R.id.name_text);
        secondNameText=v.findViewById(R.id.second_name_text);
        ageText=v.findViewById(R.id.age_text);
        idText=v.findViewById(R.id.id_text);
        numberOfPaymentsText=v.findViewById(R.id.number_of_payments_text);
        dateText=v.findViewById(R.id.date_text);

        nameText.setText(mUser.getName());
        secondNameText.setText(mUser.getSecondName());
        ageText.setText(String.valueOf(mUser.getAge()));
        idText.setText(mUser.getId());
        dateText.setText(mUser.getDate().toString());
        numberOfPaymentsText.setText(String.valueOf(RoomDBSingleton.getInstance(getActivity()).getPaymentDao().getPaymentById(mUser.getId()).size()));

        editUserButton=v.findViewById(R.id.edit_user_btn);
        viewPaymentsButton=v.findViewById(R.id.view_payments_btn);
        addPaymentButton=v.findViewById(R.id.add_payment_btn);

        editUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditUserDialog dialog=new EditUserDialog(mUser.getName(),mUser.getSecondName(),String.valueOf(mUser.getAge()));
                dialog.setTargetFragment(UserDetailFragment.this,CODE_TO_EDIT_USER);
                dialog.setCancelable(false);
                dialog.show(getFragmentManager(),null);
            }
        });
        addPaymentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddPaymentDialog dialog=new AddPaymentDialog(AddPaymentDialog.KEY_TO_PAYMENT_LAYOUT_CODE);
                dialog.setTargetFragment(UserDetailFragment.this,CODE_TO_ADD_PAYMENT);
                dialog.show(getFragmentManager(),null);
            }
        });

        viewPaymentsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallBack.onPaymentListClicked(ListPaymentFragment.USER_PAYMENT_FILTER,mUser.getId());
            }
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mCallBack= (CallBack) context;
    }

    private User createUserFromArgs() {
        User user=new User();
        user.setName(getArguments().getString(KEY_TO_NAME_IN_ARGUMENTS));
        user.setSecondName(getArguments().getString(KEY_TO_SECOND_NAME_IN_ARGUMENTS));
        user.setAge(Integer.parseInt(getArguments().getString(KEY_TO_AGE_IN_ARGUMENTS)));
        user.setId(getArguments().getString(KEY_TO_ID_IN_ARGUMENTS));
        user.set_id(getArguments().getInt(KEY_TO_PRIMARY_KEY_IN_ARGS));
        SimpleDateFormat sdf = new SimpleDateFormat("EE MMM dd HH:mm:ss z yyyy",
                Locale.ENGLISH);
        try {
            user.setDate(sdf.parse(getArguments().getString(KEY_TO_DATE_IN_ARGUMENTS)));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return user;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode== Activity.RESULT_OK){
            switch (requestCode){
                case CODE_TO_EDIT_USER:{
                    mUser.setName(data.getExtras().getString(EditUserDialog.KEY_TO_NAME));
                    mUser.setSecondName(data.getExtras().getString(EditUserDialog.KEY_TO_SECOND_NAME));
                    mUser.setAge(Integer.valueOf(data.getExtras().getString(EditUserDialog.KEY_TO_AGE)));

                    nameText.setText(mUser.getName());
                    secondNameText.setText(mUser.getSecondName());
                    ageText.setText(String.valueOf(mUser.getAge()));

                    RoomDBSingleton.getInstance(getContext()).getUserDao().updateUser(mUser);
                }break;
                case CODE_TO_ADD_PAYMENT:{
                    Payment payment=new Payment(mUser.getId(), Integer.parseInt((String) data.getExtras().get(AddPaymentDialog.KEY_TO_VALUE)));

                    RoomDBSingleton.getInstance(getContext()).getPaymentDao().insertPayment(payment);
                    numberOfPaymentsText.setText(String.valueOf(RoomDBSingleton.getInstance(getActivity()).getPaymentDao().getPaymentById(mUser.getId()).size()));
                }
            }
        }
    }

    public static UserDetailFragment getInstance(User user){
        UserDetailFragment fragment=new UserDetailFragment();
        Bundle args=new Bundle();
        args.putString(KEY_TO_NAME_IN_ARGUMENTS,user.getName());
        args.putString(KEY_TO_SECOND_NAME_IN_ARGUMENTS,user.getSecondName());
        args.putString(KEY_TO_AGE_IN_ARGUMENTS, String.valueOf(user.getAge()));
        args.putString(KEY_TO_ID_IN_ARGUMENTS,String.valueOf(user.getId()));
        args.putString(KEY_TO_DATE_IN_ARGUMENTS,user.getDate().toString());
        args.putInt(KEY_TO_PRIMARY_KEY_IN_ARGS,user.get_id());
        fragment.setArguments(args);
        return fragment;
    }

    public interface CallBack{
        void onUserClicked(User user);
        void onPaymentListClicked(int code,String id);
    }


}
