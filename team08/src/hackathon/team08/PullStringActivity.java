package hackathon.team08;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.gesture.Gesture;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.*;
import android.view.View.OnTouchListener;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.ScaleAnimation;
import android.widget.AbsoluteLayout.LayoutParams;

import java.io.IOException;
import java.util.List;

public class PullStringActivity extends Activity
        implements GestureDetector.OnGestureListener, MediaPlayer.OnCompletionListener {

    private RetortDB retortDB;
    private GestureDetector gestureDetector;
    Button btnClean;
    Button btnAbusive;
    Button btnVulgar;
    boolean clipPlaying = false;
    ImageView img = null;
    int status = 0;
    String subject;
    String style;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        retortDB = new RetortDB(this);
        gestureDetector = new GestureDetector(this);
        setContentView(R.layout.main);
       }

    @Override
    protected void onStart() {
        super.onStart();
        retortDB.open();

        //define spinner
        final Spinner spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter spinnerArrayAdapter = new ArrayAdapter(this,
                android.R.layout.simple_spinner_dropdown_item,
                new String[]{"Case of the Mondays", "Cheer Them Up", "Pat on the Back"});
        spinner.setAdapter(spinnerArrayAdapter);
        subject = spinner.getSelectedItem().toString();

        //get spinner selection
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
             public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                subject = spinner.getSelectedItem().toString();
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
                //To change body of implemented methods use File | Settings | File Templates.
            }
        });

        //define ImageView
        img = (ImageView) findViewById(R.id.pullStringImage);

        //set animation specs
        final TranslateAnimation anim = new TranslateAnimation(0, 0, 100, 0);
        anim.setInterpolator(new LinearInterpolator());
        anim.setRepeatCount(0);
        anim.setDuration(3000);

        //image listener pull down                            )
        img.setOnTouchListener(new View.OnTouchListener() {
             public boolean onTouch(View v, MotionEvent event) {

               if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    img.startAnimation(anim);
                    if (style==null)
                        style="01";
                   List<Retort> retortList = retortDB.getRetorts(subject, style);
                   playClip(retortList.get(0).getRetort());
                   return true;
                }
                return false;
            }
        });



        btnClean = (Button) findViewById(R.id.button01);
        btnClean.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                setButtonState("01");
                style = "01";
            }
        });

        btnVulgar = (Button) findViewById(R.id.button02);
        btnVulgar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                setButtonState("02");
                style = "02";
            }
        });

        btnAbusive = (Button) findViewById(R.id.button03);
        btnAbusive.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                setButtonState("03");
                style = "03";
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }

    public boolean onDown(MotionEvent motionEvent) {
        System.out.println("GestureDetector.onDown");
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void onShowPress(MotionEvent motionEvent) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void onLongPress(MotionEvent motionEvent) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void onCompletion(MediaPlayer mediaPlayer) {
        mediaPlayer.stop();
        clipPlaying = false;
    }

    private void playClip(String clipName) {
        try {
            if (!clipPlaying) {
                clipPlaying = true;
                AssetFileDescriptor afd = getAssets().openFd(clipName);
                MediaPlayer mplayer = new MediaPlayer();
                mplayer.setOnCompletionListener(this);
                mplayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
                mplayer.prepare();
                mplayer.start();
            }

        } catch (IOException e) {
            e.printStackTrace(System.out);
        }
    }

    public void setButtonState(String btnState) {
        Button btn01 = (Button) findViewById(R.id.button01);
        Button btn02 = (Button) findViewById(R.id.button02);
        Button btn03 = (Button) findViewById(R.id.button03);
        if (btnState == "01") {
            btn01.setBackgroundResource(R.drawable.button_u);
            btn02.setBackgroundResource(R.drawable.button);
            btn03.setBackgroundResource(R.drawable.button);
        } else if (btnState == null) {
            btn01.setBackgroundResource(R.drawable.button_u);
            btn02.setBackgroundResource(R.drawable.button);
            btn03.setBackgroundResource(R.drawable.button);
        } else if (btnState == "02") {
            btn01.setBackgroundResource(R.drawable.button);
            btn02.setBackgroundResource(R.drawable.button_u);
            btn03.setBackgroundResource(R.drawable.button);
        } else if (btnState == "03") {
            btn01.setBackgroundResource(R.drawable.button);
            btn02.setBackgroundResource(R.drawable.button);
            btn03.setBackgroundResource(R.drawable.button_u);
        }
    }
}
