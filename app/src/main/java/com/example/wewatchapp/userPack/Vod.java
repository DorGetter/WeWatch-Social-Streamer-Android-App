package com.example.wewatchapp.userPack;

import android.app.ProgressDialog;
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