package ict.step6.countdowntimer;

import android.os.Bundle;
import android.preference.PreferenceActivity;

public class Preferences extends PreferenceActivity {//設定画面特化のアクティビティ SharedPrefrensを使って読み取れる
	//Preference.xmlにセットしたKeyでPreferenceに書き込まれてる？　のでprefs.getString("alarm", "");で呼び出せる

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO 自動生成されたメソッド・スタブ
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.preferences_screen_);
		getPreferenceManager().setSharedPreferencesName("CountdownTimerPrefs");//prefNameをゲットして
		addPreferencesFromResource(R.xml.preference);//レイアウトの各々に割り当ててる key=alarm key=Vibratorとか
	}

}
