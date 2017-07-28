package id.my.developer.imagepicker.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import id.my.developer.imagepicker.R;
import id.my.developer.imagepicker.models.Media;

/**
 * Created by light on 27/07/2017.
 */

public class MediaListAdapter extends RecyclerView.Adapter<MediaListAdapter.MediaViewHolder> {
    private Context context;
    private List<Media> mediaList;

    public MediaListAdapter(Context context, List<Media> mediaList) {
        this.context = context;
        this.mediaList = mediaList;
    }

    @Override
    public MediaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.media_item,parent,false);
        return new MediaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MediaViewHolder holder, int position) {
        Media media = mediaList.get(position);
        Glide.with(context).load(media.getPath()).diskCacheStrategy(DiskCacheStrategy.ALL).centerCrop().into(holder.mediaHolder);
        if(media.getType()==1)
            holder.videoIcon.setVisibility(View.VISIBLE);
        else holder.videoIcon.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return mediaList.size();
    }

    public class MediaViewHolder extends RecyclerView.ViewHolder{
        ImageView mediaHolder;
        ImageView videoIcon;
        public MediaViewHolder(View itemView) {
            super(itemView);
            mediaHolder = (ImageView)itemView.findViewById(R.id.media_holder);
            videoIcon = (ImageView)itemView.findViewById(R.id.video_icon);
        }
    }
}
