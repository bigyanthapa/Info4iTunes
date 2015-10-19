package interviewtest.bigyan.com.interviewtest.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import interviewtest.bigyan.com.interviewtest.model.DescriptionModel;

/**
 * Created by bigyanthapa on 10/16/15.
 */
public class SQLiteHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 3;

    // Database Name
    private static final String DATABASE_NAME = "interview.db";

    // search data table name
    private static final String TABLE_SEARCHDATA = "searchData";

    // searchData Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_ARTIST_NAME = "artistName";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_IMAGE_SOURCE = "imgSource";
    private static final String KEY_COLLECTIONNAME="collectionName";
    private static final String KEY_COLLECTIONPRICE="collectionPrice";
    private static final String KEY_TRACKPRICE="trackPrice";

    public SQLiteHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_SEARCH_TABLE = "CREATE TABLE " + TABLE_SEARCHDATA + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_ARTIST_NAME + " TEXT,"
                +KEY_IMAGE_SOURCE+" TEXT, "
                +KEY_COLLECTIONNAME+" TEXT, "
                +KEY_COLLECTIONPRICE+" TEXT, "
                +KEY_TRACKPRICE+" TEXT, "
                + KEY_DESCRIPTION + " TEXT" + ")";
        db.execSQL(CREATE_SEARCH_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SEARCHDATA);

        // Create tables again
        onCreate(db);
    }


    // Adding new search
    public void addSearch(DescriptionModel descriptionModel) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ARTIST_NAME, descriptionModel.getTitle());
        values.put(KEY_DESCRIPTION, descriptionModel.getDescription());
        values.put(KEY_IMAGE_SOURCE,descriptionModel.getImageSource());
        values.put(KEY_COLLECTIONNAME,descriptionModel.getCollectionName());
        values.put(KEY_COLLECTIONPRICE,descriptionModel.getCollectionPrice());
        values.put(KEY_TRACKPRICE,descriptionModel.getTrackPrice());


        // Inserting Row
        db.insert(TABLE_SEARCHDATA, null, values);
        db.close(); // Closing database connection
    }
   public List<DescriptionModel> getAllSearch()
    {
        List<DescriptionModel> searchList = new ArrayList<DescriptionModel>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_SEARCHDATA;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                DescriptionModel descriptionModel = new DescriptionModel();

                descriptionModel.setTitle(cursor.getString(cursor.getColumnIndex(KEY_ARTIST_NAME)));
                descriptionModel.setImageSource(cursor.getString(cursor.getColumnIndex(KEY_IMAGE_SOURCE)));
                descriptionModel.setDescription(cursor.getString(cursor.getColumnIndex(KEY_DESCRIPTION)));
                descriptionModel.setCollectionName(cursor.getString(cursor.getColumnIndex(KEY_COLLECTIONNAME)));
                descriptionModel.setCollectionPrice(cursor.getString(cursor.getColumnIndex(KEY_COLLECTIONPRICE)));
                descriptionModel.setTrackPrice(cursor.getString(cursor.getColumnIndex(KEY_TRACKPRICE)));
                // Adding descriptionModel to list
                searchList.add(descriptionModel);
            } while (cursor.moveToNext());
        }

        // return search list
        return searchList;
    }

    public void deleteAll()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + TABLE_SEARCHDATA);
       // db.execSQL("TRUNCATE table" + TABLE_SEARCHDATA);
        db.close();
    }

}