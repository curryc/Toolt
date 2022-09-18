package com.curry.util.web;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import com.curry.util.base.BaseEvent;
import com.curry.util.event.GetBitmapByUrlEvent;
import com.curry.util.event.MatchRegexEvent;
import com.curry.util.log.Logger;
import okhttp3.*;
import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @program: Toolt
 * @author: 陈博文
 * @create: 2022-09-18 12:03
 * @description: 进行各种网络操作，必须在后台进行
 **/
public class Web {
    private static final String TAG = "web";
    private static OkHttpClient client = new OkHttpClient();

    /**
     * 根据一个正则表达式获取一个网站源代码中特定的字符串
     *
     * @param url
     * @param reg
     */
    public static void getStringsByReg(String url, String reg, String id) {
        Request request = new Request.Builder()
                .url(url)
                .build();
        BaseEvent event=new MatchRegexEvent(id, false);

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                event.setEvent(BaseEvent.NETWORK_WRONG_CODE, null);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {//回调的方法执行在子线程。
                    String data = response.body().string();
                    Pattern pattern = Pattern.compile(reg);
                    Matcher matcher = pattern.matcher(data);
                    List<String> ret = new ArrayList<>();
                    while (matcher.find()) {
                        ret.add(matcher.group());
                    }
                    event.setEvent(response.code(), ret.toArray(new String[0]));
                    EventBus.getDefault().post(event);
                }
            }
        });
    }

    /**
     * 通过一个url获取一个对象(图片，音乐，视频等等)
     *
     * @param urlString
     * @return
     */
    public static void getBitmapByUrl(String urlString, String id) {
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                URL url = null;
                Bitmap bitmap = null;
                BaseEvent event = new GetBitmapByUrlEvent(id, false);
                try {
                    url = new URL(urlString);
                    // 使用HttpURLConnection打开连接
                    HttpURLConnection urlConn = (HttpURLConnection) url
                            .openConnection();
                    urlConn.setDoInput(true);
                    urlConn.connect();
                    // 将得到的数据转化成InputStream
                    InputStream is = urlConn.getInputStream();
                    // 将InputStream转换成Bitmap
                    bitmap = BitmapFactory.decodeStream(is);
                    is.close();
                    event.setEvent(BaseEvent.EVERYTHING_RIGHT, bitmap);
                    EventBus.getDefault().post(event);
                } catch (Exception e) {
                    e.printStackTrace();
                    event.setEvent(BaseEvent.NETWORK_WRONG_CODE, null);
                    EventBus.getDefault().post(event);
                }
            }
        });
    }
}
