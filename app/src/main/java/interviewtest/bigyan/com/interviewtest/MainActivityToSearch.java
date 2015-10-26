package interviewtest.bigyan.com.interviewtest;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

public class MainActivityToSearch extends Activity {

    private RelativeLayout relativeLayout;

    private Button okButton;
    private EditText artistFirstEditText;
    private EditText artistLastEditText;
    private String artistFirstName;
    private String artistLastName;


    private String URL1 = "https://itunes.apple.com/search?term=";
    private String URL2 = "";
    private String URL3 = "&limit=25";
    private String finalUrl = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity_to_search);

        relativeLayout = (RelativeLayout)findViewById(R.id.mainRelativeLayout);

        artistFirstEditText = (EditText) findViewById(R.id.artistFirstNameEditText);
        artistLastEditText = (EditText) findViewById(R.id.artistLastNameEditText);
        okButton = (Button) findViewById(R.id.okButton);

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //get the artist name and pass to second activity
                artistFirstName = artistFirstEditText.getText().toString();
                artistLastName = artistLastEditText.getText().toString();


                //create url2
                URL2 = artistFirstName + "+" + artistLastName;
                finalUrl = URL1 + URL2 + URL3;

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("url", finalUrl);
                startActivity(intent);
            }


        });

        //if touched outside the editText, hide the keyboard
        relativeLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                //call method to hide keyboard
                hideKeyboard(view);
                return false;
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_activity_to_search, menu);
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

    //Method definition to hide keyboard
    protected void hideKeyboard(View view)
    {
        InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        in.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }
}
