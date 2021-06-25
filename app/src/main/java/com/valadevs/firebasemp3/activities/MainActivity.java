package com.valadevs.firebasemp3.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.widget.Toast;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.PlayerControlView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.valadevs.firebasemp3.R;
import com.valadevs.firebasemp3.adapters.SongsAdapter;
import com.valadevs.firebasemp3.models.Song;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{

    private RecyclerView recyclerView;
    private ArrayList<Song> songsList;
    private SongsAdapter adapter;

    private FirebaseDatabase database;

    private PlayerControlView playerView;
    private SimpleExoPlayer exoPlayer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
            initComponents();
            getSongs();
    }

    private void initComponents() {
        songsList =  new ArrayList<>();
        database = FirebaseDatabase.getInstance();
        recyclerView = findViewById(R.id.main_rv);
        playerView = findViewById(R.id.stream_player);
        exoPlayer = new SimpleExoPlayer.Builder(MainActivity.this).build();
        playerView.show();
        playerView.setPlayer(exoPlayer);
    }


    private void instanceRecycler() {
        adapter = new SongsAdapter(songsList,exoPlayer);
        LinearLayoutManager llm = new LinearLayoutManager(MainActivity.this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(llm);
    }

    private void getSongs() {
        DatabaseReference myRef = database.getReference();
        ArrayList<MediaItem> mediaItems = new ArrayList<>();
        myRef.child("songs").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                songsList.clear();
                exoPlayer.clearMediaItems();

                for (DataSnapshot snap : snapshot.getChildren()) {
                    Song auxSong = snap.getValue(Song.class);
                    songsList.add(auxSong);
                    mediaItems.add(MediaItem.fromUri(auxSong.getUrl()));
                }
                exoPlayer.setMediaItems(mediaItems);

                instanceRecycler();
                Toast.makeText(MainActivity.this,"Canciones Actualizadas",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}