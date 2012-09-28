package ict.step6.countdowntimer;

import java.text.SimpleDateFormat;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.BitmapDrawable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.support.v4.app.NavUtils;

public class CountdownTimerActivity extends Activity {

	public static TextView tv ;
	public static SeekBar sb;
	public static Context mContext;
	public static Integer timeLeft = 0;
	public static Button btnStart,btnStop;
	
	/*
	 * 
	 * serviceが一秒ごとにしか呼び出されないので
	 * ボタン連打でカウントが減らない　バグと言うより仕様か
	 */
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        CountdownTimerActivity.mContext = this;
        tv = (TextView)this.findViewById(R.id.textView1);
        btnStart = (Button)this.findViewById(R.id.buttonStart);
        btnStop = (Button)this.findViewById(R.id.buttonStop);
        sb = (SeekBar)this.findViewById(R.id.seekBar1);
        sb.setProgressDrawable(this.drawScale());//setProgressDrawableでバーの画像変更
        sb.setBackgroundDrawable(this.drawScale());//こっちは背景
        this.setListeners();
        
    }

    
    public BitmapDrawable drawScale(){//なんらかのBitmapDrawableを返す　BitmapDrawableってなんぞ
    	Paint paint;
    	Path path;
    	Canvas canvas;
    	Bitmap bitmap;
    	
    	paint = new Paint();
    	paint.setStrokeWidth(0);
    	paint.setStyle(Paint.Style.STROKE);
    	bitmap = Bitmap.createBitmap(241,30,Bitmap.Config.ARGB_8888);
    	
    	path = new Path();
    	canvas = new Canvas(bitmap);
    	
    	for(int i=0;i<17;i++){//メモリ(縦線)の描画
    		path.reset();
    		if(i == 5||i ==10 || i == 15){
    			paint.setColor(Color.BLACK);
    		}else
    			paint.setColor(Color.GRAY);
    		
    		path.moveTo(i*16, 5);
    		path.quadTo(i*16, 5, i*16, 15);
    		canvas.drawPath(path, paint);
    	}
    	BitmapDrawable bd = new BitmapDrawable(bitmap);
    	
    	return bd;
    }
    
    
    public void setListeners(){
    	sb.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {//匿名インナークラス
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO 自動生成されたメソッド・スタブ
				
			
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO 自動生成されたメソッド・スタブ
				
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				// TODO 自動生成されたメソッド・スタブ
				
				//timeLeft = progress*10;
				timeLeft = progress*60;
				if(fromUser)
					showTime(progress*60);
				if(fromUser&&(progress>0))
					btnStart.setEnabled(true);
				else 
					btnStart.setEnabled(false);
				if(progress == 0)
					btnStop.setEnabled(false);
			}
		});
    	
    	btnStart.setOnClickListener(new OnClickListener() {//スタートボタンを押したとき
			
			@Override
			public void onClick(View v) {
				// TODO 自動生成されたメソッド・スタブ
				Intent intent = new Intent(mContext,TimerService.class);
				intent.putExtra("counter", timeLeft);
				startService(intent);
				btnStart.setEnabled(false);
				btnStop.setEnabled(true);
				sb.setEnabled(false);
			}
		});
    	
    	btnStop.setOnClickListener(new OnClickListener() {//ストップボタンを押したとき
			
			@Override
			public void onClick(View v) {
				// TODO 自動生成されたメソッド・スタブ
				
				Intent intent = new Intent(mContext,TimerService.class);
				mContext.stopService(intent);
				btnStop.setEnabled(false);
				btnStart.setEnabled(true);
				sb.setEnabled(true);
			}
		});
    	
    	((Button)findViewById(R.id.buttonSettings)).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO 自動生成されたメソッド・スタブ
				
				Intent intent = new Intent(CountdownTimerActivity.this,Preferences.class);
				startActivity(intent);
			}
		});
    }
    
    public static void showTime(Integer timeSeconds){
    	
    	SimpleDateFormat form = new SimpleDateFormat("mm:ss");
    	tv.setText(form.format(timeSeconds*1000));//セットした形にObject型!を整形してくれる　Object型の利点は？
    }
    
    public static void countdown(Integer counter){
    	
    	CountdownTimerActivity.showTime(counter);
    	timeLeft = counter;
    	if(counter%60 == 0)
    		sb.setProgress(counter/60);    	
    	else
    		sb.setProgress(counter/60+1);
    	
    	if(counter!=0){
    		btnStop.setEnabled(true);
    		btnStart.setEnabled(false);
    		sb.setEnabled(false);
    	}else{
    		btnStop.setEnabled(false);
    		btnStart.setEnabled(false);
    		sb.setEnabled(true);
    	}
    		
    }
}
