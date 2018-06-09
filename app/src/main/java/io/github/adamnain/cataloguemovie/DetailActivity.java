package io.github.adamnain.cataloguemovie;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.github.adamnain.cataloguemovie.model.Favorite;
import io.github.adamnain.cataloguemovie.provider.FavoriteProvider;

import static android.os.Build.ID;
import static io.github.adamnain.cataloguemovie.db.DatabaseContract.CONTENT_URI;
import static io.github.adamnain.cataloguemovie.db.DatabaseContract.FavoriteColumns.BACKDROP;
import static io.github.adamnain.cataloguemovie.db.DatabaseContract.FavoriteColumns.COVER;
import static io.github.adamnain.cataloguemovie.db.DatabaseContract.FavoriteColumns.OVERVIEW;
import static io.github.adamnain.cataloguemovie.db.DatabaseContract.FavoriteColumns.RELEASE;
import static io.github.adamnain.cataloguemovie.db.DatabaseContract.FavoriteColumns.TITLE;

public class DetailActivity extends AppCompatActivity {

    @BindView(R.id.img_backdrop_detail)
    ImageView imgBackdrop;

    @BindView(R.id.tv_title_detail)
    TextView tvTitle;

    @BindView(R.id.tv_release_detail)
    TextView tvRelease;

    @BindView(R.id.tv_overview_detail)
    TextView tvOverview;

    @BindView(R.id.iv_star)
    ImageView ivStar;

    private Favorite favorite;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ButterKnife.bind(this);
        getIntentData();
        backButton();

        favoriteCheck();

    }


    private void getIntentData(){
        String imgPath = "http://image.tmdb.org/t/p/w185"+getIntent().getStringExtra("backdrop");
        Glide.with(this)
                .load(imgPath)
                .into(imgBackdrop);
        tvTitle.setText(getIntent().getStringExtra("title"));
        tvRelease.setText(getIntent().getStringExtra("release"));
        tvOverview.setText(getIntent().getStringExtra("overview"));
    }

    //untuk enampilkan back button
    public void backButton(){
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getIntent().getStringExtra("title"));
    }

    //fungsi back ketika tombol back diklik
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent i = new Intent(getApplicationContext(), NavbarActivity.class);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.iv_star)
    public void submitFavorite(){
        String title = getIntent().getStringExtra("title");
        String cover = getIntent().getStringExtra("cover");
        String backdrop = getIntent().getStringExtra("backdrop");
        String release = getIntent().getStringExtra("release");
        String overview = getIntent().getStringExtra("overview");

        ContentValues values = new ContentValues();
        values.put(TITLE,title);
        values.put(COVER,cover);
        values.put(BACKDROP,backdrop);
        values.put(RELEASE,release);
        values.put(OVERVIEW,overview);

        getContentResolver().insert(CONTENT_URI,values);

        Toast.makeText(this, title, Toast.LENGTH_SHORT).show();
        setResult(101);
        finish();
    }

    private void favoriteCheck(){
//        FavoriteProvider favoriteProvider = new FavoriteProvider(getApplicationContext());
////        favoriteProvider.query(CONTENT_URI,)
//        String[] mSelectionArgs = {getIntent().getStringExtra("title")};
        String[] id = {ID};
        Cursor cursor = getContentResolver().query(
                CONTENT_URI,
                id,
                "title LIKE ?",
                new String[]{getIntent().getStringExtra("title")+"%"},
                null
        );

        if(cursor != null){
            Toast.makeText(this, id[0]+"sudah favorit", Toast.LENGTH_SHORT).show();
        }
    }

}
