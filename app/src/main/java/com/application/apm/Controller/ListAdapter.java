package com.application.apm.Controller;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.application.apm.Model.ModelAble;
import com.application.apm.Model.Payment;
import com.application.apm.Model.User;
import com.application.apm.R;
import com.application.apm.View.ListPaymentFragment;
import com.application.apm.View.UserDetailFragment;
import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListHolder> {

    private List<ModelAble> mAdapterList;
    private int typeHolder;
    private Context context;

    public ListAdapter(List<ModelAble> mAdapterList, int typeHolder, Context context){
        this.mAdapterList=mAdapterList;
        this.typeHolder= typeHolder;
        this.context = context;
    }

    public List<ModelAble> getAdapterList() {
        return mAdapterList;
    }


    @NonNull
    @Override
    public ListAdapter.ListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (typeHolder== UsersList.USER_LIST_HOLDER_TYPE) return new UsersList(LayoutInflater.from(context).inflate(R.layout.item_user_in_list,parent,false));
        else if (typeHolder== PaymentList.PAYMENT_LIST_HOLDER_TYPE) return new PaymentList(LayoutInflater.from(context).inflate(R.layout.item_payment,parent,false));
        return null;
    }



    @Override
    public void onBindViewHolder(@NonNull ListAdapter.ListHolder holder, int position) {
        holder.bind(mAdapterList.get(position));
    }

    @Override
    public int getItemCount() {
        return mAdapterList.size();
    }

    abstract static class ListHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        protected ModelAble model;

        public ListHolder(@NonNull View itemView) {
            super(itemView);
            initView(itemView);
        }

        protected abstract void bind(ModelAble model);

        protected abstract void initView(View v);

        protected abstract void setInformationInView(ModelAble model);
    }

    public class UsersList extends ListHolder{
        public static final int USER_LIST_HOLDER_TYPE = 1;
        private TextView nameText;
        private TextView secondNameText;
        private TextView ageText;
        private TextView dateRegistrationText;
        private UserDetailFragment.CallBack mCallBack;

        public UsersList(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void bind(ModelAble model) {
            this.model=model;
            this.mCallBack= (UserDetailFragment.CallBack) context;
            setInformationInView(model);
        }

        @Override
        protected void initView(View v) {
            nameText = itemView.findViewById(R.id.name_user);
            secondNameText = itemView.findViewById(R.id.secondName_user);
            ageText = itemView.findViewById(R.id.age_user);
            dateRegistrationText = itemView.findViewById(R.id.date_user);
        }

        @SuppressLint("SetTextI18n")
        @Override
        protected void setInformationInView(ModelAble model) {
            User user = (User) model;
            nameText.setText(user.getName());
            secondNameText.setText(user.getSecondName());
            ageText.setText(String.valueOf(user.getAge()));
            String dateReg = context.getString(R.string.reg_date_info);
            dateRegistrationText.setText(dateReg+user.getDate());
        }


        @Override
        public void onClick(View v) {
            mCallBack.onUserClicked((User) model);
        }
    }

    public class PaymentList extends ListHolder{
        public static final int PAYMENT_LIST_HOLDER_TYPE = 2;
        private TextView sumTextView;
        private TextView dateTextView;
        private ListPaymentFragment.Callback mCallback;

        public PaymentList(@NonNull View itemView) {

            super(itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        protected void bind(ModelAble model) {
            this.model=model;
            setInformationInView(model);
        }

        @Override
        protected void initView(View v) {
            sumTextView = v.findViewById(R.id.sum_payment);
            dateTextView = v.findViewById(R.id.date_payment);
            mCallback= (ListPaymentFragment.Callback) context;
        }

        @Override
        protected void setInformationInView(ModelAble model) {
            Payment payment = (Payment) model;
            sumTextView.setText(String.valueOf(payment.getSum()));
            dateTextView.setText(payment.getDate().toString());
        }

        @Override
        public void onClick(View v) {
            mCallback.onPayValueChangePressed(((Payment)model).getSum(),getAdapterPosition());
        }
    }
}
