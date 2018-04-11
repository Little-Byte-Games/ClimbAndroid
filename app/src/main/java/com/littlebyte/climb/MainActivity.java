package com.littlebyte.climb;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;

import java.util.List;

import io.swagger.client.api.SampleDataApi;
import io.swagger.client.model.WeatherForecast;

class DataResponse implements Listener<List<WeatherForecast>> {
    final private TextView mTextView;

    public DataResponse(TextView textView) {
        mTextView = textView;
    }

    @Override
    public void onResponse(List<WeatherForecast> response) {
        String text = "";
        if (response != null) {
            for (int i = 0; i < response.size(); i++) {
                text += response.get(i).toString() + "\n";
            }
        }
        mTextView.setText(text);
    }
}

class DataError implements Response.ErrorListener {
    final private TextView mTextView;

    public DataError(TextView textView) {
        mTextView = textView;
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        mTextView.setText(error.getMessage());
    }
}


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void loadData(View view) {
        final TextView mTextView = findViewById(R.id.textView);
        mTextView.setText("loading...");

        SampleDataApi sampleData = new SampleDataApi();
        sampleData.setBasePath("http://192.168.196.1:45455");

        sampleData.sampleDataWeatherForecasts(new DataResponse(mTextView), new DataError(mTextView));
    }

    private void test() {
        final TextView mTextView = findViewById(R.id.textView);
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://192.168.196.1:45456/api/v1/SampleData/WeatherForecasts";

        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        mTextView.setText(response.toString());
                    }
                }, new ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        mTextView.setText(error.getMessage());

                    }
                });

        queue.add(jsonObjectRequest);
    }
}
