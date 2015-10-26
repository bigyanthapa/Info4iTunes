package interviewtest.bigyan.com.interviewtest;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import interviewtest.bigyan.com.interviewtest.adapters.SearchAdapter;
import interviewtest.bigyan.com.interviewtest.model.DescriptionModel;
import interviewtest.bigyan.com.interviewtest.sqlite.SQLiteHandler;

public class MainActivity extends Activity {

    //create an obj for searchAdapter
    private SearchAdapter searchAdapter;

    private List<DescriptionModel> descriptionModelList;

    //create obj of DescriptionModel
    private DescriptionModel descriptionModel;

    //create obj ListView
    private ListView displayView;

    SQLiteHandler db;

    //Given String to test
   // final String URL = "https://itunes.apple.com/search?term=jack+johnson&limit=25";

    private String url = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Now Receive url name
        url= getIntent().getStringExtra("url");

        //create a custom ActionBar
        createCutomActionBarTitle();

        //mapping the widgets
        displayView = (ListView) findViewById(R.id.displayListView);

        descriptionModelList = new ArrayList<>();

        descriptionModel = new DescriptionModel();

        db = new SQLiteHandler(this);


        //If Network is available, get data from Network, else get data from database.
        if (isNetworkAvailable()) {
            db.deleteAll();
            getData();
        } else {
            //get from database
            descriptionModelList = db.getAllSearch();
        }


        searchAdapter = new SearchAdapter(descriptionModelList, this);
        displayView.setAdapter(searchAdapter);

        //navigate to the next screen when the item on the listview is clicked

        displayView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {

                Intent mIntent = new Intent(view.getContext(), DetailsView.class);
                mIntent.putExtra("obj", descriptionModelList.get(position));
                mIntent.putExtra("url",url);
                startActivityForResult(mIntent, 0);

            }

        });


    }

    // get data from the given url

    public void getData() {

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            JSONArray results = response.getJSONArray("results");

                            for (int i = 0; i < results.length(); i++) {
                                JSONObject itunesObject = results.getJSONObject(i);

                                descriptionModel = new DescriptionModel();
                                String title = itunesObject.getString("artistName");
                                String desc = itunesObject.getString("collectionCensoredName");
                                String imgUrl = itunesObject.getString("artworkUrl60");
                                String collectionName = itunesObject.getString("collectionName");
                                String collectionPrice = itunesObject.getString("collectionPrice");
                                String trackPrice = itunesObject.getString("trackPrice");

                                descriptionModel.setTitle(title);
                                descriptionModel.setDescription(desc);
                                descriptionModel.setImageSource(imgUrl);

                                descriptionModel.setCollectionName(collectionName);
                                descriptionModel.setTrackPrice(trackPrice);
                                descriptionModel.setCollectionPrice(collectionPrice);


                                descriptionModelList.add(descriptionModel);
                                db.addSearch(descriptionModel);

                                searchAdapter.notifyDataSetChanged();
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub

                    }
                });

        queue.add(jsObjRequest);
    }

   //Check if Network is available or not
    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    //Check the network status


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //exit if back is pressed in main activity
    @Override
    public void onBackPressed() {

        Intent mainIntent = new Intent(getApplicationContext(), MainActivityToSearch.class);
        startActivity(mainIntent);
        finish();
    }
    //custom action bar title

    private void createCutomActionBarTitle(){
        this.getActionBar().setDisplayShowCustomEnabled(true);
        this.getActionBar().setDisplayShowTitleEnabled(true);

        LayoutInflater inflator = LayoutInflater.from(this);
        View v = inflator.inflate(R.layout.custom_action_bar, null);

        //assign the view to the actionbar
        this.getActionBar().setCustomView(v);
    }
}
