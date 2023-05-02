package com.example.androidapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link formfrg#newInstance} factory method to
 * create an instance of this fragment.
 */
public class formfrg extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    String lat;
    String lng;
    JSONObject jsondata = new JSONObject();



    public formfrg() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment formfrg.
     */
    // TODO: Rename and change types and number of parameters
    public static formfrg newInstance(String param1, String param2) {
        formfrg fragment = new formfrg();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }





    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_formfrg, container, false);
        Spinner spinner = view.findViewById(R.id.my_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                requireContext(), R.array.spinner_items, R.layout.spinner_text);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setSelection(0);

        Button btn_clear = view.findViewById(R.id.btn_clear);
        Button btn_search = view.findViewById(R.id.btn_search);
        AutoCompleteTextView keyword = view.findViewById(R.id.keyword);
        EditText distance = view.findViewById(R.id.distance);
        EditText location = view.findViewById(R.id.location);
        Switch auto = view.findViewById(R.id.auto);
        distance.setText("10");
        ArrayAdapter<String> keyword_adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, new ArrayList<String>());
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

                                        try {
                                            jsondata.put("tkeyword", keyword.getText().toString());
                                            jsondata.put("tdistance", distance.getText().toString());
                                            jsondata.put("tcategory", spinner.getSelectedItem().toString());
                                            jsondata.put("tlat", lat);
                                            jsondata.put("tlng", lng);
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                        NavController navController = Navigation.findNavController(requireView());
                                        Bundle bundle = new Bundle();
                                        try {
                                            bundle.putString("url",  "https://myfirstnodejs-379900.wl.r.appspot.com/data?jsondata=" + URLEncoder.encode(jsondata.toString(), "UTF-8"));
                                        } catch (UnsupportedEncodingException e) {
                                            throw new RuntimeException(e);
                                        }
//                    bundle.putString("key", "芜湖");
                                        navController.navigate(R.id.action_formfrg_to_tablefrg,bundle);
//                                        if (lat == "" || lng == "") {
//
//                                        } else {
//                                            String url2 = null;
//                                            try {
//                                                Log.d("jsonobject", String.valueOf(jsondata));
//                                                url2 = "https://myfirstnodejs-379900.wl.r.appspot.com/data?jsondata=" + URLEncoder.encode(jsondata.toString(), "UTF-8");
//                                                Log.d("url", url2);
//                                            } catch (UnsupportedEncodingException e) {
//                                                throw new RuntimeException(e);
//                                            }
//                                            StringRequest stringRequest2 = new StringRequest(Request.Method.GET, url2,
//                                                    response2 -> {
//                                                        try {
//                                                            Log.d("jsondatastr", response2);
//                                                            JSONObject jsonResponse2 = new JSONObject(response2);
//                                                            Log.d("jsondata", String.valueOf(jsonResponse2));
//
////                                                            NavController navController = getParentFragment().getViewLifecycleOwner().getNavController();
//                                                            NavController navController = Navigation.findNavController(requireView());
//                                                            navController.navigate(R.id.action_formfrg_to_tablefrg);
//
//
//
////                                        creatable(jsonResponse);
//                                                            //tableflag = true;
//                                                            //creattable(jsonResponse);
////                                                            NavHostFragment navHostFragment = (NavHostFragment) requireParentFragment().getChildFragmentManager().findFragmentById(R.id.form_fragment);
////                                                            NavHostFragment navHostFragment = (NavHostFragment) getParentFragment().getParentFragmentManager().findFragmentById(R.id.form_fragment);
////
////                                                            NavController navController = navHostFragment.getNavController();
////                                                            navController.navigate(R.id.form_to_table);
//                                                            // 获取frg1的实例
////                                                            Fragment frg1 = getParentFragmentManager().findFragmentById(R.id.main_fragment_container);
////
////                                                            if (frg1 != null) {
////                                                                // 获取frg1的NavHostFragment实例
////                                                                Log.d("TAG", "有了了了了了了了了");
////                                                                NavHostFragment navHostFragment = (NavHostFragment) frg1;
////
////                                                                if (navHostFragment != null) {
////                                                                    // 获取NavController实例
////                                                                    NavController navController = navHostFragment.getNavController();
////
////                                                                    // 切换到frg3
////                                                                    navController.navigate(R.id.table_fragment);
////                                                                }
////                                                                else {
////                                                                    Log.d("TAG", "nullllll: ");
////                                                                }
////                                                            }
////                                                            else {
////                                                                Log.d("TAG", "nullllll: ");
////                                                            }
//
////                                                            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
////                                                            Fragment newFragment = new tablefrg();
////                                                            fragmentManager.beginTransaction().replace(R.id.form_fragment, newFragment).commit();
//
//
//                                                        } catch (JSONException e) {
//                                                            e.printStackTrace();
//                                                        }
//                                                    },
//                                                    error -> {
//                                                        // 处理错误
//                                                    }
//                                            );
//                                            Volley.newRequestQueue(getContext()).add(stringRequest2);
//
//                                        }


                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }, error -> {
                            // 处理错误
                        }
                        );
                        Volley.newRequestQueue(getContext()).add(stringRequest);
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
                                            try {
                                                jsondata.put("tkeyword", keyword.getText().toString());
                                                jsondata.put("tdistance", distance.getText().toString());
                                                jsondata.put("tcategory", spinner.getSelectedItem().toString());
                                                jsondata.put("tlat", lat);
                                                jsondata.put("tlng", lng);
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                            NavController navController = Navigation.findNavController(requireView());
                                            Bundle bundle = new Bundle();
                                            try {
                                                bundle.putString("url",  "https://myfirstnodejs-379900.wl.r.appspot.com/data?jsondata=" + URLEncoder.encode(jsondata.toString(), "UTF-8"));
                                            } catch (UnsupportedEncodingException e) {
                                                throw new RuntimeException(e);
                                            }
//                    bundle.putString("key", "芜湖");
                                            navController.navigate(R.id.action_formfrg_to_tablefrg,bundle);
//                                            if (lat == "" || lng == "") {
//
//                                            } else {
//                                                String url2 = null;
//                                                try {
//                                                    Log.d("jsonobject", String.valueOf(jsondata));
//                                                    url2 = "https://myfirstnodejs-379900.wl.r.appspot.com/data?jsondata=" + URLEncoder.encode(jsondata.toString(), "UTF-8");
//                                                    Log.d("url", url2);
//                                                } catch (UnsupportedEncodingException e) {
//                                                    throw new RuntimeException(e);
//                                                }
//                                                StringRequest stringRequest2 = new StringRequest(Request.Method.GET, url2,
//                                                        response2 -> {
//                                                            try {
//                                                                Log.d("jsondatastr", response2);
//                                                                JSONObject jsonResponse2 = new JSONObject(response2);
//                                                                Log.d("jsondata", String.valueOf(jsonResponse2));
////                                        creatable(jsonResponse);
//                                                                //tableflag = true;
//                                                                //creattable(jsonResponse);
//                                                            } catch (JSONException e) {
//                                                                e.printStackTrace();
//                                                            }
//                                                        },
//                                                        error -> {
//                                                            // 处理错误
//                                                        }
//                                                );
//                                                Volley.newRequestQueue(getContext()).add(stringRequest2);
//
//                                            }
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
                        Volley.newRequestQueue(getContext()).add(stringRequest);

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


//        return inflater.inflate(R.layout.fragment_formfrg, container, false);
        return view;
    }

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


        Volley.newRequestQueue(getContext()).add(stringRequest);
    }
}