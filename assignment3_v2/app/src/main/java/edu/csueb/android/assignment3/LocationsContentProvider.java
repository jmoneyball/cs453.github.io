package edu.csueb.android.assignment3;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.UriMatcher;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class LocationsContentProvider extends ContentProvider {
    //declarations
    LocationsDB locationsDB = new LocationsDB(getContext());
    public static final String contentProvider_NAME = "edu.csueb.android.assignment3.locations";
    public static final Uri contentProvider_URI = Uri.parse("content://" + contentProvider_NAME + "/locations");
    private static final UriMatcher uriMatcher;
    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(contentProvider_NAME, "locations", 1);
    }

    @Override
    public boolean onCreate() {
        locationsDB = new LocationsDB(getContext());
        return true;
    }

    // todo a callback method that is invoked when insert operation is request on this content provider
    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {
        //declarations
        long key = locationsDB.locationsDB_INSERT(values);

        //insert key to locationsDB if valid
        if (key > 0) uri = ContentUris.withAppendedId(contentProvider_URI, key);
        return uri;
    }

    // todo a callback method that is invoked when delete operation is request on this content provider
    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return locationsDB.locationsDB_DELETE();
    }

    // todo a callback method that is invoked by a default content URI
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        if (uriMatcher.match(uri) == 1) { return locationsDB.locationsDB_RETURN(); }
        else { return null; }
    }

    //auto-generated
    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) { return 0; }
    @Override
    public String getType(@NonNull Uri uri) { return null; }
}
