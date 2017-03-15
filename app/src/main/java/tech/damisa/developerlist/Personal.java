package tech.damisa.developerlist;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class Personal extends AppCompatActivity {

    private static String TAG =Personal.class.getSimpleName();

    TextView Username,Location,Bio,Page_url,Repos,Follower,Following;
    private String Page_link;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        ///find the texviews
        Username=(TextView)findViewById(R.id.username_pg);
        Location=(TextView)findViewById(R.id.location_pg);
        Bio=(TextView)findViewById(R.id.bio);
        Repos=(TextView)findViewById(R.id.repos);
        Follower=(TextView)findViewById(R.id.follow);
        Following=(TextView)findViewById(R.id.following);
        Page_url=(TextView)findViewById(R.id.profile);


        ////when view profile is cliked
        Page_url.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent= new Intent(Intent.ACTION_VIEW, Uri.parse(Page_link));
                startActivity(intent);

            }
        });

        //retrieves the thumbnail data
        Bundle bundle = getIntent().getExtras();

        String username = bundle.getString("username");
        String image = bundle.getString("image");
        String pageurl= bundle.getString("pageurl");
        String githuburl = bundle.getString("githuburl");

        ///the gitur
        Page_link=githuburl;

        ////set variables
        Username.setText(username);

        //Set image url
        imageView = (ImageView) findViewById(R.id.avatar_pg);

        Picasso.with(this)
                .load(image)
                .placeholder(R.drawable.avatar)
                .into(imageView);

        ////download other details
        FetchDevelopersDetails(pageurl);

    }

    public void FetchDevelopersDetails(String Url) {

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET,Url, new

                Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                                ////get user new details
                                String fullname = response.getString("name");
                                String location = response.getString("location");
                                String bio = response.getString("bio");
                                String respo = response.getString("public_repos");
                                String follower = response.getString("followers");
                                String following = response.getString("following");

                            ////set the data
                            Username.setText(fullname);
                            Location.setText(location);
                            Bio.setText(bio);
                            Repos.setText(respo);
                            Follower.setText(follower);
                            Following.setText(following);

                        }
                        catch (JSONException e) {

                            e.printStackTrace();
                           /// Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
               // Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(req);
    }

}
