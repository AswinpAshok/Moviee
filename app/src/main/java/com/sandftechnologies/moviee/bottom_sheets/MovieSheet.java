package com.sandftechnologies.moviee.bottom_sheets;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.sandftechnologies.moviee.R;

/**
 * Created by pc4 on 12/29/2017.
 */

public class MovieSheet extends BottomSheetDialogFragment {

    private SimpleDraweeView poster;
    private TextView title,overview;
    private String posterPath,Title,Overview;


    @SuppressLint("RestrictedApi")
    @Override
    public void setupDialog(Dialog dialog, int style) {
        super.setupDialog(dialog, style);
        View contentView = View.inflate(getContext(), R.layout.movie_sheet_layout, null);
        dialog.setContentView(contentView);

        poster=contentView.findViewById(R.id.poster);
        title=contentView.findViewById(R.id.movieName);
        overview=contentView.findViewById(R.id.overview);


        Uri uri=Uri.parse(posterPath);
//        Log.d("#######", "setData: "+posterPath);
        poster.setImageURI(uri);
        title.setText(Title);
        overview.setText(Overview);

    }

    public void setData(String posterPath,String Title, String Overview){
        this.posterPath=posterPath;
        this.Overview=Overview;
        this.Title=Title;
    }


}
