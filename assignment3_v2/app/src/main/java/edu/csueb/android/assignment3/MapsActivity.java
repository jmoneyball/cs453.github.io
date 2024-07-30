package edu.csueb.android.assignment3;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import edu.csueb.android.assignment3.databinding.ActivityMapsBinding;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, LoaderManager.LoaderCallbacks<Cursor> {
    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private final LatLng LOCATION_CITY = new LatLng(37.335480, -121.893028);
    private final LatLng LOCATION_UNI = new LatLng(37.335371, -121.881050);
    private final LatLng LOCATION_DEPT = new LatLng(37.333714, -121.881860);
    LocationsDB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);

        db = new LocationsDB(this);
        getSupportLoaderManager().initLoader(0, null, this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        //declarations
        mMap = googleMap;

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                // todo: Add marker to map
                mMap.addMarker(new MarkerOptions().position(latLng));

                // todo: store the latitude, longitude, and zoom to SQL
                ContentValues contentValues = new ContentValues();
                contentValues.put(LocationsDB.DB_LAT, latLng.latitude);
                contentValues.put(LocationsDB.DB_LON, latLng.longitude);
                contentValues.put(LocationsDB.DB_ZOOM, mMap.getCameraPosition().zoom);

                // todo: create an instance of LocationInsertTask
                LocationInsertTask locationInsertTask = new LocationInsertTask();
                locationInsertTask.execute(new ContentValues[]{contentValues});

                // todo: Display Marker is added to the Map "Toast" message
                Toast toast = Toast.makeText(MapsActivity.this.getApplicationContext(),
                        "Marker is added to the Map.", Toast.LENGTH_SHORT);
                toast.show();
            }
        });

        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                // todo: Remove all markers from map
                mMap.clear();

                // todo: Create an instance of LocationDeleteTask
                LocationDeleteTask locationDeleteTask = new LocationDeleteTask();

                // todo: deleting all rows from the database table
                locationDeleteTask.execute();

                // todo: All markers are removed "Toast" message
                Toast toast = Toast.makeText(getApplicationContext(), "All markers are removed.", Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }

    @SuppressLint("StaticFieldLeak")
    private class LocationInsertTask extends AsyncTask<ContentValues, Void, Void> {
        @Override
        protected Void doInBackground(ContentValues... contentValues) {
            db.locationsDB_INSERT(contentValues[0]);
            return null;
        }
    }

    private class LocationDeleteTask extends AsyncTask <Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            db.locationsDB_DELETE();
            return null;
        }
    }
    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        // TODO: URI to the content provider LocationsContentProvider
        Uri uri = LocationsContentProvider.contentProvider_URI;

        //TODO: Fetches all the rows from the locations table
        CursorLoader c = new CursorLoader(this, uri, null, null, null, null);
        return c;
    }

    @SuppressLint("Range")
    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> arg0, Cursor arg1) {
        int locationCount = 0;
        double lat = 0;
        double lon = 0;
        float zoom = 0;
        LatLng latLng = null;

//        //number of locations available in the DB
        locationCount = arg1.getCount();
//        //move current record pointer to first row of DB
        arg1.moveToFirst();

        //add elements to the database
        for (int i = 0; i < locationCount; i++) {
            lat = arg1.getInt(arg1.getColumnIndex(LocationsDB.DB_LAT));
            lon = arg1.getInt(arg1.getColumnIndex(LocationsDB.DB_LON));
            zoom = Float.parseFloat(arg1.getString(arg1.getColumnIndex(LocationsDB.DB_ZOOM)));
            latLng = new LatLng(lat, lon);
            mMap.addMarker(new MarkerOptions().position(latLng));
            arg1.moveToNext();
        }
        //if multiple positions, set view at last position created
        if (locationCount > 0) {
            CameraUpdate update = CameraUpdateFactory.newLatLngZoom(latLng, zoom);
            mMap.animateCamera(update);
        }
    }

    //click handlers
    public void onClick_CS (View view){
        mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(LOCATION_DEPT, 18);
        mMap.animateCamera(update);
    }
    public void onClick_Univ (View view){
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(LOCATION_UNI, 14);
        mMap.animateCamera(update);
    }
    public void onClick_City (View view){
        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(LOCATION_CITY, 10);
        mMap.animateCamera(update);
    }

    //automatically created
    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) { }
}