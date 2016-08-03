package android.bug;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.EditText;
import java.io.BufferedReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends Activity {

        private ProgressDialog dialog;
        private InputStream is;
        SecondActivity url;

        @Override
        public void onCreate(Bundle savedInstanceState) {

                super.onCreate(savedInstanceState);
                setContentView(R.layout.list);
                new RequestTask().execute();
        }

        class RequestTask extends AsyncTask<String, String, String> {

                @Override
                protected String doInBackground(String... params) {

                        try {
                                URL url = new URL("https://api.dribbble.com/v1/shots?access_token=14979ba18ae1b95b9695a753e69c520f7475bef8e2614166df5e750853799713");
 
                                HttpURLConnection Connect = (HttpURLConnection) url.openConnection();
                                Connect.setRequestMethod("GET");
                                Connect.connect();

                                InputStream inputStream = Connect.getInputStream();
                                StringBuffer buffer = new StringBuffer();

                                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

                                String line;
                                while ((line = reader.readLine()) != null) {
                                    buffer.append(line);
                                }

                                String resultJson = buffer.toString();
                                
                                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                                intent.putExtra(SecondActivity.JsonURL, resultJson);
                                startActivity(intent);
                        } catch (Exception e) {
                                System.out.println("Exp=" + e);
                        }
                        return null;
                }

                @Override
                protected void onPostExecute(String result) {

                        dialog.dismiss();
                        super.onPostExecute(result);
                }

                @Override
                protected void onPreExecute() {

                        dialog = new ProgressDialog(MainActivity.this);
                        dialog.setMessage("Загружаюсь...");
                        dialog.setIndeterminate(true);
                        dialog.setCancelable(true);
                        dialog.show();
                        super.onPreExecute();
                }
        }
}
