package tech.damisa.developerlist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static String TAG = MainActivity.class.getSimpleName();

    private ListView mListView;
    private ProgressBar mProgressBar;

    private ListViewAdapter mListAdapter;
    private ArrayList<ListItem> mListData;
    private String FEED_URL = "https://api.github.com/search/users?q=location:lagos+language:java";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mListView = (ListView) findViewById(R.id.listview);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);

        //Initialize with empty data
        mListData = new ArrayList<>();
        mListAdapter = new ListViewAdapter(this, R.layout.listview_item, mListData);
        mListView.setAdapter(mListAdapter);

        ////fetch the products
        ///Start download
        mProgressBar.setVisibility(View.VISIBLE);
        FetchDevelopers();

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                //Get item at position
                ListItem item = (ListItem) parent.getItemAtPosition(position);

                Intent intent = new Intent(MainActivity.this, Personal.class);

                //Pass the image title and url to Personal DetailsActivity
                intent.putExtra("username", item.getUsername()).
                        putExtra("image", item.getImage()).
                        putExtra("pageurl",item.getPageurl()).
                        putExtra("githuburl",item.getGithuburl());

                //Start details activity
                startActivity(intent);
            }
        });

    }

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

    public void FetchDevelopers() {

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, FEED_URL, new

                Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        ///hide loader
                        mProgressBar.setVisibility(View.GONE);

                        try {
                            JSONArray obj = response.getJSONArray("items");

                            ////for the array
                            ListItem item;

                            for (int i = 0; i < obj.length(); i++) {

                                JSONObject jsonObject = obj.getJSONObject(i);

                                String username = jsonObject.getString("login");
                                String imageurl = jsonObject.getString("avatar_url");
                                String score = jsonObject.getString("score");
                                String pageurl = jsonObject.getString("url");
                                String githuburl = jsonObject.getString("html_url");
                                String type = jsonObject.getString("type");

                                item = new ListItem();
                                item.setUsername(username);
                                item.setImage(imageurl);
                                item.setScore(score);
                                item.setType(type);
                                item.setPageurl(pageurl);
                                item.setGithuburl(githuburl);

                                mListData.add(item);
                            }

                            mListAdapter.setGridData(mListData);

                        }
                        catch (JSONException e) {

                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(),
                                    "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                        ///hide loader
                        mProgressBar.setVisibility(View.GONE);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();

                ///hide loader
                mProgressBar.setVisibility(View.GONE);
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(req);
    }
}
