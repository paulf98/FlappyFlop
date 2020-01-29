package de.hs_kl.gatav.flyingsaucerfull;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Surface;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    private SpaceGLSurfaceView spaceGLSurfaceView;
    private WindowManager mWindowManager;

    HighScoreManager db;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new HighScoreManager(this);
        listView = (ListView) findViewById(R.id.highscores);

        Context ctx = this;

        int itemRes = android.R.layout.simple_list_item_1;
        List<String> data = new ArrayList<>();
        Cursor cursor = db.selectAllScores();
        //Log.d("HSKL", DatabaseUtils.dumpCursorToString(cursor));


        if ( cursor != null){
            do{
                int score = cursor.getInt(0);
                String scoreStr = score + "";
                data.add(scoreStr);
                Log.d("HSKL", scoreStr);
            }while (cursor.moveToNext());
            cursor.close();
        }

        ArrayAdapter adapter = new ArrayAdapter(ctx, itemRes,data);
        listView.setAdapter(adapter);

        mWindowManager = (WindowManager)getSystemService(WINDOW_SERVICE);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        spaceGLSurfaceView = new SpaceGLSurfaceView(this);
        spaceGLSurfaceView.context=this;

        final Button buttonStart = findViewById(R.id.button_start);
        buttonStart.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setContentView(spaceGLSurfaceView);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();

    }


}