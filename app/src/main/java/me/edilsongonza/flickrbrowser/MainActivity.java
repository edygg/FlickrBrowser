package me.edilsongonza.flickrbrowser;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class MainActivity extends ActionBarActivity implements SwipeRefreshLayout.OnRefreshListener {

    private static final String FLICKR_FEED_URL = "https://api.flickr.com/services/feeds/photos_public.gne?format=json&nojsoncallback=1";
    public static final String TAG = MainActivity.class.getSimpleName();
    private RecyclerView photoList;
    private LinearLayoutManager layoutManager;
    private SwipeRefreshLayout refreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initComponents();
        getJSONData();
    }

    private void initComponents() {
        photoList = (RecyclerView) findViewById(R.id.photo_list);
        photoList.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        photoList.setLayoutManager(layoutManager);
        photoList.setItemAnimator(new DefaultItemAnimator());

        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.refresh_container);
        refreshLayout.setOnRefreshListener(this);
    }

    public void getJSONData() {

        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, FLICKR_FEED_URL, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray items = response.getJSONArray("items");
                    Gson gson = new GsonBuilder().create();
                    FlickrPhotoItem[] photos = gson.fromJson(items.toString(), FlickrPhotoItem[].class);
                    List<FlickrPhotoItem> allPhotos = new ArrayList<FlickrPhotoItem>(Arrays.asList(photos));
                    FlickrPhotoListAdapter adapter = new FlickrPhotoListAdapter(allPhotos);
                    photoList.setAdapter(adapter);
                    refreshLayout.setRefreshing(false);
                } catch (JSONException ex) {
                    ex.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_LONG).show();
            }
        });

        AppController.getIntance().addToRequestQueue(jsonRequest);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRefresh() {
        getJSONData();
    }
}
