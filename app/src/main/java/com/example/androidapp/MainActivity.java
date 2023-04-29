package com.example.androidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.Switch;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Spinner spinner = findViewById(R.id.my_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.spinner_items, R.layout.spinner_text);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setSelection(0);

        Button btn_clear = findViewById(R.id.btn_clear);
        Button btn_search = findViewById(R.id.btn_search);
        EditText keyword = findViewById(R.id.keyword);
        EditText distance = findViewById(R.id.distance);
        EditText location = findViewById(R.id.location);
        Switch auto = findViewById(R.id.auto);
        distance.setText("10");
        btn_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 在这里执行你想要实现的逻辑
                // ...
                keyword.setText("");
                distance.setText("10");
                spinner.setSelection(0);
                location.setText("");
                auto.setChecked(false);

            }
        });
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 在这里执行你想要实现的逻辑
                // ...
                String text = keyword.getText().toString().trim();
                if (TextUtils.isEmpty(text)) {
                    keyword.setError("This field cannot be empty.");

                }


            }
        });

    }


}