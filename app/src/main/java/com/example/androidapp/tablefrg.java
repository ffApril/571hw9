package com.example.androidapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link tablefrg#newInstance} factory method to
 * create an instance of this fragment.
 */
public class tablefrg extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public tablefrg() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment tablefrg.
     */
    // TODO: Rename and change types and number of parameters
    public static tablefrg newInstance(String param1, String param2) {
        tablefrg fragment = new tablefrg();
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
        View view = inflater.inflate(R.layout.fragment_tablefrg, container, false);
        ProgressBar progressBar = view.findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.VISIBLE);
        String url = getArguments().getString("url");
        TextView json = view.findViewById(R.id.jsonid);
        json.setText(url);

        StringRequest stringRequest2 = new StringRequest(Request.Method.GET, url,
                response2 -> {
                    try {
                        Log.d("jsondatastr", response2);
                        JSONObject jsonResponse2 = new JSONObject(response2);
                        Log.d("jsondata", String.valueOf(jsonResponse2));
                        progressBar.setVisibility(View.GONE); // 隐藏进度条




                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    // 处理错误
                }
        );
        Volley.newRequestQueue(getContext()).add(stringRequest2);





        return view;
    }
}