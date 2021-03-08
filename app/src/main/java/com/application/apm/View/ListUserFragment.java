package com.application.apm.View;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.application.apm.Controller.ListAdapter;
import com.application.apm.Controller.MainActivity;
import com.application.apm.Model.ModelAble;
import com.application.apm.Model.RoomDBSingleton;
import com.application.apm.Model.User;
import com.application.apm.R;

import java.util.ArrayList;
import java.util.List;

public class ListUserFragment extends Fragment{

    private RecyclerView listUser;
    private List<User> users;
    private UsersCallback usersCallback;
    private ListAdapter listAdapter;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        usersCallback = (UsersCallback) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_list_user, container, false);
        users = new ArrayList<>();
        users = RoomDBSingleton.getInstance(getContext()).getUserDao().getUsers();
        List<ModelAble> modelAbles = new ArrayList<>();
        modelAbles.addAll(users);
        listUser = v.findViewById(R.id.list_user);
        listUser.setLayoutManager(new LinearLayoutManager(getActivity()));
        listAdapter = new ListAdapter(modelAbles, ListAdapter.UsersList.USER_LIST_HOLDER_TYPE, getActivity());
        listUser.setAdapter(listAdapter);
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        listAdapter.notifyDataSetChanged();
        listUser.setAdapter(listAdapter);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        usersCallback = null;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.user_list_menu,menu);

        MenuItem searchItem = menu.findItem(R.id.menu_search_item);
        final SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                updateItems(query);
                searchView.onActionViewCollapsed();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.add_user_item:{
                usersCallback.addUser();
            }
            break;
            case R.id.menu_cancel_item:{
                updateItems(null);
            }
            break;
            case R.id.exit_account:{
                Intent intent=new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
            break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void updateItems(String query){
        users.clear();
        if (query!=null) {
            users = RoomDBSingleton.getInstance(getContext()).getUserDao().getUserBySecondName(query);
        }
        else {
            users = RoomDBSingleton.getInstance(getContext()).getUserDao().getUsers();
        }
        List<ModelAble> modelAbles = new ArrayList<>();
        modelAbles.addAll(users);
        listAdapter = new ListAdapter(modelAbles, ListAdapter.UsersList.USER_LIST_HOLDER_TYPE, getActivity());
        listUser.setAdapter(listAdapter);
    }


    public interface UsersCallback{
        void addUser();
    }
}
