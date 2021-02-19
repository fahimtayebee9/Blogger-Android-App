package com.devilzone.blogger.Fragments;

import android.app.ProgressDialog;
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
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.devilzone.blogger.AuthActivity;
import com.devilzone.blogger.HomeActivity;
import com.devilzone.blogger.ProfileInfoActivity;
import com.devilzone.blogger.R;
import com.devilzone.blogger.RestApiConstant.Constant;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SignUpFragment extends Fragment {
    private View view;

    private TextInputLayout layout_name,layout_email, layout_password, layout_retypePassword, layout_phone;
    private TextInputEditText txt_name, txt_email,txt_password, txt_retypePassword, txt_phone;
    private TextView txtSignIn;
    private AppCompatButton btnSignUp;

    private ProgressDialog dialog;

    protected SignUpFragment(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.layout_sign_up, container, false);
        init();
        return view;
    }

    private void init(){
        layout_email = view.findViewById(R.id.txtEmailLayout);
        layout_password = view.findViewById(R.id.txtPasswordLayout);
        layout_name = view.findViewById(R.id.txtNameLayout);
        layout_phone = view.findViewById(R.id.txtPhoneLayout);
        layout_retypePassword = view.findViewById(R.id.txtConfirmPasswordLayout);

        txt_name = view.findViewById(R.id.txtNameField);
        txt_email = view.findViewById(R.id.txtEmialField);
        txt_password = view.findViewById(R.id.txPasswordField);
        txt_retypePassword = view.findViewById(R.id.txConfirmPasswordField);
        txt_phone = view.findViewById(R.id.txtPhoneField);

        txtSignIn = view.findViewById(R.id.txtSignIn);
        btnSignUp = view.findViewById(R.id.btn_signIn);

        dialog = new ProgressDialog(getContext());
        dialog.setCancelable(false);

        btnAction();
        txtChangeAction();

    }

    private void txtChangeAction() {
        txt_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!txt_name.getText().toString().isEmpty()){
                    layout_name.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        txt_email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!txt_email.getText().toString().isEmpty()){
                    layout_email.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        txt_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(txt_password.getText().toString().length() > 7){
                    layout_password.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        txt_retypePassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(txt_retypePassword.getText().toString().length() > 7){
                    layout_retypePassword.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        txt_phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!txt_phone.getText().toString().isEmpty()){
                    layout_phone.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    private void btnAction() {
        txtSignIn.setOnClickListener(v->{
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameAuthContainer, new SignInFragment()).commit();
        });

        btnSignUp.setOnClickListener(v->{
            if( validate() ){
                register();
            }
        });
    }

    private void register() {
        dialog.setMessage("Registering, Please Wait");
        dialog.show();
        StringRequest request = new StringRequest(Request.Method.POST, Constant.REGISTER, response -> {
            try {
                JSONObject object = new JSONObject(response);
                if(object.getBoolean("success")){
                    JSONObject user = object.getJSONObject("user");
                    SharedPreferences userPref = getActivity().getApplicationContext().getSharedPreferences("user", getContext().MODE_PRIVATE);
                    SharedPreferences.Editor editor = userPref.edit();
                    editor.putString("token", object.getString("token"));
                    editor.putString("name", user.getString("name"));
                    editor.putString("email", user.getString("email"));
                    editor.putString("phone", user.getString("phone"));
                    editor.putString("user_type", user.getString("user_type"));
                    editor.putString("status", user.getString("status"));
                    editor.putBoolean("isLoggedIn", true);
                    editor.apply();

                    Toast.makeText(getContext(),"Register Successful",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent( (AuthActivity) getContext(), ProfileInfoActivity.class));
                    ((AuthActivity) getContext()).finish();

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            dialog.dismiss();
        },error -> {
            error.printStackTrace();
            dialog.dismiss();
        }){
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put("name",txt_name.getText().toString().trim());
                map.put("email",txt_email.getText().toString().trim());
                map.put("password",txt_password.getText().toString().trim());
                map.put("phone",txt_phone.getText().toString().trim());
                return map;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(request);
    }

    private boolean validate(){
        if(txt_name.getText().toString().isEmpty()){
            layout_name.setErrorEnabled(true);
            layout_name.setError("Name is Required");
            return false;
        }
        if(txt_email.getText().toString().isEmpty()){
            layout_email.setErrorEnabled(true);
            layout_email.setError("Email is Required");
            return false;
        }
        if(txt_password.getText().toString().isEmpty()){
            layout_password.setErrorEnabled(true);
            layout_password.setError("Password is Required");
            return false;
        }
        if(txt_password.getText().toString().length() < 8){
            layout_password.setErrorEnabled(true);
            layout_password.setError("Password must be 8 characters");
            return false;
        }
        if(!txt_retypePassword.getText().toString().equals(txt_password.getText().toString())){
            layout_retypePassword.setErrorEnabled(true);
            layout_retypePassword.setError("Password must be Same");
            return false;
        }
        if(txt_phone.getText().toString().isEmpty()){
            layout_phone.setErrorEnabled(true);
            layout_phone.setError("Phone is empty");
            return false;
        }
        return true;
    }
}
