package de.hs_kl.gatav.flyingsaucerfull;

import android.app.Activity;
import android.content.Intent;
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
import android.widget.Button;
import android.widget.TextView;


public class GameOverActivity extends Activity {

    HighScoreManager db;

    private SpaceGLSurfaceView spaceGLSurfaceView;
    private WindowManager mWindowManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        db = new HighScoreManager(this);

        Intent i = getIntent();
        String scoreOut = i.getStringExtra("SCORE");

        int score = Integer.parseInt(scoreOut);
        Log.d("HSKL", score +"");
        db.insertScore(score);


        TextView scoreText = (TextView)findViewById(R.id.score);
        scoreText.setText(scoreOut);
        mWindowManager = (WindowManager)getSystemService(WINDOW_SERVICE);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        spaceGLSurfaceView = new SpaceGLSurfaceView(this);
        spaceGLSurfaceView.context=this;

        final Button buttonNew = findViewById(R.id.neustart);
        buttonNew.setOnClickListener(new View.OnClickListener() {
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
