package minaz.devcafe.devcafe;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String BASE_URL = "http://178.62.223.183/";
    public static String AUTH_TOKEN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Uri uri = Uri.parse(BASE_URL).buildUpon().appendPath("ideas/").build();

//        titleTextView = (TextView) findViewById(R.id.title);
//        descriptionTextView = (TextView) findViewById(R.id.description);


        DownloadIdeaListTask task = new DownloadIdeaListTask();
        String[] params = new String[1];
        params[0] = uri.toString();
        task.execute(params);
    }

    public List<Idea> parseIdeaList(String response) {
        Type listType = new TypeToken<List<Idea>>() {}.getType();
        return new Gson().fromJson(response, listType);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

//    @Override
//    public boolean onPrepareOptionsMenu(Menu menu) {
//        super.onPrepareOptionsMenu(menu);
//        if (!isLoggedIn()) {
//            menu.findItem(R.id.action_login).setVisible(false);
//        }
//        return true;
//    }

    private boolean isLoggedIn() {
        return AUTH_TOKEN.length() != 0;
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

        if (id == R.id.action_login) {
            Intent loginIntent = new Intent(this, LoginActivity.class);
            startActivity(loginIntent);
        }

        return super.onOptionsItemSelected(item);
    }

    private class DownloadIdeaListTask extends AsyncTask<String, Void, List<Idea>> {

        ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);

        @Override
        protected List<Idea> doInBackground(String... urls) {

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
                return parseIdeaList(sb.toString());

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
        protected void onPostExecute(List<Idea> result) {
            final ListView ideasListView = (ListView) findViewById(R.id.ideaListView);
            IdeaListAdapter adapter = new IdeaListAdapter(MainActivity.this, result);
            ideasListView.setAdapter(adapter);
            ideasListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(MainActivity.this, IdeaDetailActivity.class);
                    Idea idea = (Idea) parent.getAdapter().getItem(position);
                    intent.putExtra("ID", idea.id);
                    startActivity(intent);
                }
            });
            progressDialog.dismiss();
            super.onPostExecute(result);
        }
    }
}
