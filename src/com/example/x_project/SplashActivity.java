package com.example.x_project;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.widget.EditText;

import com.example.login.activity.UserStruct;
import com.example.login.database.DBHelper;
import com.tx.util.UtilJson;
import com.tx.util.common;

public class SplashActivity extends Activity {

	private DBHelper mDbHelper;
	private String mStrUsrNameString;
	private String mStrUsrPasswdString;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		
		//����Ҫ�ж�Ҫ��Ҫ���ֵ�¼����
		initDlgLogin();
	}
	
	private void initDlgLogin()
	{
		mDbHelper = new DBHelper(getApplicationContext());
		final EditText edtUsrName = new EditText(this);
		
		//����
		if(mDbHelper.queryAllUserName().length!=0)
		{
			mStrUsrNameString = mDbHelper.queryAllUserName()[0];
			mStrUsrPasswdString = mDbHelper.queryPasswordByName(mStrUsrNameString);
			//�ж��ǲ��ǵ绰����
			if (mStrUsrNameString.isEmpty() || mStrUsrPasswdString.isEmpty()) {
				StartLoginActivity();
			}
			else {
				//ֱ�ӵ�¼
				UserStruct usrStruct = new UserStruct();
				usrStruct.strUsrId = mStrUsrNameString;
				usrStruct.strPassWd = mStrUsrPasswdString;
				LoginActivity.attemptLogin(usrStruct);
			}
		}
		else
		{
			StartLoginActivity();
		}
	}
	
	private void StartLoginActivity() {
		Intent intent = new Intent(this,com.example.x_project.LoginActivity.class);
		intent.putExtra(common.EXTRA_USERID, mStrUsrNameString);
		startActivity(intent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.splash, menu);
		return true;
	}

}
