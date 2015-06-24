package minaz.devcafe.devcafe;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    static String BASE_URL = "http://178.62.223.183/";
    TextView titleTextView;
    TextView descriptionTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Uri uri = Uri.parse(BASE_URL).buildUpon().appendPath("ideas").appendPath("1/").build();

        titleTextView = (TextView) findViewById(R.id.title);
        descriptionTextView = (TextView) findViewById(R.id.description);

        DownloadWebpageTask task = new DownloadWebpageTask();
        String[] params = new String[1];
        params[0] = uri.toString();
        task.execute(params);

//        titleTextView.setText(readIdea.title);
//        try {
//            URL url = new URL(uri.toString());
//            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//            InputStream stream = connection.getInputStream();
//            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
//            Idea readIdea = parse(reader.toString());
//
//            titleTextView = (TextView) findViewById(R.id.title);
//            titleTextView.setText(readIdea.title);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    public Idea parse(String response) {
        return new Gson().fromJson(response, Idea.class);
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

    private class DownloadWebpageTask extends AsyncTask<String, Void, Idea> {
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
                    sb.append(line + "\n");
                }
                reader.close();
                System.out.println(sb.toString());
                Idea readIdea = parse(sb.toString());
                return readIdea;

            } catch (IOException e) {
                e.printStackTrace();
//                return "Unable to retrieve web page. URL may be invalid.";
                return null;
            }
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(Idea result) {
            titleTextView.setText(result.title);
            descriptionTextView.setText(result.description);
        }
    }
}
