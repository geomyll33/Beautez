package gr.teicm.msc.beautez;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import classes.DataStore;
import classes.LazyAdapter;

public class ListActivity extends AppCompatActivity {
    TextView textViewInfo;
    ListView listViewCosmetics;
    private void findViews(){
        textViewInfo = (TextView)findViewById(R.id.textViewInfo);
        listViewCosmetics = (ListView)findViewById(R.id.listViewCosmetics); }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        //Animation when this Activity appears
        overridePendingTransition(R.anim.pull_in_from_right, R.anim.hold);
        //get user filters from Intent
        Intent intent = getIntent();
        String filterProduct = intent.getStringExtra("PRODUCT");
        String filterTitle = intent.getStringExtra("TITLE");
        int filterGenreId = intent.getIntExtra("GENREID", 0);
        findViews();
        //show user filters for information
        String message = String.format("Product: %s\nTitle: %s\nGenreId: %d", filterProduct, filterTitle, filterGenreId);
        textViewInfo.setText(message);
        DataStore.LoadCosmetics(filterProduct, filterTitle, filterGenreId);
            //COMPLEX OBJECT BINDING
       // LazyAdapter cosmeticsAdapter = new LazyAdapter(this, DataStore.Cosmetics);
        ListAdapter cosmeticsAdapter = new SimpleAdapter(
        this,
                DataStore.Cosmetics,
                R.layout.list_item,
                new String[] {DataStore.KEY_TITLE, DataStore.KEY_PRODUCT, DataStore.KEY_GENRENAME},
                new int[] {R.id.cosmetic_item_title, R.id.cosmetic_item_product, R.id.cosmetic_item_genre}
        );
        listViewCosmetics.setAdapter(cosmeticsAdapter);

        listViewCosmetics.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent detailsIntent = new Intent(ListActivity.this, DetailsActivity.class);
                detailsIntent.putExtra(DataStore.KEY_POSITION, position);
                startActivity(detailsIntent);
            }
        });

    }
    @Override
    protected void onPause() {
        overridePendingTransition(R.anim.hold, R.anim.push_out_to_right);
        super.onPause();
    }

}
