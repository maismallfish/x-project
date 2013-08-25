package com.example.login.activity;

import java.io.ByteArrayInputStream;
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
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupWindow;

import com.example.x_project.R;
import com.example.login.database.DBHelper;
import com.example.login.database.DBUser.User;

public class MainActivity extends Activity implements OnClickListener {
	private DBHelper dbHelper;

	private String m_strUsrName;
	WebView wv;  
    ProgressDialog pd;  
    Handler handler;  

	
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		gotoNextPage();
	}

	private void initWidget(){
		
//		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()  
//        .detectDiskReads()  
//        .detectDiskWrites()  
//        .detectNetwork()   // or .detectAll() for all detectable problems  
//        .penaltyLog()  
//        .build());  
//		StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()  
//        .detectLeakedSqlLiteObjects()  
//        .detectLeakedClosableObjects()  
//        .penaltyLog()  
//        .penaltyDeath()  
//        .build());  
		
		initDlgLogin();
		
	}
	
	
	public void readJsonStream(String in) throws IOException {
		//InputStreamReader isr = new InputStreamReader(in, "UTF-8");
	     JSONArray jsonArr = null;
		
	     JSONObject oj = null;
	     try
		{
	    	 oj = new JSONObject(in);
		}
	     catch(Exception e)
	     {
	    	 
	     }
		
		String id = null;
	     String text = null;
	     
		 String value = oj.optString("id");
		 if(!value.isEmpty())
		 {
			 id = value;
		 }
		 value = oj.optString("nickname");
		 if(!value.isEmpty())
		 {
			 text = value;
		 }
	     
	   //����ֵ������Ҫ�����������´ε�¼��ʱ��ȥȡ����
	     String strPasswd = null;
         dbHelper.insertOrUpdate(text, id, strPasswd, 1);
	   }
	
   InputStream String2InputStream(String str){ 
	   ByteArrayInputStream stream = new ByteArrayInputStream(str.getBytes()); 
	   return stream; 
	} 
	   
	
	private Boolean Login(UserStruct user)
    {
		//setContentView(layoutResID)
		
		//return true;
		
		Boolean bLogin = false;
		
        //�������ݸ�������
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
                
                //���ػ�������json�����ݣ���Ҫ����
                InputStream is = String2InputStream(str);
				readJsonStream(str);
				
				//	��ת����һҳ
				gotoNextPage();
                
                bLogin = true;
            }else{
                //tv_rp.setText("�������");
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
	
	private void initDlgLogin()
	{
		dbHelper = new DBHelper(this);
		final EditText edtUsrName = new EditText(this);
		
		if(dbHelper.queryAllUserName().length!=0)
		{
			m_strUsrName = dbHelper.queryAllUserName()[0];
			gotoNextPage();
		}
		else
		{
			User user = new User();
			
			Builder dlg = new AlertDialog.Builder(this).setTitle("����һ���ǳư�:").setView(
					edtUsrName).setPositiveButton("��ʼʹ��", new DialogInterface.OnClickListener() {
			            
			             public void onClick(DialogInterface dialog, int which) {
			            	 UserStruct usr = new UserStruct();
			            	 usr.strUsrName = edtUsrName.getText().toString();
			            	 Login(usr);
			             }});
			dlg.show();
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////
	//	���������
	private void gotoNextPage()
	{
		setContentView(R.layout.activity_topic);
		
		
		 
		init();//ִ�г�ʼ������  
        loadurl(wv,"http://happytopic.sinaapp.com/topic.php");  
        handler=new Handler(){  
            public void handleMessage(Message msg)  
            {//����һ��Handler�����ڴ��������߳���UI��ͨѶ  
              if (!Thread.currentThread().isInterrupted())  
              {  
                switch (msg.what)  
                {  
                case 0:  
                    pd.show();//��ʾ���ȶԻ���           
                    break;  
                case 1:  
                    pd.hide();//���ؽ��ȶԻ��򣬲���ʹ��dismiss()��cancel(),�����ٴε���show()ʱ����ʾ�ĶԻ���СԲȦ���ᶯ��  
                    break;  
                }  
              }  
              super.handleMessage(msg);  
            }  
        };  
    }  
    public void init(){//��ʼ��  
        wv=(WebView)findViewById(R.id.mainpanel);  
        wv.getSettings().setJavaScriptEnabled(true);//����JS  
        wv.setScrollBarStyle(0);//���������Ϊ0���ǲ������������ռ䣬��������������ҳ��  
        wv.setWebViewClient(new WebViewClient(){     
            public boolean shouldOverrideUrlLoading(final WebView view, final String url) {  
                loadurl(view,url);//������ҳ  
                return true;     
            }//��д�������,��webview����  
   
        });  
        wv.setWebChromeClient(new WebChromeClient(){  
            public void onProgressChanged(WebView view,int progress){//������ȸı������   
                if(progress==100){  
                    handler.sendEmptyMessage(1);//���ȫ������,���ؽ��ȶԻ���  
                }     
                super.onProgressChanged(view, progress);     
            }     
        });  
   
        pd=new ProgressDialog(MainActivity.this);  
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);  
        pd.setMessage("���������У����Ժ�");  
    }  
    public boolean onKeyDown(int keyCode, KeyEvent event) {//��׽���ؼ�  
        if ((keyCode == KeyEvent.KEYCODE_BACK) && wv.canGoBack()) {     
            wv.goBack();     
            return true;     
        }else if(keyCode == KeyEvent.KEYCODE_BACK){  
            ConfirmExit();//���˷��ؼ������Ѿ����ܷ��أ���ִ���˳�ȷ��  
            return true;   
        }     
        return super.onKeyDown(keyCode, event);     
    }  
    public void ConfirmExit(){//�˳�ȷ��  
        AlertDialog.Builder ad=new AlertDialog.Builder(MainActivity.this);  
        ad.setTitle("�˳�");  
        ad.setMessage("�Ƿ��˳����?");  
        ad.setPositiveButton("��", new DialogInterface.OnClickListener() {//�˳���ť  
              
            public void onClick(DialogInterface dialog, int i) {  
                // TODO Auto-generated method stub  
                MainActivity.this.finish();//�ر�activity  
   
            }  
        });  
        ad.setNegativeButton("��",new DialogInterface.OnClickListener() {  
              
            public void onClick(DialogInterface dialog, int i) {  
                //���˳�����ִ���κβ���  
            }  
        });  
        ad.show();//��ʾ�Ի���  
    }  
    public void loadurl(final WebView view,final String url){  
        new Thread(){  
            public void run(){  
                handler.sendEmptyMessage(0);  
                view.loadUrl(url);//������ҳ  
            }  
        }.start();  
    }  
	
}



