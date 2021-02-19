package com.devilzone.blogger;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.os.Bundle;

import com.devilzone.blogger.Fragments.ProfileViewFragment;
import com.devilzone.blogger.Fragments.SignInFragment;
import com.devilzone.blogger.Fragments.UpdateProfileFragment;

public class ProfileInfoActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_fragment_container);
        getSupportFragmentManager().beginTransaction().replace(R.id.frameProfileContainer, new ProfileViewFragment()).commit();
    }

}