package com.example.spasli;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;

public class RegisterActivity extends AppCompatActivity {

    private EditText mViewUser, mViewPassword, mViewPassword2;
    private Button daftar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mViewUser = findViewById(R.id.etUsernameRegister);
        mViewPassword = findViewById(R.id.etPassRegister);
        mViewPassword2 = findViewById(R.id.etPassRegister2);
        daftar = findViewById(R.id.btnDaftar);

        mViewPassword2.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_NULL) {
                    cek();
                    return true;
                }
                return false;
            }
        });

        daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cek();
            }
        });
    }

    //Fungsi untuk mengecek apakah semua kolom sudah di isi
    private void cek(){
        mViewUser.setError(null);
        mViewPassword.setError(null);
        mViewPassword2.setError(null);
        View fokus = null;
        boolean cancel = false;

        String password2 = mViewPassword2.getText().toString();
        String user = mViewUser.getText().toString();
        String password = mViewPassword.getText().toString();

        if (TextUtils.isEmpty(user)){
            mViewUser.setError("Nama Pengguna harus di isi");
            fokus = mViewUser;
            cancel = true;
        }else if(cekUser(user)){
            mViewUser.setError("Nama Pengguna sudah ada");
            fokus = mViewUser;
            cancel = true;
        }

        if (TextUtils.isEmpty(password)){
            mViewPassword.setError("Kata Sandi harus di isi");
            fokus = mViewPassword;
            cancel = true;
        }else if (!cekPassword(password,password2)){
            mViewPassword2.setError("This password is incorrect");
            fokus = mViewPassword2;
            cancel = true;
        }

        if (cancel){
            fokus.requestFocus();
        }else{
            Preferences.setRegisteredUser(getBaseContext(),user);
            Preferences.setRegisteredPass(getBaseContext(),password);
            finish();
        }
    }

    //untuk mengecek apakah password yang diisikan kembali sama atau tidak
    private boolean cekPassword(String password, String repassword){
        return password.equals(repassword);
    }

    //untuk mengecek apakah nama pengguna sudah digunakan atau tidak
    private boolean cekUser(String user){
        return user.equals(Preferences.getRegisteredUser(getBaseContext()));
    }
}