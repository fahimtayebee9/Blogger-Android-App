package com.devilzone.blogger.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import com.devilzone.blogger.HomeActivity;
import com.devilzone.blogger.R;

public class ProfileViewFragment extends Fragment {
    private AppCompatButton btn_update, btn_home;
    private TextView user_name, user_email, user_phone, user_address, user_role, user_status;
    private View view;
    private SharedPreferences userPref;

    public ProfileViewFragment(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.layout_profile_info, container, false);
        init();
        return view;
    }

    private void init(){
        userPref = getActivity().getApplicationContext().getSharedPreferences("user", Context.MODE_PRIVATE);
        user_name = view.findViewById(R.id.user_name);
        user_email = view.findViewById(R.id.user_email);
        user_phone = view.findViewById(R.id.user_phone);
        user_address = view.findViewById(R.id.user_address);
        user_role = view.findViewById(R.id.user_role);
        user_status = view.findViewById(R.id.user_status);

        user_name.setText(userPref.getString("name",""));
        user_email.setText(userPref.getString("email",""));
        user_phone.setText(userPref.getString("phone",""));
        user_address.setText(userPref.getString("address",""));
        user_role.setText(userPref.getString("role",""));
        user_status.setText(userPref.getString("status",""));

        btn_update = view.findViewById(R.id.btn_update);
        btn_home = view.findViewById(R.id.btn_home);

        btnAction();
    }

    private void btnAction(){
        btn_update.setOnClickListener(v->{
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameProfileContainer, new UpdateProfileFragment()).commit();
        });

        btn_home.setOnClickListener(v->{
            startActivity(new Intent(getActivity().getApplicationContext(), HomeActivity.class));
        });
    }
}
