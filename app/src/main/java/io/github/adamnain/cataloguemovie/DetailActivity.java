package io.github.adamnain.cataloguemovie;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class DetailActivity extends AppCompatActivity {
    ImageView imgBackdrop;
    TextView tvTitle, tvVote, tvRelease, tvOverview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        init();
        getIntentData();
        backButton();
    }

    private void init(){
        imgBackdrop = findViewById(R.id.img_backdrop_detail);
        tvTitle = findViewById(R.id.tv_title_detail);
        tvRelease = findViewById(R.id.tv_release_detail);
        tvOverview = findViewById(R.id.tv_overview_detail);
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


}
