package minaz.devcafe.devcafe;

import android.app.ProgressDialog;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class IdeaDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_idea_detail);

        int ideaID = this.getIntent().getIntExtra("ID", -1);
        String appendedPath = String.valueOf(ideaID).concat("/");

        if (ideaID == -1) {
            Toast.makeText(this, "Invalid ID", Toast.LENGTH_SHORT).show();
            this.finish();
        }

        Uri uri = Uri.parse(MainActivity.BASE_URL)
                .buildUpon()
                .appendPath("ideas").appendPath(appendedPath)
                .build();

        DownloadIdeaDetailTask task = new DownloadIdeaDetailTask();
        String[] params = new String[1];
        params[0] = uri.toString();
        task.execute(params);
    }

    public Idea parseIdeaDetail(String response) {
        return new Gson().fromJson(response, Idea.class);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_idea_detail, menu);
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

    private class DownloadIdeaDetailTask extends AsyncTask<String, Void, Idea> {

        ProgressDialog progressDialog = new ProgressDialog(IdeaDetailActivity.this);

        @Override
        protected Idea doInBackground(String... urls) {

            // params comes from the execute() call: params[0] is the url.
            try {
                URL url = new URL(urls[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                InputStream stream = connection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line).append("\n");
                }
                reader.close();
                System.out.println(sb.toString());
                return parseIdeaDetail(sb.toString());

            } catch (IOException e) {
                e.printStackTrace();
//                return "Unable to retrieve web page. URL may be invalid.";
                return null;
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            try {
                progressDialog.setMessage("Loading...");
                progressDialog.show();
//                progressDialog.show(MainActivity.this, "Loading...", "Please Wait...");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(Idea result) {
            TextView ideaTitle = (TextView) findViewById(R.id.idea_detail_ideaTitle_textview);
            TextView ideaDescription = (TextView) findViewById(R.id.idea_detail_ideaDescription_textview);
            TextView ideaOwner = (TextView) findViewById(R.id.idea_detail_ideaOwner_textview);
            ideaTitle.setText(result.title);
            ideaDescription.setText(result.description);
            ideaOwner.setText("by: " + result.owner.username);
            progressDialog.dismiss();
            super.onPostExecute(result);
        }
    }
}
