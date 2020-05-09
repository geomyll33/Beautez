package gr.teicm.msc.beautez;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Notification;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import classes.DataStore;


public class MainActivity extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST_CODE = 1001;
    public static String[] RequiredPermissions = {
            Manifest.permission.INTERNET,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
    };

    private EditText textProduct;
    private EditText textTitle;
    private Spinner spinnerGenre;
    private Button buttonSearch;

    @RequiresApi(Build.VERSION_CODES.M)
    public boolean checkPermissions() {
        for (String permission : RequiredPermissions) {
            if (ActivityCompat.checkSelfPermission(this, permission) !=
                    PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[]
            permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CODE: {
                // If request is cancelled, the result arrays are empty.
                boolean permissionGranted = false;
                if (grantResults.length > 0) {
                    permissionGranted = true;
                    for (int grantResult : grantResults) {
                        if (grantResult != PackageManager.PERMISSION_GRANTED) {
                            permissionGranted = false;
                            break;
                        }
                    }
                }
                if (permissionGranted) {
                    //possibly show a message to the user
                }
            }
            break;
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DataStore.Init(getApplicationContext());
        textProduct = (EditText)findViewById(R.id.editTextProduct);
        textTitle = (EditText)findViewById(R.id.editTextTiltle);
        spinnerGenre = (Spinner) findViewById(R.id.spinnerGenre);
        buttonSearch = (Button)findViewById(R.id.buttonSearch);
        ArrayAdapter<CharSequence> genreAdapter = ArrayAdapter.createFromResource(
                        this,
                        R.array.cosmetics_genre,
                        android.R.layout.simple_spinner_item
                );
        genreAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGenre.setAdapter(genreAdapter);

        buttonSearch.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //
                // Code will run on Button Click
                //
                String filterProduct = textProduct.getText().toString();
                String filterTitle = textTitle.getText().toString();
                int filterGenreId = spinnerGenre.getSelectedItemPosition();
                Intent intent = new Intent(MainActivity.this, ListActivity.class);
                intent.putExtra("PRODUCT", filterProduct);
                intent.putExtra("TITLE", filterTitle);
                intent.putExtra("GENREID", filterGenreId);
                startActivity(intent);
            }
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!checkPermissions()){
                requestPermissions(RequiredPermissions, MY_PERMISSIONS_REQUEST_CODE);
            }
        }
    };
    @Override
    public boolean onCreateOptionsMenu (Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_exit:
                finish();
                return true;
            case R.id.menu_settings:
                Toast.makeText(this, "Under construction", Toast.LENGTH_LONG).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }}
