package com.demo.vuln_zip;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class MainActivity extends AppCompatActivity {
    public static String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button_Activity1=(Button) findViewById(R.id.button);
        button_Activity1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String zipPtath = getApplicationContext().getCacheDir()+"/test.zip";//压缩包路径
                String unzipPath = getApplicationContext().getCacheDir().toString();//解压路径
                try {
                    unzipFile(zipPtath,unzipPath);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void unzipFile(String zipPtath, String outputDirectory)throws IOException {
        Log.i(TAG,"开始解压的文件： "  + zipPtath + "\n" + "解压的目标路径：" + outputDirectory );
        // 创建解压目标目录
        File file = new File(outputDirectory);
        // 如果目标目录不存在，则创建
        if (!file.exists()) {
            file.mkdirs();
        }

        // 打开压缩文件
        InputStream inputStream = new FileInputStream(zipPtath); ;
        ZipInputStream zipInputStream = new ZipInputStream(inputStream);
        // 读取一个进入点
        ZipEntry zipEntry = zipInputStream.getNextEntry();
        // 使用1Mbuffer
        byte[] buffer = new byte[1024 * 1024];
        // 解压时字节计数
        int count = 0;
        // 如果进入点为空说明已经遍历完所有压缩包中文件和目录
        while (zipEntry != null) {
            Log.i(TAG,"解压文件 入口 1： " +zipEntry );
            if (!zipEntry.isDirectory()) {
                String fileName = zipEntry.getName();
                Log.i(TAG,"解压文件 的名字： " + fileName);
                Log.i(TAG,"outputDirectory + File.separator + fileName");
                file = new File(outputDirectory + File.separator + fileName);  //放到新的解压的文件路径
                file.createNewFile();
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                while ((count = zipInputStream.read(buffer)) > 0) {
                    fileOutputStream.write(buffer, 0, count);
                }
                fileOutputStream.close();
            }
            // 定位到下一个文件入口
            zipEntry = zipInputStream.getNextEntry();
            Log.i(TAG,"解压文件 入口 2： " + zipEntry );
        }
        zipInputStream.close();
        Log.i(TAG,"解压完成");
    }

}