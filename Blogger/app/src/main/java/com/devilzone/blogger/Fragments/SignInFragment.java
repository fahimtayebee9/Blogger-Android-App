package com.devilzone.blogger.Fragments;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.devilzone.blogger.AuthActivity;
import com.devilzone.blogger.HomeActivity;
import com.devilzone.blogger.R;
import com.devilzone.blogger.RestApiConstant.Constant;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SignInFragment extends Fragment {
    private View view;
    private TextInputLayout txtEmailLayout, txtPasswordLayout;
    private TextInputEditText txtEmialField, txtPasswordField;
    private TextView txtSignUp;
    private AppCompatButton btnSignIn;
//    private Button btnSignIn;

    private ProgressDialog dialog;

    private AlertDialog.Builder builder;

    public SignInFragment(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.layout_sign_in, container, false);
        init();
        return view;
    }

    private void init(){
        txtEmailLayout = view.findViewById(R.id.txtEmailLayout);
        txtPasswordLayout = view.findViewById(R.id.txtPasswordLayout);
        txtEmialField = view.findViewById(R.id.txtEmialField);
        txtPasswordField = view.findViewById(R.id.txtPasswordField);
        txtSignUp = view.findViewById(R.id.txtSignUp);
        btnSignIn = view.findViewById(R.id.btn_signIn);

        dialog = new ProgressDialog(getContext());
        dialog.setCancelable(false);

        txtSignUp.setOnClickListener(v->{
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameAuthContainer, new SignUpFragment()).commit();
        });

        btnSignIn.setOnClickListener(v->{
            if( validate() ){
                login();
            }
            else{
                Toast.makeText(getContext(),"ERROR", Toast.LENGTH_SHORT).show();
            }
        });

        txtEmialField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!txtEmialField.getText().toString().isEmpty()){
                    txtEmailLayout.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        txtPasswordField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(txtPasswordField.getText().toString().length() > 7){
                    txtEmailLayout.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void login() {

        StringRequest request = new StringRequest(Request.Method.POST, Constant.LOGIN, response->{
            try {
                JSONObject object = new JSONObject(response);
                if(object.getBoolean("success")){

                    JSONObject user = object.getJSONObject("user");
                    SharedPreferences userPref = getActivity().getApplicationContext().getSharedPreferences("user", getContext().MODE_PRIVATE);
                    SharedPreferences.Editor editor = userPref.edit();
                    editor.putString("token", object.getString("token"));
                    editor.putString("name", user.getString("name").toString().trim());
                    editor.putString("slug", user.getString("slug").toString().trim());
                    editor.putString("email", user.getString("email").toString().trim());
                    editor.putString("user_type", user.getString("user_type").toString().trim());
                    editor.putString("status", user.getString("status").toString().trim());
                    editor.putString("image", user.getString("image").toString().trim());
                    editor.putBoolean("isLoggedIn", true);
                    editor.apply();

                    startActivity(new Intent( (AuthActivity) getContext(), HomeActivity.class));
                    ((AuthActivity) getContext()).finish();
                    Toast.makeText(getContext(),"Login Success",Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            dialog.dismiss();
        },error -> {
            error.printStackTrace();
            dialog.dismiss();
        }){
            @NotNull
            @Override
            protected Map<String, String> getParams() throws AuthFailureError{
                HashMap<String, String> map = new HashMap<>();
                map.put("email", txtEmialField.getText().toString().trim());
                map.put("password", txtPasswordField.getText().toString().trim());
                return map;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(request);
    }

    private boolean validate(){
        if(txtEmialField.getText().toString().isEmpty()){
            txtEmailLayout.setErrorEnabled(true);
            txtEmailLayout.setError("Email is Required");
            return false;
        }
        if(txtPasswordField.getText().toString().isEmpty()){
            txtPasswordLayout.setErrorEnabled(true);
            txtPasswordLayout.setError("Password is Required");
            return false;
        }
//        if(txtPasswordField.getText().toString().length() < 8){
//            txtPasswordLayout.setErrorEnabled(true);
//            txtPasswordLayout.setError("Password must be 8 characters");
//            return false;
//        }
        return true;

    }
}
