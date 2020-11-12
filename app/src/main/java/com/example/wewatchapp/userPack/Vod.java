package com.example.wewatchapp.userPack;

import android.app.ProgressDialog;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.wewatchapp.Adapter.MoviesShowAdapter;
import com.example.wewatchapp.Adapter.SliderPagerAdapterNew;
import com.example.wewatchapp.Model.GetVideoDetails;
import com.example.wewatchapp.Model.MoviesItemClickListenerNew;
import com.example.wewatchapp.Model.SliderSide;
import com.example.wewatchapp.R;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Vod extends AppCompatActivity implements MoviesItemClickListenerNew {

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

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbar);

        progressDialog = new ProgressDialog(this);

        inViews();
        addAllMovies();
        iniPopularMovies();
        iniWeekMovies();
        moviesViewTab();



    }


    /**
     * addinf movies from dataBase
     */
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

        mDatabaseReference = FirebaseDatabase.getInstance().getReference("videos");
        progressDialog.setMessage("loading...");
        progressDialog.show();

        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){

                    GetVideoDetails upload = postSnapshot.getValue(GetVideoDetails.class);
                    SliderSide slide = postSnapshot.getValue(SliderSide.class);

                    //getting to the label
                    if(upload.getVideo_type().equals("latest movies")){
                        uploadlistLatest.add(upload);
                    }
                    if(upload.getVideo_type().equals("Best popular movies")){
                        uploadListPopular.add(upload);
                    }

                    if(upload.getVideo_type().equals("Slide movies")){
                        uploadSlider.add(slide);
                    }

                    if(upload.getVideo_category().equals("Action")){
                        actionMovies.add(upload);
                    }

                    if(upload.getVideo_category().equals("adventure")){
                        adventureMovies.add(upload);
                    }

                    if(upload.getVideo_category().equals("romantic")){
                        romanticMovies.add(upload);
                    }
                    if(upload.getVideo_category().equals("sports")){
                        sportMovies.add(upload);
                    }

                    //                    if(upload.getVideo_type().equals("No Type")){
//                        .add(upload);
//                    }

                    uploads.add(upload);
                }
                iniSlider();
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    ///movies sliders  and tabs.
    private void iniSlider() {
        SliderPagerAdapterNew adapterNew = new SliderPagerAdapterNew(this,uploadSlider);
        slidePager.setAdapter(adapterNew);

        //setup timer:
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new SliderTimer(), 4000, 6000);
        indicator.setupWithViewPager(slidePager,true);

    }

    private void iniWeekMovies(){
        moviesShowAdapter = new MoviesShowAdapter(this,uploadlistLatest,this);
        moviesRvWeek.setAdapter(moviesShowAdapter);
        moviesRvWeek.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,false));
        moviesShowAdapter.notifyDataSetChanged();
    }

    private void iniPopularMovies(){
        moviesShowAdapter = new MoviesShowAdapter(this,uploadListPopular,this);
        moviesRv.setAdapter(moviesShowAdapter);
        moviesRv.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,false));
        moviesShowAdapter.notifyDataSetChanged();
    }



    private void moviesViewTab(){
        getActionMovies();
        tabMovieActions.addTab(tabMovieActions.newTab().setText("action"));
        tabMovieActions.addTab(tabMovieActions.newTab().setText("adventure"));
        tabMovieActions.addTab(tabMovieActions.newTab().setText("comedy"));
        tabMovieActions.addTab(tabMovieActions.newTab().setText("romance"));
        tabMovieActions.addTab(tabMovieActions.newTab().setText("sports"));
        tabMovieActions.setTabGravity(TabLayout.GRAVITY_FILL);
        tabMovieActions.setTabTextColors(ColorStateList.valueOf(Color.WHITE));

        tabMovieActions.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch(tab.getPosition()){
                    case 0:
                        getActionMovies();
                        break;
                    case 1:
                        getAdventureMovies();
                        break;
                    case 2:
                        getComedyMovies();
                        break;
                    case 3:
                        getRomanticMovies();
                        break;
                    case 4:
                        getSportMovies();
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

    @Override
    public void onMovieClick(GetVideoDetails getVideoDetails, ImageView imageView) {

    }

    /**
     * Slider moving video timer inner class
     */
    public class SliderTimer extends TimerTask {
        public void run(){

            Vod.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(slidePager.getCurrentItem()<uploadSlider.size()-1){
                        slidePager.setCurrentItem(slidePager.getCurrentItem()+1);
                    }else{                        slidePager.setCurrentItem(0); }
                }
            });
        }
    }

    private void getActionMovies(){
        moviesShowAdapter = new MoviesShowAdapter(this,actionMovies,this);
        tab.setAdapter(moviesShowAdapter);
        tab.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,false));
        moviesShowAdapter.notifyDataSetChanged();
    }

    private void getSportMovies(){
        moviesShowAdapter = new MoviesShowAdapter(this,sportMovies,this);
        tab.setAdapter(moviesShowAdapter);
        tab.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,false));
        moviesShowAdapter.notifyDataSetChanged();
    }

    private void getRomanticMovies(){
        moviesShowAdapter = new MoviesShowAdapter(this,romanticMovies,this);
        tab.setAdapter(moviesShowAdapter);
        tab.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,false));
        moviesShowAdapter.notifyDataSetChanged();
    }

    private void getComedyMovies(){
        moviesShowAdapter = new MoviesShowAdapter(this,comedyMovies,this);
        tab.setAdapter(moviesShowAdapter);
        tab.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,false));
        moviesShowAdapter.notifyDataSetChanged();
    }

    private void getAdventureMovies(){
        moviesShowAdapter = new MoviesShowAdapter(this,adventureMovies,this);
        tab.setAdapter(moviesShowAdapter);
        tab.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,false));
        moviesShowAdapter.notifyDataSetChanged();
    }


}