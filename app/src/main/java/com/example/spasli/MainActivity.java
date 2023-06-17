package com.example.spasli;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;

public class MainActivity extends AppCompatActivity {

    private EditText mViewUser, mViewPassword;
    private Button login,register;

    //Mendefinisikan variabel untuk firebase
    private FirebaseRemoteConfig mFirebaseRemoteConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Mendapatkan objek singleton Remote Config
        mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
                .setMinimumFetchIntervalInSeconds(60)
                .build();
        mFirebaseRemoteConfig.setConfigSettingsAsync(configSettings);

        //Menetapkan parameter value default dalam aplikasi
        mFirebaseRemoteConfig.setDefaultsAsync(R.xml.remote_config_defaults);
        //Memanggil fungsi untuk mengaktifkan dan mengamnbil nilai parameter firebase
        getValueFromFireBaseConfig();

        mViewUser=findViewById(R.id.etUsernameLogin);
        mViewPassword =findViewById(R.id.etPassLogin);
        login = findViewById(R.id.btnLogin);
        register = findViewById(R.id.btnRegister);

        mViewPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_NULL) {
                    cek();
                    return true;
                }
                return false;
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cek();
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(),RegisterActivity.class));
            }
        });
    }

    private void getValueFromFireBaseConfig(){
        //Mengambil dan mengaktifkan nilai
        mFirebaseRemoteConfig.fetchAndActivate()
                .addOnCompleteListener(new OnCompleteListener<Boolean>() {
                    @Override
                    public void onComplete(@NonNull Task<Boolean> task) {
                        if(task.isSuccessful()){
                            boolean updated = task.getResult();
                            Log.d("PV", "Config params updated: " + updated);
                            Toast.makeText(MainActivity.this, "Fetch and activate succeeded",
                                    Toast.LENGTH_SHORT).show();
                            String text = mFirebaseRemoteConfig.getString("btn_login_text");
                            login.setText(text);
                        }
                        else {
                            Toast.makeText(MainActivity.this,"Fetch Failed",Toast.LENGTH_LONG).show();
                        }
                        //Mendapatkan parameter value yang akan digunakan dalam aplikasi
                        String text = mFirebaseRemoteConfig.getString("btn_login_text");
                        login.setText(text);
                    }
                });
    }

//    protected void onStart() {
//        super.onStart();
//        if (Preferences.getLoggedStatus(getBaseContext())){
//            startActivity(new Intent(getBaseContext(),MainActivity.class));
//            finish();
//        }
//    }

    //Fungsi untuk mengecek password dan username, apakah sudah di isi
    private void cek(){
        mViewUser.setError(null);
        mViewPassword.setError(null);
        View fokus = null;
        boolean cancel = false;

        String user = mViewUser.getText().toString();
        String password = mViewPassword.getText().toString();

        if (TextUtils.isEmpty(user)){
            mViewUser.setError("Nama Pengguna harus di isi");
            fokus = mViewUser;
            cancel = true;
        }else if(!cekUser(user)){
            mViewUser.setError("Nama Pengguna salah");
            fokus = mViewUser;
            cancel = true;
        }

        if (TextUtils.isEmpty(password)){
            mViewPassword.setError("Kata Sandi harus di isi");
            fokus = mViewPassword;
            cancel = true;
        }else if (!cekPassword(password)){
            mViewPassword.setError("Kata Sandi salah");
            fokus = mViewPassword;
            cancel = true;
        }

        /* Jika cancel true, variable fokus mendapatkan fokus */
        if (cancel) fokus.requestFocus();
        else masuk();
    }

    private void masuk(){
        Preferences.setLoggedUser(getBaseContext(),Preferences.getRegisteredUser(getBaseContext()));
        Preferences.setLoggedStatus(getBaseContext(),true);
        startActivity(new Intent(getBaseContext(),LoggedActivity.class));finish();
    }

    //fungsi untuk mengecek password
    private boolean cekPassword(String password){
        return password.equals(Preferences.getRegisteredPass(getBaseContext()));
    }

    private boolean cekUser(String user){
        return user.equals(Preferences.getRegisteredUser(getBaseContext()));
    }
}