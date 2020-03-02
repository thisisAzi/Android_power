package cn.mmvtc.power;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {

    private Button btnReadSms;
    private TextView tvShowSms;
    private StringBuilder smss=new StringBuilder();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //初始化控件
        initView();

    //添加按钮点击事件
        btnReadSms.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_SMS)!= PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.READ_SMS},1);
            }else{
                readSms();
            }
        }
    });
}

    //读取手机短信
    private void readSms(){
        Uri parse = Uri.parse("content://sms/");
        Cursor cursor = getContentResolver().query(parse, new String[]{"_id"}, null, null, null, null);
        if(cursor!=null){
            while(cursor.moveToNext()){
                int anInt = cursor.getInt(0);
                smss.append(anInt+"\n");
            }
        }
        tvShowSms.setText(smss.toString());
    }
    //对权限进行处理

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode){
            case 1:
                if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    readSms();
                }else {
                    Toast.makeText(this, "授权失败", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

    //初始化控件
    private void initView() {
        btnReadSms = (Button) findViewById(R.id.btn_readSms);
        tvShowSms = (TextView) findViewById(R.id.tv_showSms);
    }
}
