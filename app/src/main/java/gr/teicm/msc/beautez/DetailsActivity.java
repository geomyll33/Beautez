package gr.teicm.msc.beautez;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;

import classes.DataStore;
import classes.ImageLoader;

public class DetailsActivity extends AppCompatActivity {

        TextView textViewTitle;
        TextView textViewProduct;
        TextView textViewGenre;
        Button buttonVisitWebsite;
        ImageView imageViewCover;
        ImageLoader imageLoader;

        HashMap<String, Object> cosmetic = null;

        private void findViews() {
            textViewTitle = (TextView)findViewById(R.id.cosmetic_details_title);
            textViewProduct = (TextView)findViewById(R.id.cosmetic_details_product);
            textViewGenre = (TextView)findViewById(R.id.cosmetic_details_genre);
            buttonVisitWebsite = (Button)findViewById(R.id.buttonVisitWebsite);


        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_details);

            //Animation when this Activity appears
            overridePendingTransition(R.anim.pull_in_from_right, R.anim.hold);

            findViews();

            Intent intent = getIntent();
            int cosmeticPosition = intent.getIntExtra(DataStore.KEY_POSITION, 0);

            cosmetic =  DataStore.Cosmetics.get(cosmeticPosition);
            String cosmeticTitle = (String)cosmetic.get(DataStore.KEY_TITLE);
            String cosmeticProduct = (String)cosmetic.get(DataStore.KEY_PRODUCT);
            String cosmeticGenreName = (String)cosmetic.get(DataStore.KEY_GENRENAME);
            textViewTitle.setText(cosmeticTitle);
            textViewProduct.setText(cosmeticProduct);
            textViewGenre.setText(cosmeticGenreName);
            imageViewCover = (ImageView)findViewById(R.id.imageViewCover);

            String cosmeticCoverUrl = (String)cosmetic.get(DataStore.KEY_IMAGEURL);
            imageLoader = new ImageLoader(this);
            imageLoader.DisplayImage(cosmeticCoverUrl, imageViewCover);


            buttonVisitWebsite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String cosmeticProductUrl = (String)cosmetic.get(DataStore.KEY_PRODUCTURL);
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(cosmeticProductUrl));
                    startActivity(browserIntent);
                }
            });
        }

        @Override
        protected void onPause() {
            overridePendingTransition(R.anim.hold, R.anim.push_out_to_right);
            super.onPause();
        }


    }

