package com.example.kiptoomagutt.testkeen;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class MainActivity extends Activity {
    private static String TAG = "MainActivity";
    private static int MAX_ACTIVITY_LAUNCHES = 30;
    private static int OTHER_ACTIVITY_LAUNCHES = 0;
    private static final int START_OTHER_ACTIVITY_REQUEST = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = (Button) findViewById(R.id.button);
        final EditText text = (EditText) findViewById(R.id.launch_editor);
        text.setText(Integer.toString(MAX_ACTIVITY_LAUNCHES));

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OTHER_ACTIVITY_LAUNCHES = 1;
                String runText = text.getText().toString();
                if (runText != null && !TextUtils.isEmpty(runText)) {
                    MAX_ACTIVITY_LAUNCHES = Integer.parseInt(runText);;
                }
                Log.d(TAG, "MainActivity max launches: " + MAX_ACTIVITY_LAUNCHES);
                Intent intent = new Intent(getApplicationContext(), OtherActivity.class);
                startActivityForResult(intent, START_OTHER_ACTIVITY_REQUEST);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "onactivityresult");
        if (requestCode == START_OTHER_ACTIVITY_REQUEST) {
            if (OTHER_ACTIVITY_LAUNCHES < MAX_ACTIVITY_LAUNCHES) {
                Log.d(TAG, "onactivityresult 2");
                OTHER_ACTIVITY_LAUNCHES++;
                Intent intent = new Intent(getApplicationContext(), OtherActivity.class);
                startActivityForResult(intent, START_OTHER_ACTIVITY_REQUEST);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
