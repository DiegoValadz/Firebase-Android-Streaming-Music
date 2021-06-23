package com.valadevs.firebasemp3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.valadevs.firebasemp3.adapters.SongsAdapter;
import com.valadevs.firebasemp3.models.Song;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<Song> songs;
    private SongsAdapter adapter;
    FirebaseDatabase database;
    private static final String TAG = "MyActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        songs =  new ArrayList<>();
        database = FirebaseDatabase.getInstance();
        database.setPersistenceEnabled(true);
        recyclerView = findViewById(R.id.main_rv);
        instanceRecycler();


    }

    private void instanceRecycler() {
        getSongs();

    }

    private  void getSongs() {
        DatabaseReference myRef = database.getReference();

    /*    myRef.child("songs").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.isSuccessful()) {
                    for (DataSnapshot snap : task.getResult().getChildren()) {
                        Song auxSong = snap.getValue(Song.class);
                        songs.add(auxSong);

                    }
                    adapter = new SongsAdapter(songs,recyclerView);
                    LinearLayoutManager llm = new LinearLayoutManager(MainActivity.this);
                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(llm);

                }else{
                    Toast.makeText(MainActivity.this,task.getException().toString(),Toast.LENGTH_LONG).show();
                }



            }


        });*/


      myRef.child("songs").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                songs.clear();
                for (DataSnapshot snap : snapshot.getChildren()) {
                    Song auxSong = snap.getValue(Song.class);
                    songs.add(auxSong);

                }
                adapter = new SongsAdapter(songs,recyclerView);
                LinearLayoutManager llm = new LinearLayoutManager(MainActivity.this);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(llm);
                Toast.makeText(MainActivity.this,"Canciones Actualizadas",Toast.LENGTH_LONG).show();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}