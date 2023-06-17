package com.example.spasli;

import androidx.appcompat.app.AppCompatActivity;
import android.widget.Button;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class LoggedActivity extends AppCompatActivity {

    private Button keluar;
    private TextView name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged);

        keluar = findViewById(R.id.btnLogout);
        name = findViewById(R.id.tvName);

        name.setText(Preferences.getLoggedUser(getBaseContext()));

        keluar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Preferences.clearLogged(getBaseContext());
                startActivity(new Intent(getBaseContext(),MainActivity.class));
                finish();
            }
        });
    }
}