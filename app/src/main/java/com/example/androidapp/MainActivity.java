package com.example.androidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;

import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.Switch;

import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {
    String lat;
    String lng;

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
        AutoCompleteTextView keyword = findViewById(R.id.keyword);
        EditText distance = findViewById(R.id.distance);
        EditText location = findViewById(R.id.location);
        Switch auto = findViewById(R.id.auto);
        distance.setText("10");
        ArrayAdapter<String> keyword_adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, new ArrayList<String>());
        keyword.setAdapter(keyword_adapter);


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
                String keytext = keyword.getText().toString().trim();
                String loctext = location.getText().toString().trim();
                if (TextUtils.isEmpty(keytext)) {
                    Snackbar.make(keyword, "Please fill all fields", Snackbar.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(loctext) && !auto.isChecked()){
                       Snackbar.make(location, "Please fill all fields", Snackbar.LENGTH_SHORT).show();
                }
                else {

                    if (auto.isChecked()) {
                        String url = "https://ipinfo.io/json?token=3c5f05564839d1";
                        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                                response -> {
                                    try {
                                        JSONObject jsonResponse = new JSONObject(response);
                                        String loc = jsonResponse.getString("loc");
                                        String[] latLng = loc.split(",");
                                        lat = latLng[0];
                                        lng = latLng[1];
                                        Log.d("lat", lat);
                                        Log.d("lng", lng);
                                        JSONObject jsondata = new JSONObject();
                                        try {
                                            jsondata.put("tkeyword", keyword.getText().toString());
                                            jsondata.put("tdistance", distance.getText().toString());
                                            jsondata.put("tcategory", spinner.getSelectedItem().toString());
                                            jsondata.put("tlat", lat);
                                            jsondata.put("tlng", lng);
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                        if (lat == "" || lng == "") {

                                        } else {
                                            String url2 = null;
                                            try {
                                                Log.d("jsonobject", String.valueOf(jsondata));
                                                url2 = "https://myfirstnodejs-379900.wl.r.appspot.com/data?jsondata=" + URLEncoder.encode(jsondata.toString(), "UTF-8");
                                                Log.d("url", url2);
                                            } catch (UnsupportedEncodingException e) {
                                                throw new RuntimeException(e);
                                            }
                                            StringRequest stringRequest2 = new StringRequest(Request.Method.GET, url2,
                                                    response2 -> {
                                                        try {
                                                            Log.d("jsondatastr", response2);
                                                            JSONObject jsonResponse2 = new JSONObject(response2);
                                                            Log.d("jsondata", String.valueOf(jsonResponse2));
//                                        creatable(jsonResponse);
                                                            //tableflag = true;
                                                            //creattable(jsonResponse);
                                                        } catch (JSONException e) {
                                                            e.printStackTrace();
                                                        }
                                                    },
                                                    error -> {
                                                        // 处理错误
                                                    }
                                            );
                                            Volley.newRequestQueue(MainActivity.this).add(stringRequest2);

                                        }


                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }, error -> {
                            // 处理错误
                        }
                        );
                        Volley.newRequestQueue(MainActivity.this).add(stringRequest);
                    }
                    else {
                        String locationv = location.getText().toString();
                        locationv = locationv.replaceAll(" ", "+");
                        locationv = locationv.replaceAll(",", "+");

                        String url = "https://maps.googleapis.com/maps/api/geocode/json?address=" + locationv + "&key=AIzaSyC5rzO2n0yCE-k8V32GXk2gnRqma1uxUrQ";
                        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        try {
                                            JSONObject jsonResponse = new JSONObject(response);
                                            lat = jsonResponse.optJSONArray("results").optJSONObject(0).optJSONObject("geometry").optJSONObject("location").optString("lat", "");
                                            lng = jsonResponse.optJSONArray("results").optJSONObject(0).optJSONObject("geometry").optJSONObject("location").optString("lng", "");
                                            Log.d("lat", String.valueOf(lat));
                                            Log.d("lng", String.valueOf(lng));
                                            JSONObject jsondata = new JSONObject();
                                            try {
                                                jsondata.put("tkeyword", keyword.getText().toString());
                                                jsondata.put("tdistance", distance.getText().toString());
                                                jsondata.put("tcategory", spinner.getSelectedItem().toString());
                                                jsondata.put("tlat", lat);
                                                jsondata.put("tlng", lng);
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                            if (lat == "" || lng == "") {

                                            } else {
                                                String url2 = null;
                                                try {
                                                    Log.d("jsonobject", String.valueOf(jsondata));
                                                    url2 = "https://myfirstnodejs-379900.wl.r.appspot.com/data?jsondata=" + URLEncoder.encode(jsondata.toString(), "UTF-8");
                                                    Log.d("url", url2);
                                                } catch (UnsupportedEncodingException e) {
                                                    throw new RuntimeException(e);
                                                }
                                                StringRequest stringRequest2 = new StringRequest(Request.Method.GET, url2,
                                                        response2 -> {
                                                            try {
                                                                Log.d("jsondatastr", response2);
                                                                JSONObject jsonResponse2 = new JSONObject(response2);
                                                                Log.d("jsondata", String.valueOf(jsonResponse2));
//                                        creatable(jsonResponse);
                                                                //tableflag = true;
                                                                //creattable(jsonResponse);
                                                            } catch (JSONException e) {
                                                                e.printStackTrace();
                                                            }
                                                        },
                                                        error -> {
                                                            // 处理错误
                                                        }
                                                );
                                                Volley.newRequestQueue(MainActivity.this).add(stringRequest2);

                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // 处理错误
                            }
                        });
                        Volley.newRequestQueue(MainActivity.this).add(stringRequest);

                    }
                }






            }
        });

        auto.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // Switch 选中
                    location.setText("");
                    location.setEnabled(false);
                } else {
                    // Switch 没有选中
                    location.setEnabled(true);
                }
            }
        });

       

        keyword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String keywordstr = charSequence.toString();
                if (keywordstr.length() >= 1) { // 只在输入文本长度大于等于1时才发送搜索请求
                    search(keywordstr, keyword_adapter);
                    Log.d("OLD", "done");

                }
                else {
                    keyword_adapter.clear();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });




    }

//    private void search(String keyword, ArrayAdapter<CharSequence> adapter) {
//    }
    private void search(String keywordstr, ArrayAdapter<String> adapter) {
        Log.d("NEW", "start");
        String url = "https://myfirstnodejs-379900.wl.r.appspot.com/search?searchText=" + keywordstr;
//        String url = "http://10.0.2.2:3000/search?searchText=" + keywordstr;
//        String url = "http://10.0.2.2:3000/search";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    // 处理响应
//                    Log.d("data", response.toString());
                    Log.d("TAG", "over");
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        ArrayList<String> keywords = new ArrayList<>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            String item = jsonArray.getString(i);
                            keywords.add(item);
                        }
                        Log.d("keywords", String.valueOf(keywords));
                        adapter.clear();
                        adapter.addAll(keywords);
                        adapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> {
                    // 处理错误
                });


        Volley.newRequestQueue(this).add(stringRequest);
    }


}