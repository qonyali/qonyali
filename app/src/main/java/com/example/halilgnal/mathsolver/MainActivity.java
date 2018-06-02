package com.example.halilgnal.mathsolver;


import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.halilgnal.mathsolver.util.QUtil;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Button btnBeginGame;
    Spinner itsDropdown;
    int itsSelectedItemPosition;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnBeginGame = findViewById(R.id.btnBeginGame);
        btnBeginGame.setOnClickListener(this);

        itsDropdown = findViewById(R.id.spinner);
        ArrayAdapter<QUtil.GameLevel> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, QUtil.GameLevel.values());
        itsDropdown.setAdapter(adapter);
        itsDropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                itsSelectedItemPosition = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                itsSelectedItemPosition = 0;
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.btnBeginGame:
                Intent beginGameIntent = new Intent(this, GameActivity.class);
                beginGameIntent.putExtra("game_level",itsSelectedItemPosition);
                startActivity(beginGameIntent);
                this.finish();
        }
    }
}
