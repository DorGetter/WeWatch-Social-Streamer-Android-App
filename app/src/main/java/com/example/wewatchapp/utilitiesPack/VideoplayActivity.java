package com.example.wewatchapp.utilitiesPack;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.wewatchapp.R;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;
import java.util.HashMap;

public class VideoplayActivity extends AppCompatActivity {
    Uri videoUri;
    PlayerView playerView;
    ExoPlayer exoPlayer ;
    ExtractorsFactory extractorsFactory;
    ImageView exo_floating_widget ;
    String movieName;
    String userName;
    FirebaseDatabase database;
    DatabaseReference rootRef;
    DatabaseReference counterMovieRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setFullScreen();
        setContentView(R.layout.activity_videoplay);
        hideActionBar();
        playerView = findViewById(R.id.playerView);
        exo_floating_widget = findViewById(R.id.exo_floating_widget);
        Intent intent = getIntent();

        if(intent != null ){
            String uri_str = intent.getStringExtra("videoUri");
            movieName = intent.getStringExtra("movieName");
            userName = intent.getStringExtra("userID");

            database = FirebaseDatabase.getInstance();
            rootRef  = database.getReference("Views");

            movieViewed();
            updateMovieCounter();
//            Toast.makeText(VideoplayActivity.this, movieName + userName, Toast.LENGTH_LONG ).show();
            videoUri = Uri.parse(uri_str);
        }
        exo_floating_widget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exoPlayer.setPlayWhenReady(false);
                exoPlayer.release();
                Intent serviceintent = new Intent(VideoplayActivity.this,
                        FloatingWidgetService.class);
                serviceintent.putExtra("videoUri", videoUri.toString());
                startService(serviceintent);
            }
        });


        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelector trackSelector = new DefaultTrackSelector
                (new AdaptiveTrackSelection.Factory(bandwidthMeter));
        exoPlayer = ExoPlayerFactory.newSimpleInstance(this,trackSelector);
        extractorsFactory = new DefaultExtractorsFactory();
        playVideo();
    }

    private void hideActionBar() {

        getSupportActionBar().hide();
    }

    private void setFullScreen() {

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

    }



    private void playVideo(){

        try {
            String playerInfo = Util.getUserAgent(this,"MovieAppClient");
            DefaultDataSourceFactory dataSourceFactory = new DefaultDataSourceFactory(this,playerInfo);
            MediaSource mediaSource = new ExtractorMediaSource(
                    videoUri,dataSourceFactory,extractorsFactory,null,null);

            playerView.setPlayer(exoPlayer);
            exoPlayer.prepare(mediaSource);
            exoPlayer.setPlayWhenReady(true);


        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        exoPlayer.setPlayWhenReady(false);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        exoPlayer.setPlayWhenReady(false);
        exoPlayer.release();
    }





    private void updateMovieCounter(){
        counterMovieRef = database.getReference("movie_counter");

        counterMovieRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot child : snapshot.getChildren()){
                    MovieCounterView MCV = child.getValue(MovieCounterView.class);
                    if(movieName.compareTo(MCV.getMovie_name()) == 0){
                        HashMap map = new HashMap();
                        map.put("counter", MCV.getCounter()+1);
                        counterMovieRef.child(child.getKey()).updateChildren(map);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

//.addListenerForSingleValueEvent(new ValueEventListener() {
//
//            public void onDataChange(@NonNull DataSnapshot snapshot) {

    }

    private void movieViewed() {

        Views views = new Views();
        views.setMovieName(movieName);
        System.out.println(movieName);
        views.setUserName(userName);
        Date _date = new Date(System.currentTimeMillis());
        views.setDate(_date);
        /* set an ID from the database */
        views.setViewID(rootRef.push().getKey());
        /* insert the movie by its ID */
        rootRef.child(views.getViewID()).setValue(views);

        Toast.makeText(this, "Thanks   " + userName
                + "\nYour view saved...", Toast.LENGTH_LONG).show();

    }
}
