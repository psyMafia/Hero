package com.z.gleb.hero;

import android.app.DownloadManager;
import android.content.SharedPreferences;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements HeroAdapter.OnItemClickListener {

    private RecyclerView _recyclerView;
    private HeroAdapter _heroAdapter;
    private ArrayList<HeroItem> _heroItemList;
    private RequestQueue _requestQueue;


    private int FavoritItemPosition;
    private final static String FAVORIT_KEY = "FAVORIT_KEY";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        _recyclerView = findViewById(R.id.recycler_view);
        _recyclerView.setHasFixedSize(true);
        _recyclerView.setLayoutManager(new LinearLayoutManager(this));

        _heroItemList = new ArrayList<>();
        _requestQueue = Volley.newRequestQueue(this);


        SharedPreferences sharedPref = getPreferences(MODE_PRIVATE);
        FavoritItemPosition = sharedPref.getInt(FAVORIT_KEY, 0);


        if (_heroItemList.isEmpty()) {
            parseJSON();
        }else {
            initHeroAdapter();
        }

    }

    private void parseJSON(){
        String url = "https://heroapps.co.il/employee-tests/android/androidexam.json";

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {



                for (int i = 0 ; i < response.length() ; i++){

                    try {
                        JSONObject heroJSON = response.getJSONObject(i);

                        String name = heroJSON.getString("title");

                        JSONArray abilitiesJSON = heroJSON.getJSONArray("abilities");
                        ArrayList<String> abilities = new ArrayList<>();
                        for (int j = 0 ; j < abilitiesJSON.length() ; j++){

                            String ability = abilitiesJSON.getString(j);
                            abilities.add(ability);
                        }

                        String imageUrl = heroJSON.getString("image");

                        _heroItemList.add(new HeroItem(name, abilities, imageUrl));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

            initHeroAdapter();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        _requestQueue.add(request);
    }

    private void initHeroAdapter(){

        _heroAdapter = new HeroAdapter(MainActivity.this, _heroItemList);
        _heroAdapter.setOnItemClickListener(MainActivity.this);
        _recyclerView.setAdapter(_heroAdapter);


            onItemClick(FavoritItemPosition);


    }

    @Override
    public void onItemClick(int position) {

        FavoritItemPosition = position;

        HeroItem clickedItem = _heroItemList.get(position);
        ImageView favoritImage = findViewById(R.id.sected_hero_image_view);
        Picasso.get().load(clickedItem.getImageUrl()).fit().centerInside().into(favoritImage);
        CollapsingToolbarLayout toolbarLayout = findViewById(R.id.toolBar_heroName);
        toolbarLayout.setTitle(clickedItem.getTitle());




        for (HeroItem item :
                _heroItemList) {
            item.setFavorite(false);
        }
        clickedItem.setFavorite(true);


       _heroAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt(FAVORIT_KEY, FavoritItemPosition);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        if (savedInstanceState != null){
            this.FavoritItemPosition = savedInstanceState.getInt(FAVORIT_KEY);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        SharedPreferences sharedPref = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(FAVORIT_KEY, FavoritItemPosition);
        editor.commit();
    }
}
