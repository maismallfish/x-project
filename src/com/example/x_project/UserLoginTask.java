package com.example.x_project;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import android.content.Intent;
import android.os.AsyncTask;

import com.example.login.activity.MainActivity;
import com.example.login.activity.UserStruct;
import com.tx.util.UtilJson;
import com.tx.util.common;

/**
 * Represents an asynchronous login/registration task used to authenticate
 * the user.
 */
public class UserLoginTask extends AsyncTask<UserStruct, Void, Boolean> {
	@Override
	protected Boolean doInBackground(UserStruct... params) {
		// TODO: attempt authentication against a network service.
		UserStruct usrStruct = new UserStruct();
		if (params.length==0) {
			usrStruct = params[0];
		}

		try {
			// Simulate network access.
			DoLogin(usrStruct);
		} catch (InterruptedException e) {
			return false;
		}

		for (String credential : DUMMY_CREDENTIALS) {
			String[] pieces = credential.split(":");
			if (pieces[0].equals(mEmail)) {
				// Account exists, return true if the password matches.
				return pieces[1].equals(mPassword);
			}
		}

		// TODO: register the new account here.
		return true;
	}

	@Override
	protected void onPostExecute(final Boolean success) {
		if (success) {
			finish();
		} else {
			mPasswordView
					.setError(getString(R.string.error_incorrect_password));
			mPasswordView.requestFocus();
		}
	}

	@Override
	protected void onCancelled() {
	}
	
	public Boolean DoLogin(UserStruct user)
    {
		//setContentView(layoutResID)
		
		Boolean bLogin = false;
		
        //发送数据给服务器
		String httpUrl = "http://happytopic.sinaapp.com/register.php";
        HttpPost request = new HttpPost(httpUrl);
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("nickname",user.strUsrName));
        try {
            HttpEntity entity = new UrlEncodedFormEntity(params, "UTF-8");
            request.setEntity(entity);
            HttpClient client = new DefaultHttpClient();
            HttpResponse response = client.execute(request);
            
            System.out.println(response.getStatusLine().getStatusCode());
            
            if(response.getStatusLine().getStatusCode()==HttpStatus.SC_OK){
                String str = EntityUtils.toString(response.getEntity());
                
                //返回回来的是json的数据，需要解析
                InputStream is = UtilJson.String2InputStream(str);
				UtilJson.readJsonStream(str, user.context);
				
				//	跳转到下一页
				gotoNextPage(user);
                
                bLogin = true;
            }else{
                //tv_rp.setText("请求错误");
            }
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            
            StringWriter sw = new StringWriter();  
            e.printStackTrace(new PrintWriter(sw, true));  
            String str = sw.toString();  
            System.out.println("==========");  
  
            System.out.println(str);
        }
        
        return bLogin;
    }
	
	private void gotoNextPage(UserStruct user)
	{
		Intent intent = new Intent(user.context, MainActivity.class);
		intent.putExtra(common.EXTRA_USERID, user.strUsrId);
		user.context.startActivity(intent);
    }  
}