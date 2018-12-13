package fanruiqi.bwie.com.week3_1213.util;

import android.os.Handler;
import android.os.Looper;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

public class OkHttps {

    private static OkHttps mOkHttps;
    Handler handler;
    private final OkHttpClient mOkHttpClient;

    private OkHttps() {
         handler = new Handler(Looper.getMainLooper());

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(); //日志拦截器
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        mOkHttpClient = new OkHttpClient.Builder()
                 .readTimeout(5000, TimeUnit.MILLISECONDS)
                 .writeTimeout(5000,TimeUnit.MILLISECONDS)
                 .connectTimeout(5000,TimeUnit.MILLISECONDS)
                 .addInterceptor(interceptor)
                 .build();
    }

    public static OkHttps getInstance(){
        if (mOkHttps==null){
            synchronized (OkHttps.class){
                if (mOkHttps==null){
                    return mOkHttps=new OkHttps();
                }
            }
        }
        return mOkHttps;
    }

    public interface OnCallBack{
        void onFail();
        void onResponse(String json);
    }

    public void doGet(String url,final OnCallBack onCallBack){

        Request request = new Request.Builder()
                .url(url)
                .build();

        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                if (response!=null&&response.isSuccessful()){
                    final String json = response.body().string();

                    if (onCallBack!=null){
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                onCallBack.onResponse(json);
                            }
                        });
                    }
                }
            }
        });
    }

    public void doPost(String url, Map<String,String> map,final OnCallBack onCallBack){  //POST

        FormBody.Builder builder = new FormBody.Builder();

        for (String k:map.keySet()){
            builder.add(k,map.get(k));
        }

        FormBody formBody = builder.build();

        Request request = new Request.Builder()
                .post(formBody)
                .url(url)
                .build();

        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                if (response!=null&&response.isSuccessful()){
                    final String json = response.body().string();

                    if (onCallBack!=null){
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                onCallBack.onResponse(json);
                            }
                        });
                    }
                }
            }
        });
    }
}
