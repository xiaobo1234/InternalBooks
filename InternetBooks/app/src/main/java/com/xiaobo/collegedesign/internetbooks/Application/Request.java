package com.xiaobo.collegedesign.internetbooks.Application;

import android.os.Handler;
import android.os.Message;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.xiaobo.collegedesign.internetbooks.R;
import com.xiaobo.collegedesign.internetbooks.Utils.ErrorUtils;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import io.realm.Realm;
import io.realm.RealmObject;
import retrofit.ErrorHandler;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.converter.GsonConverter;
import retrofit.mime.TypedOutput;

/**
 *
 * version 2.1
 *
 * changes:
 * 1. changes adapter() to final static adapter
 * 2. check class type in toObject method
 * version 2.0
 *
 * changes:
 * 1. add reflection
 *
 * Updated by hu on 14/11/14
 *
 * version 1.0
 *
 * Created by hu on 14/10/31.
 */
public class Request {

    public final static String apiServerUrl = "http://jichedang.sohuapps.com/api/v1";
//    public final static String apiServerUrl = "http://testjichedang.sohuapps.com/api/v1";
//    public final static String apiServerUrl = "http://10.0.0.9:9292/api/v1";

    public final static String noParams = "1234567890987654321";
    public final static String noEqualParams = "\"1234567890987654321\"";

    private static final int errorCode = 0;

    static Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what)
            {
                case errorCode:
                    int cause = (Integer) msg.obj;
                    ErrorUtils.setError(cause);
                    break;
            }
        }
    };



    static  Gson gson = new GsonBuilder()
            .setExclusionStrategies(new ExclusionStrategy() {
                @Override
                public boolean shouldSkipField(FieldAttributes f) {
                    return f.getDeclaringClass().equals(RealmObject.class);
                }

                @Override
                public boolean shouldSkipClass(Class<?> clazz) {
                    return false;
                }
            })
            .create();
    /**
     * 构造 RestAdapter ，指定 authorization 和 api 地址
     */
    public static RestAdapter adapter(final Object object){
//        if(ApplicationRunTime.getAppContext().getResources().getBoolean(R.bool.haveDebug))
//        {
//            return new RestAdapter.Builder()
//                    .setLogLevel(RestAdapter.LogLevel.FULL)
//                    .setLog(new RestAdapter.Log() {
//                        @Override
//                        public void log(String message) {
////                            Log.setEnabled(true);
////                            Log.setLog2FileEnabled(true);
////                            FilePathGenerator.DefaultFilePathGenerator defaultFilePathGenerator = new FilePathGenerator.DefaultFilePathGenerator(ApplicationRunTime.getAppContext().getFilesDir().getPath()+ File.separator+"log","app",".log");
////                            defaultFilePathGenerator.getPath();
////                            Log.setFilePathGenerator(defaultFilePathGenerator);
////                            Log.e(message);
//                        }
//                    })
//                    .setErrorHandler(new ErrorHandler() {
//                        @Override
//                        public Throwable handleError(RetrofitError cause) {
//
////                        Log.e("CardMainActivity--Request", "cause=" + cause.getCause().toString() + "$$" + cause.getKind());
//                            if (cause.getKind() == RetrofitError.Kind.NETWORK) {
//                                // 获取一个Message对象，设置what为1
//                                Message msg = Message.obtain();
//                                msg.obj = 2;
//                                msg.what = errorCode;
//                                // 发送这个消息到消息队列中
//                                handler.sendMessage(msg);
//                            } else {
//
//                                // 获取一个Message对象，设置what为1
//                                Message msg = Message.obtain();
//                                msg.obj = cause.getResponse().getStatus();
//                                msg.what = errorCode;
//                                // 发送这个消息到消息队列中
//                                handler.sendMessage(msg);
//
//                            }
//                            return cause;
//                        }
//                    })
//                    .setRequestInterceptor(new RequestInterceptor() {
//                        @Override
//                        public void intercept(RequestFacade request) {
//                            request.addHeader("Content-Type", "application/json");
////                            request.addHeader("Authorization", User.authorization(new Gson().toJson(object)));
//                            request.addHeader("Platform", "2");
//                        }
//                    })
//                    .setConverter(new GsonConverter(gson))
//                    .setEndpoint(apiServerUrl)
//
//                    .build();
//        }
//        else {
            return new RestAdapter.Builder()
                    .setErrorHandler(new ErrorHandler() {
                        @Override
                        public Throwable handleError(RetrofitError cause) {

//                        Log.e("CardMainActivity--Request", "cause=" + cause.getCause().toString() + "$$" + cause.getKind());
                            if (cause.getKind() == RetrofitError.Kind.NETWORK) {
                                // 获取一个Message对象，设置what为1
                                Message msg = Message.obtain();
                                msg.obj = 2;
                                msg.what = errorCode;
                                // 发送这个消息到消息队列中
                                handler.sendMessage(msg);
                            } else {

                                // 获取一个Message对象，设置what为1
                                Message msg = Message.obtain();
                                msg.obj = cause.getResponse().getStatus();
                                msg.what = errorCode;
                                // 发送这个消息到消息队列中
                                handler.sendMessage(msg);

                            }
                            return cause;
                        }
                    })
                    .setRequestInterceptor(new RequestInterceptor() {
                        @Override
                        public void intercept(RequestFacade request) {
                            request.addHeader("Content-Type", "application/json");
//                            request.addHeader("Authorization", User.authorization(new Gson().toJson(object)));
                            request.addHeader("Platform", "2");
                        }
                    })
                    .setConverter(new GsonConverter(gson))
                    .setEndpoint(apiServerUrl)

                    .build();
//        }
    }

    private static class JsonTypedOutput implements TypedOutput {
        private final byte[] jsonBytes;
        private final String mimeType;

        public JsonTypedOutput(byte[] jsonBytes, String charset) {
            this.jsonBytes = jsonBytes;
            this.mimeType = "application/json; charset=" + charset;
        }

        @Override
        public String fileName() {
            return null;
        }

        @Override
        public String mimeType() {
            return mimeType;
        }

        @Override
        public long length() {
            return jsonBytes.length;
        }

        @Override
        public void writeTo(OutputStream out) throws IOException {
            out.write(jsonBytes);
        }
    }

    /**
     * 将 JsonElement 对象 转换为 Model 类
     * @param element JsonElement 对象
     * @param cls   指定的转换目标对象的 class
     * @return 目标对象
     */
    public static Object toObject(JsonElement element, Class cls){

        if(cls.getSuperclass().getSimpleName().equals("RealmObject") == false){
            return new Gson().fromJson(element, cls);
        }

        Realm realm = Realm.getInstance(ApplicationRunTime.getAppContext());
        return realm.createObjectFromJson(cls,element.toString());
    }

}
