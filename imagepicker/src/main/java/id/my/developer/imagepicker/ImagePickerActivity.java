package id.my.developer.imagepicker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import id.my.developer.imagepicker.adapter.MediaListAdapter;
import id.my.developer.imagepicker.models.Media;

public class ImagePickerActivity extends AppCompatActivity implements ImagePickerInterface.Callback{

    private RecyclerView mediaRecyclerView;

    private ImagePickerPresenter presenter;
    private MediaListAdapter adapter;
    private RecyclerView.LayoutManager lm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_picker);

        presenter = new ImagePickerPresenter(this);

        init();
    }

    private void init(){
        mediaRecyclerView = (RecyclerView)findViewById(R.id.media_recyclerview);
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.loadAllMedia();
    }

    @Override
    public void onPicturesLoaded(List<Media> mediaList) {
        lm = new GridLayoutManager(this,3, LinearLayoutManager.VERTICAL,false);
        mediaRecyclerView.setLayoutManager(lm);
        adapter = new MediaListAdapter(this,mediaList);
        mediaRecyclerView.setAdapter(adapter);
    }
}
