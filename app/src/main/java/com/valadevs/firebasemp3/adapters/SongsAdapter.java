package com.valadevs.firebasemp3.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.valadevs.firebasemp3.R;
import com.valadevs.firebasemp3.models.Song;
import java.util.ArrayList;

import pl.droidsonroids.gif.GifImageView;

public class SongsAdapter extends RecyclerView.Adapter<SongsAdapter.SongsViewHolder> {

    private ArrayList<Song> songs;
    private SimpleExoPlayer exoPlayer;
    private SongsViewHolder currentViewPlaying = null;

    public SongsAdapter(ArrayList<Song> songs,SimpleExoPlayer exoPlayer) {
        this.songs = songs;
        this.exoPlayer = exoPlayer;
    }

    @NonNull
    @Override
    public SongsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_song_rv, parent, false);
        return new SongsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SongsViewHolder holder, int position) {
        holder.title.setText(songs.get(position).getTitle());
        holder.artist.setText(songs.get(position).getArtist());
        holder.album.setText(songs.get(position).getAlbum());

        holder.itemView.setOnClickListener(v -> {
            exoPlayer.prepare();
            exoPlayer.seekToDefaultPosition(position);
            exoPlayer.setPlayWhenReady(true);
            holder.isPlayingGif.setVisibility(View.VISIBLE);
            if(currentViewPlaying!=null){
                currentViewPlaying.isPlayingGif.setVisibility(View.GONE);
            }
            currentViewPlaying = holder;

        });
    }

    @Override
    public int getItemCount() {
        return songs.size();
    }


    public class SongsViewHolder extends RecyclerView.ViewHolder {
        private TextView title, artist, album;
        private GifImageView isPlayingGif;

        public SongsViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.titulo);
            artist = itemView.findViewById(R.id.artista);
            album = itemView.findViewById(R.id.album);
            isPlayingGif = itemView.findViewById(R.id.isPlaying);
        }
    }
}