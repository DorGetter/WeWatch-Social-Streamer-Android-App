package com.example.wewatchapp.userPack;

import android.app.ProgressDialog;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.wewatchapp.Adapter.MoviesShowAdapter;
import com.example.wewatchapp.Model.GetVideoDetails;
import com.example.wewatchapp.Model.SliderSide;
import com.example.wewatchapp.R;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

public class Vod extends AppCompatActivity {

    MoviesShowAdapter moviesShowAdapter;
    DatabaseReference mDatabaseReference;
    private List<GetVideoDetails> uploads,uploadlistLatest,uploadListPopular;
    private List<GetVideoDetails> actionMovies,sportMovies,comedyMovies,romanticMovies,adventureMovies;
    private ViewPager slidePager;
    private List<SliderSide> uploadSlider;
    private TabLayout indicator,tabMovieActions;
    private RecyclerView moviesRv,moviesRvWeek,tab;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vod);

        inViews();
        moviesViewTab();
    }

    private void addAllMovies(){
        uploads = new ArrayList<>();
        uploadlistLatest = new ArrayList<>();
        uploadListPopular = new ArrayList<>();
        actionMovies = new ArrayList<>();
        adventureMovies = new ArrayList<>();
        comedyMovies = new ArrayList<>();
        sportMovies = new ArrayList<>();
        romanticMovies = new ArrayList<>();
        uploadSlider = new ArrayList<>();


    }


    private void moviesViewTab(){
        tabMovieActions.addTab(tabMovieActions.newTab().setText("action"));
        tabMovieActions.addTab(tabMovieActions.newTab().setText("adventure"));
        tabMovieActions.addTab(tabMovieActions.newTab().setText("comedy"));
        tabMovieActions.addTab(tabMovieActions.newTab().setText("Romance"));
        tabMovieActions.addTab(tabMovieActions.newTab().setText("Sports"));
        tabMovieActions.setTabGravity(TabLayout.GRAVITY_FILL);
        tabMovieActions.setTabTextColors(ColorStateList.valueOf(Color.WHITE));

        tabMovieActions.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch(tab.getPosition()){
                    case 0:
                        break;
                    case 1:
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    private void inViews(){
        tabMovieActions = findViewById(R.id.tabActionMovies);
        slidePager = findViewById(R.id.slider_page); // check if not work
        indicator = findViewById(R.id.indicator);
        moviesRvWeek = findViewById(R.id.rv_movies_week);
        moviesRv = findViewById(R.id.Rv_movies);
        tab = findViewById(R.id.tabrecycler);

    }
}