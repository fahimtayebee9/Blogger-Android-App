package com.devilzone.blogger.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.devilzone.blogger.ProfileInfoActivity;
import com.devilzone.blogger.R;
import com.devilzone.blogger.RestApiConstant.Constant;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;

public class UpdateProfileFragment extends Fragment {

    private View view;
    private TextInputLayout layout_name,layout_email, layout_password, layout_retypePassword, layout_phone, layout_address;
    private TextInputEditText txt_name, txt_email,txt_password, txt_retypePassword, txt_phone, txt_address;
    private TextView upload_img;
    private AppCompatButton btn_saveChanges;
    private CircleImageView circleImageView;
    private static final int GALLERY_ADD_PROFILE = 1;
    private SharedPreferences userPref;
    private ProgressDialog dialog;
    private Bitmap bitmap = null;

    public UpdateProfileFragment(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.layout_update_profile, container, false);
        init();
        return view;
    }

    private void init(){
        userPref = getActivity().getApplicationContext().getSharedPreferences("user", Context.MODE_PRIVATE);
        setUserData(userPref);
        layout_email = view.findViewById(R.id.layout_email);
        layout_password = view.findViewById(R.id.layout_password);
        layout_name = view.findViewById(R.id.layout_name);
        layout_phone = view.findViewById(R.id.layout_phone);
        layout_retypePassword = view.findViewById(R.id.layout_confirmPass);
        layout_address = view.findViewById(R.id.layout_address);

        txt_name = view.findViewById(R.id.txt_name);
        txt_email = view.findViewById(R.id.txt_email);
        txt_password = view.findViewById(R.id.txt_password);
        txt_retypePassword = view.findViewById(R.id.txt_confirmPass);
        txt_phone = view.findViewById(R.id.txt_phone);
        txt_address = view.findViewById(R.id.txt_address);
        circleImageView = view.findViewById(R.id.user_image);

        upload_img = view.findViewById(R.id.upload_img);
        btn_saveChanges = view.findViewById(R.id.btn_saveChanges);

        dialog = new ProgressDialog(getContext());
        dialog.setCancelable(false);

        btnAction();
        txtChangeAction();

    }

    private void setUserData(SharedPreferences userPref) {
        txt_name.setText(userPref.getString("name", ""));
        txt_email.setText(userPref.getString("email", ""));
        txt_phone.setText(userPref.getString("phone", ""));
        txt_address.setText(userPref.getString("address", ""));
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

        txt_address.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!txt_address.getText().toString().isEmpty()){
                    layout_address.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void btnAction() {
        upload_img.setOnClickListener(v->{
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("images/*");
            startActivityForResult(intent,GALLERY_ADD_PROFILE);
        });

        btn_saveChanges.setOnClickListener(v->{
            if( validate() ){
                updateInfo();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==GALLERY_ADD_PROFILE && resultCode == RESULT_OK){
            Uri imgUri = data.getData();
            circleImageView.setImageURI(imgUri);

            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), imgUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void updateInfo() {
        String name = txt_name.getText().toString().trim();
        String email = txt_email.getText().toString().trim();
        String password = txt_password.getText().toString().trim();
        String phone = txt_phone.getText().toString().trim();
        String address = txt_address.getText().toString().trim();
        String imgName = "";

        dialog.setMessage("Saving Changes, Please Wait...");
        dialog.show();
        StringRequest request = new StringRequest(Request.Method.POST, Constant.UPDATE_USER, response -> {
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
                    editor.putString("address", user.getString("address"));
                    editor.putString("image", user.getString("image"));
                    editor.putString("user_type", user.getString("user_type"));
                    editor.putString("status", user.getString("status"));
                    editor.putBoolean("isLoggedIn", true);
                    editor.apply();

                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameProfileContainer, new ProfileViewFragment()).commit();
                    Toast.makeText(getContext(),"Register Successful",Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            dialog.dismiss();
        },error -> {
            error.printStackTrace();
            dialog.dismiss();
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                String token = userPref.getString("token","");
                HashMap<String, String> map = new HashMap<>();
                map.put("Authorization", "Bearer "+ token);
                return map;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put("name", name);
                if(!password.isEmpty()){
                    map.put("email", email);
                }
                if(!password.isEmpty()){
                    map.put("password", password);
                }
                map.put("phone", phone);
                map.put("address", address);
                map.put("image", bitmapToString(bitmap));
                return map;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(request);
    }

    private String bitmapToString(Bitmap bitmap){
        if (bitmap!=null){
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            byte[] array = byteArrayOutputStream.toByteArray();
            return Base64.encodeToString(array, Base64.DEFAULT);
        }
        return "";
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
        if(txt_password.getText().toString().isEmpty() && !txt_retypePassword.getText().toString().equals(txt_password.getText().toString())){
            layout_retypePassword.setErrorEnabled(true);
            layout_retypePassword.setError("Password must be Same");
            return false;
        }
        if(txt_password.getText().toString().length() < 8){
            layout_password.setErrorEnabled(true);
            layout_password.setError("Password must be 8 characters");
            return false;
        }
        return true;
    }
}
