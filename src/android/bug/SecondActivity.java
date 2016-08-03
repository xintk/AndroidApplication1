/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package android.bug;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;

public class SecondActivity extends Activity {

        public static String JsonURL;
        private static ArrayList<HashMap<String, Object>> myBooks;
        private static final String TITLE = "title";
        private static final String DESCRIPTION = "description";
        public ListView listView;

        /** @param result */
        public void JSONURL(String result) {

                try {
                        JSONArray mainarray = new JSONArray(result);
                        //JSON выдает не более 12 вместо 50, я пытался это исправить, однакое не вышло
                        
                        for(int i=0; i<mainarray.length(); i++){
               
                            String images = mainarray.getJSONObject(i).getString("images");
                            JSONObject img = new JSONObject(images);
                            
                            if (mainarray.getJSONObject(i).getBoolean("animated")){
                                continue;
                            }
                            else if(img.getString("hidpi")==null){
                                break;
                            }
                            
                            HashMap<String, Object> hm;
                            hm = new HashMap<String, Object>();

                            hm.put(TITLE, mainarray.getJSONObject(i).getString("title").toString());
                            hm.put(DESCRIPTION, mainarray.getJSONObject(i).getString("description").toString());


                            myBooks.add(hm);
                            SimpleAdapter adapter = new SimpleAdapter(SecondActivity.this, myBooks, R.layout.list, 
                                    new String[] { TITLE, DESCRIPTION }, new int[] { R.id.text1, R.id.text2 });
                            
                            listView.setAdapter(adapter);
                            listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
                        }
                        
                        
                } catch (JSONException e) {
                        Log.e("log_tag", "Error parsing data " + e.toString());
            }
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {

                super.onCreate(savedInstanceState);
                setContentView(R.layout.url);
                listView = (ListView) findViewById(R.id.list);
                myBooks = new ArrayList<HashMap<String, Object>>();
                Bundle extras = getIntent().getExtras();
                String json = extras.getString(JsonURL);
                JSONURL(json);
        }
}
