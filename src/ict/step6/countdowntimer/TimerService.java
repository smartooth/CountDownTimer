package ict.step6.countdowntimer;

import java.util.Timer;
import java.util.TimerTask;

import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.os.PowerManager;
import android.util.Log;

public class TimerService extends Service {//画面を持たずバックグラウンドで動いてくれる　常に動作し続けることが可能 Manifest.xmlに追記するのを忘れずに

	
	public Context mContext;
	@Override
	public void onStart(Intent intent, int startId) {
		// TODO 自動生成されたメソッド・スタブ
		super.onStart(intent, startId);
		
		this.mContext = this;//インスタンス変数にthisを入れる意味あるの？
		this.counter = intent.getIntExtra("counter", 0);
		if(this.counter != 0){
			PowerManager pm = (PowerManager)getSystemService(Context.POWER_SERVICE);
			this.wl = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK+PowerManager.ON_AFTER_RELEASE,"My Tag" );//ロック状態に移行しないように設定
			this.wl.acquire();
			this.startTimer();
		}
	}

	public Integer counter;
	public Timer timer;
	public PowerManager.WakeLock wl;
	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO 自動生成されたメソッド・スタブ
		
		
		
		return null;
	}

	@Override
	public void onDestroy() {
		// TODO 自動生成されたメソッド・スタブ
		super.onDestroy();
		timer.cancel();
		if(wl.isHeld())
			wl.release();
	}
	
	public void startTimer(){
		if(this.timer != null)
			this.timer.cancel();
		this.timer = new Timer();
		final android.os.Handler handler = new android.os.Handler();//完全修飾子での呼び出し インナークラスでの利用を想定してるから？ インポートで普通に動くが･･
		
		this.timer.schedule(new TimerTask() {//インナークラスの中にインナークラス　インインナー？
			
			@Override
			public void run() {
				// TODO 自動生成されたメソッド・スタブ
				handler.post(new Runnable() {
					//counter=1;このインスタンスでは呼び出せないよ！
					//timer.cancel();
					@Override
					public void run() {
						// TODO 自動生成されたメソッド・スタブ
						if(counter == -1){//こっちなら呼び出せる
							timer.cancel();
							if(wl.isHeld())
								wl.release();
							showAlarm();
						}else{
							CountdownTimerActivity.countdown(counter);//一秒減らす　一秒ごとに呼び出されている
							counter = counter -1;
						}
					}
				});
			}
		}, 0, 1000);//1000msごとにTimerTaskを呼び出す
	}

	public void showAlarm(){//countが-1になったとき
		Intent intent = new Intent(mContext,TimerService.class);
		this.mContext.stopService(intent);//このサービスを停止
		intent = new Intent(this.mContext,AlarmDialog.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		this.mContext.startActivity(intent);
	}
}
