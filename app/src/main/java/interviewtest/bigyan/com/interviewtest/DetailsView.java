package interviewtest.bigyan.com.interviewtest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import interviewtest.bigyan.com.interviewtest.model.DescriptionModel;

public class DetailsView extends Activity {


    private ImageButton backImageButton;

    private TextView collectionTitleTextView;
    private TextView collectionNameTextView;
    private TextView collectionPriceTextView;
    private TextView trackPriceTextView;

    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_view);


        backImageButton = (ImageButton) findViewById(R.id.backImageButton);

        Intent intent = getIntent();
        DescriptionModel descriptionModel = (DescriptionModel) intent.getSerializableExtra("obj");

        url = intent.getStringExtra("url");
        collectionTitleTextView = (TextView) findViewById(R.id.collectionTitleTextView);
        collectionNameTextView = (TextView) findViewById(R.id.collectionNameTextView);
        collectionPriceTextView = (TextView) findViewById(R.id.collectionPriceTextView);
        trackPriceTextView = (TextView) findViewById(R.id.trackPriceTextView);


        collectionTitleTextView.setText(descriptionModel.getTitle());
        collectionNameTextView.setText(descriptionModel.getCollectionName());
        collectionPriceTextView.setText(descriptionModel.getCollectionPrice());
        trackPriceTextView.setText(descriptionModel.getTrackPrice());


        backImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainIntent = new Intent(getApplicationContext(), MainActivity.class);
                mainIntent.putExtra("url",url);
                startActivity(mainIntent);
                finish();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_details_view, menu);
        return true;
    }

    //Just allow my return button to take the application to the previous activity.
    @Override
    public void onBackPressed() {
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
}
