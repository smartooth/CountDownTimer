package ict.step6.countdowntimer;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.view.View.OnClickListener;

public class AlarmDialog extends Activity {
	
	public Ringtone rt;//音に関する
	public Vibrator vib;//バイブに関する変数？
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO 自動生成されたメソッド・スタブ
		super.onCreate(savedInstanceState);
		setContentView(R.layout.alarmdialog);
		
		this.vib = (Vibrator)getSystemService(VIBRATOR_SERVICE);
		
		findViewById(R.id.button1).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO 自動生成されたメソッド・スタブ
				
				if(rt != null)
					rt.stop();
				vib.cancel();
				finish();
			}
		});
		
	}
	@Override
	protected void onResume() {
		// TODO 自動生成されたメソッド・スタブ
		super.onResume();
		
		SharedPreferences prefs;
		prefs=this.getSharedPreferences("CountdownTimerPrefs", 0);
		
		String fn = prefs.getString("alarm", "");
		if(fn!=""){
			rt = RingtoneManager.getRingtone(this, Uri.parse(fn));
			if(rt!=null&&!rt.isPlaying())
				rt.play();
		}
		
		if(prefs.getBoolean("vibrator", true))
			vib.vibrate(new long[]{0,1000,500,1000,500,1000},-1);
	}
	

}
