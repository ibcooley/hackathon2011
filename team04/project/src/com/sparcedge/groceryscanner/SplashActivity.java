package com.sparcedge.groceryscanner;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

public class SplashActivity extends Activity {
	
	static final int MSG_TYPE_KILL_ME = 1;
	static final int MSG_TYPE_START_FIRE = 2;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Setup the window
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.splash);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        // Set result CANCELED incase the user backs out
        setResult(Activity.RESULT_CANCELED);   
        
        android.os.Message to2 = Message.obtain();   	    
		to2.what = MSG_TYPE_KILL_ME;
   	    handler.sendMessageDelayed(to2, 5000);
   	    
        ImageView image = (ImageView) findViewById(R.id.hand);
        image.setVisibility(View.INVISIBLE);
    	ImageView fire = (ImageView) findViewById(R.id.fire);
	    fire.setVisibility(View.INVISIBLE);
        
   	    // start animation in 100 ms (cant start in onCreate)
   	    android.os.Message to3 = Message.obtain();
   	    to3.what = MSG_TYPE_START_FIRE;
   	    handler.sendMessageDelayed(to3, 100);
        
    }
    
    public void startFire() {
    	ImageView fire = (ImageView) findViewById(R.id.fire);
        if(fire != null) {
        	    fire.setVisibility(View.VISIBLE);
                fire.setBackgroundResource(R.drawable.fireanim);
                AnimationDrawable frameAnimation = (AnimationDrawable) fire.getBackground();
                frameAnimation.start();
        }
        ImageView image = (ImageView) findViewById(R.id.hand);
        image.setVisibility(View.VISIBLE);
        Animation animation = new TranslateAnimation(0,0,400,125);
        animation.setDuration(3000);
        image.startAnimation(animation);
        animation.setFillEnabled(true);
        animation.setFillAfter(true);
    }
    
    private final Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
            case MSG_TYPE_KILL_ME:
            	finish();
            	break;
            case MSG_TYPE_START_FIRE:
            	startFire();
            	break;
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
