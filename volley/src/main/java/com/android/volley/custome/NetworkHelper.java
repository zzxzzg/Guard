package com.android.volley.custome;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class NetworkHelper {

	public static final String TAG = NetworkHelper.class.getName();
    private static final int MSG_UPLOAD_SUCCESS=0;
    private static final int MSG_UPLOAD_FAIL=1;

	private static NetworkHelper mNetworkHelper;

	private static final String HTTP = "http://";
	private static final String HTTPS = "https://";

	private RequestQueue mRequestQueue;
	private Context mContext;
//    private Handler handler=new Handler(){
//        @Override
//        public void handleMessage(Message msg) {
//            switch (msg.what){
//                case MSG_UPLOAD_SUCCESS:
//                    if(msg.getData()!=null){
//                        String result=msg.getData().getString("result");
//                        OnRequestResult listener=(OnRequestResult)msg.obj;
//                        try {
//                            listener.onRequestSuccess(new JSONObject(result));
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//
//                case MSG_UPLOAD_FAIL:
//                    OnRequestResult listener=(OnRequestResult)msg.obj;
//                    listener.onRequestFail(null);
//                    break;
//            }
//        }
//    };

	private static String getProtocol() {
		return HTTP;
	}

	public static NetworkHelper getInstance(Context context) {
		if (mNetworkHelper == null) {
			mNetworkHelper = new NetworkHelper(context.getApplicationContext());
		}
		return mNetworkHelper;
	}

	private NetworkHelper(Context context) {
		mContext = context;
		mRequestQueue = Volley.newRequestQueue(context);
	}

	public <T> void addToRequestQueue(Request<T> req, String tag) {
		// set the default tag if tag is empty
		req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
		mRequestQueue.add(req);
	}

	public <T> void addToRequestQueue(Request<T> req) {
		req.setTag(TAG);
		mRequestQueue.add(req);
	}

	public void cancelPendingRequests(Object tag) {
		if (mRequestQueue != null) {
			mRequestQueue.cancelAll(tag);
		}
	}

//    public void uploadAvatar(byte[] file,UploadUtil.OnUploadProcessListener listener){
//        Map<String, String> jsonValues = new HashMap<String, String>();
//        jsonValues.put("type", UPLOAD_TYPE_AVATAR);
//        UploadUtil mUploadUtil= UploadUtil.getInstance();
//        mUploadUtil.setOnUploadProcessListener(listener);
//        //"/sdcard/DCIM/Camera/IMG_20150701_112020.jpg"
//        String url=getProtocol()+"121.40.51.83/file/post/fileUpload.do";
//        mUploadUtil.uploadFile(file, "pic", url, jsonValues);
//    }

//    public void register(String data,final OnRequestResult onRequestResult ){
//        String tag="register";
//        mRequestQueue.cancelAll(tag);
//        String url=getProtocol()+"121.40.51.83/user/post/editUserInfo.do";
//        Map<String, String> params = new HashMap<String, String>();
//        params.put("data", data);
//        baseRequest(url, params, onRequestResult, tag);
//    }
//
//	public void login(String name,String passwd,final OnRequestResult onRequestResult){
//		String tag="login";
//		mRequestQueue.cancelAll(tag);
//		String url=getProtocol()+"121.40.51.83/user/login.do";
//		Map<String, String> params = new HashMap<String, String>();
//		params.put("userName", name);
//		params.put("password", passwd);
//		baseRequest(url, params, onRequestResult, tag);
//
//	}

//    public void getUserInfo(int userid,final OnRequestResult onRequestResult){
//        String tag="getUserInfo";
//        mRequestQueue.cancelAll(tag);
//        String url=getProtocol()+"121.40.51.83/user/userInfo.do";
//        Map<String, String> params = new HashMap<String, String>();
//        params.put("userId", userid+"");
//        baseRequest(url, params, onRequestResult, tag);
//    }



	private void baseRequestGET(final String url,Map<String, String> params,final OnRequestResult onRequestResult,String tag){
		CustomeJsonRequest request=new CustomeJsonRequest(Request.Method.GET,url,params,new Response.Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				Log.d("sss", response.toString());
				if(onRequestResult!=null){
					onRequestResult.onRequestSuccess(response);
				}
			}


		},new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				Log.d("sss", url+"  error "+error.getMessage());
				if(onRequestResult!=null){
					onRequestResult.onRequestError();
				}
			}
		});
		request.setTag(tag);
		request.setRetryPolicy(new DefaultRetryPolicy());
		mRequestQueue.add(request);
	}


	private void baseRequest(final String url,Map<String, String> params,final OnRequestResult onRequestResult,String tag){
        CustomeJsonRequest request=new CustomeJsonRequest(Request.Method.POST,url,params,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("sss", response.toString());
                if(onRequestResult!=null){
                    onRequestResult.onRequestSuccess(response);
                }
            }


        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("sss", url+"  error "+error.getMessage());
                if(onRequestResult!=null){
                    onRequestResult.onRequestError();
                }
            }
        });
        request.setTag(tag);
        request.setRetryPolicy(new DefaultRetryPolicy());
        mRequestQueue.add(request);
    }




    public interface OnRequestResult {
		public abstract void onRequestError();

		public abstract void onRequestFail(Object obj);

		public abstract void onAuthError(Object obj);

		public abstract void onRequestSuccess(Object obj);
	}

	public static class SimpleOnRequestResult implements OnRequestResult {

		public void onReturn() {

		}

		@Override
		public void onRequestError() {
			onReturn();
		}

		@Override
		public void onRequestFail(Object obj) {
			onReturn();
		}

		@Override
		public void onAuthError(Object obj) {
			onReturn();
		}

		@Override
		public void onRequestSuccess(Object obj) {
			onReturn();
		}

	}







}
