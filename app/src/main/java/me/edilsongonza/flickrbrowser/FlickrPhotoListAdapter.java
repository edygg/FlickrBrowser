package me.edilsongonza.flickrbrowser;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.List;

/**
 * Created by Edilson Gonzalez on 01/06/2015.
 */
public class FlickrPhotoListAdapter extends RecyclerView.Adapter<FlickrPhotoListAdapter.FlickrPhotoViewHolder> {

    public class FlickrPhotoViewHolder extends RecyclerView.ViewHolder {
        private NetworkImageView photo;
        private TextView title;
        private TextView author;

        public FlickrPhotoViewHolder(final View itemView) {
            super(itemView);
            photo = (NetworkImageView) itemView.findViewById(R.id.photo);
            title = (TextView) itemView.findViewById(R.id.title);
            author = (TextView) itemView.findViewById(R.id.author);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String tmpDirFilePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/FlickBrowser";
                    File dir = new File(tmpDirFilePath);
                    if (!dir.exists())
                        dir.mkdirs();
                    File tmpFile = new File(dir, title.getText() + Long.toString((new Date()).getTime()));
                    FileOutputStream out;
                    try {
                        out = new FileOutputStream(tmpFile);
                        Bitmap image = ((BitmapDrawable) photo.getDrawable()).getBitmap();
                        image.compress(Bitmap.CompressFormat.PNG, 100, out);
                        out.flush();
                        out.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Uri uri = Uri.fromFile(tmpFile);
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_SEND);
                    intent.setType("image/*");

                    intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "");
                    intent.putExtra(android.content.Intent.EXTRA_TEXT, "");
                    intent.putExtra(Intent.EXTRA_STREAM, uri);
                    itemView.getContext().startActivity(Intent.createChooser(intent, "Share Image"));
                }
            });
        }

        public NetworkImageView getPhoto() {
            return photo;
        }

        public TextView getTitle() {
            return title;
        }

        public TextView getAuthor() {
            return author;
        }
    }

    private List<FlickrPhotoItem> photos;

    public FlickrPhotoListAdapter(List<FlickrPhotoItem> photos) {
        this.photos = photos;
    }

    @Override
    public FlickrPhotoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.photo_item_layout, parent, false);
        //View onClickListener
        FlickrPhotoViewHolder viewHolder = new FlickrPhotoViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(FlickrPhotoViewHolder holder, int position) {
        FlickrPhotoItem currentPhoto = photos.get(position);

        if (holder != null) {
            holder.getPhoto().setImageUrl(currentPhoto.getMedia().getLinkToMedia(), AppController.getIntance().getImageLoader());
            holder.getTitle().setText(currentPhoto.getTitle());
            holder.getAuthor().setText(currentPhoto.getAuthor());
        }
    }

    @Override
    public int getItemCount() {
        return this.photos.size();
    }

    public void clear() {
        photos.clear();
        notifyDataSetChanged();
    }
}
