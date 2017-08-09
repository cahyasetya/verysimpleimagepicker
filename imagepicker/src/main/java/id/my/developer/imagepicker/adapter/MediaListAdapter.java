package id.my.developer.imagepicker.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.media.Image;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import id.my.developer.imagepicker.R;
import id.my.developer.imagepicker.image_picker_activity.ImagePickerInterface;
import id.my.developer.imagepicker.models.Media;

/**
 * Created by light on 27/07/2017.
 */

public class MediaListAdapter extends RecyclerView.Adapter<MediaListAdapter.MediaViewHolder> {
    private Context context;
    private List<Media> mediaList;
    private int curerntlySeleted = -1;
    private MediaViewHolder currentlyViewHolder = null;
    private ImagePickerInterface.Callback callback;

    public MediaListAdapter(Context context, List<Media> mediaList) {
        this.context = context;
        this.mediaList = mediaList;
        if(context instanceof  ImagePickerInterface.Callback)
            this.callback = (ImagePickerInterface.Callback)context;
    }

    @Override
    public MediaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.media_item,parent,false);
        return new MediaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MediaViewHolder holder, final int position) {
        Media media = mediaList.get(position);
        if(media.getType()==2) {
            holder.videoIcon.setVisibility(View.GONE);
            Glide.with(context).load(media.getPath()).centerCrop().diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.mediaHolder);
            holder.videoDuration.setVisibility(View.GONE);
        }
        else if(media.getType()==1) {
            Glide.with(context).load(Uri.fromFile(new File(media.getPath()))).centerCrop().into(holder.mediaHolder);
            holder.videoIcon.setVisibility(View.VISIBLE);
            holder.videoDuration.setText(buildTime(media.getDuration()));
            holder.videoDuration.setVisibility(View.VISIBLE);
        }
        if(media.isSelected()){
            holder.imageStatus.setSelected(true);
        }else holder.imageStatus.setSelected(false);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(curerntlySeleted>-1) {
                    currentlyViewHolder.imageStatus.setSelected(false);
                    mediaList.get(curerntlySeleted).setSelected(false);
                }
                curerntlySeleted = position;
                currentlyViewHolder = holder;
                currentlyViewHolder.imageStatus.setSelected(true);
                mediaList.get(position).setSelected(true);
                callback.onMediaSelected();
            }
        });
    }

    private String buildTime(long milis){
        return String.format("%02d:%02d", TimeUnit.MILLISECONDS.toMinutes(milis),TimeUnit.MILLISECONDS.toSeconds(milis));
    }

    public String getSelectedMedia(){
        return mediaList.get(curerntlySeleted).getPath();
    }

    @Override
    public int getItemCount() {
        return mediaList.size();
    }

    public class MediaViewHolder extends RecyclerView.ViewHolder{
        ImageView mediaHolder;
        ImageView videoIcon;
        ImageView imageStatus;
        TextView videoDuration;
        public MediaViewHolder(View itemView) {
            super(itemView);
            videoDuration = (TextView)itemView.findViewById(R.id.video_duration);
            mediaHolder = (ImageView)itemView.findViewById(R.id.media_holder);
            videoIcon = (ImageView)itemView.findViewById(R.id.video_icon);
            imageStatus = (ImageView) itemView.findViewById(R.id.image_status);
            imageStatus.bringToFront();
        }
    }
}
