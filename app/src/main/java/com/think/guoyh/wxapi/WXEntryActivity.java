package com.think.guoyh.wxapi;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.base.gyh.baselib.utils.mylog.Logger;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import com.think.guoyh.R;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 使用：
 * <p>
 * Intent intent = new Intent(LoginActivity.this, WXEntryActivity.class);
 * intent.putExtra(WXEntryActivity.FLAG_KEY, "3");
 * startActivity(intent);
 */

public class WXEntryActivity extends AppCompatActivity implements IWXAPIEventHandler {
    public static String FLAG_KEY = "flag";
    private String WX_APP_SECRET = "21c507afa151dd0b1a9e897793d865c6";
    public String WX_APP_APP_ID = "wxc6350072e0733efc";
    private ProgressDialog mProgressDialog;
    private IWXAPI iwxapi;
    private final int SHARE_FIREND = 0x1;
    private final int SHARE_CIRCLE = 0x2;
    private final int LOGIN = 0x4;

    public int flag;

    private String user_openId, accessToken, refreshToken, scope;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_wxentry);
        iwxapi = WXAPIFactory.createWXAPI(this, WX_APP_APP_ID, false);
        iwxapi.handleIntent(getIntent(), this);
        iwxapi.registerApp(WX_APP_APP_ID);
        flag = getIntent().getIntExtra(FLAG_KEY, -1);
        Log.e("TGA", flag + "-----------------flag-----------");
        slecterFlag();
    }

    private void slecterFlag() {
        if (flag == SHARE_CIRCLE) {
            shareWXSceneTimeline();
        } else if (flag == SHARE_FIREND) {
            shareWXSceneSession();
        } else if (flag == LOGIN) {
            login();
//            finish();
        }
    }

    /**
     * 登陆
     */
    private void login() {
        if (!iwxapi.isWXAppInstalled()) {
            Toast.makeText(this, "您的设备未安装微信客户端", Toast.LENGTH_SHORT).show();
        } else {
            final SendAuth.Req req = new SendAuth.Req();
            req.scope = "snsapi_userinfo";
            req.state = "wechat_sdk_demo_test";
            iwxapi.sendReq(req);
        }
    }

    /**
     * 分享好友
     */
    public void shareWXSceneSession() {
        share(SendMessageToWX.Req.WXSceneSession);
    }

    /**
     * 分享朋友圈
     */
    public void shareWXSceneTimeline() {
        share(SendMessageToWX.Req.WXSceneTimeline);
    }

    private void createProgressDialog() {
        if (mProgressDialog != null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);//转盘
            mProgressDialog.setCancelable(false);
            mProgressDialog.setCanceledOnTouchOutside(false);
            mProgressDialog.setTitle("提示");
            mProgressDialog.setMessage("登录中，请稍后");
        }
        mProgressDialog.show();
    }

    private void share(int type) {
        WXWebpageObject webpageObject = new WXWebpageObject();
        webpageObject.webpageUrl = "https://www.juranguanjia.com/mobile/downJkApp/?from=singlemessage";
        WXMediaMessage msg = new WXMediaMessage(webpageObject);
        msg.title = "匠可-提供技师之间交流、学习安装维修技能的全方面学习平台";
        Bitmap thumb = BitmapFactory.decodeResource(getResources(), R.drawable.arrow_up);
        msg.thumbData = bmpToByteArray(thumb, true);
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("Req");
        req.message = msg;
        req.scene = type;
        iwxapi.sendReq(req);
        finish();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        iwxapi.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq baseReq) {
    }

    @Override
    public void onResp(BaseResp resp) {
        String result;
        switch (resp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                String code = ((SendAuth.Resp) resp).code;
                //获取accesstoken
                getAccessToken(code);
                Log.d("fantasychongwxlogin", code.toString() + "");
                result = "分享成功";
//                startActivity(new Intent(WXEntryActivity.this, MainActivity.class));
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                result = "取消分享";
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                result = "分享被拒绝";
                break;
            default:
                result = "发送返回";
                break;
        }
//        Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    protected void onDestroy() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
        super.onDestroy();
    }

    private void getAccessToken(String code) {
        createProgressDialog();
        StringBuffer loginUrl = new StringBuffer();
        loginUrl.append("https://api.weixin.qq.com/sns/oauth2/access_token")
                .append("?appid=")
                .append(WX_APP_APP_ID)
                .append("&secret=")
                .append(WX_APP_SECRET)
                .append("&code=")
                .append(code)
                .append("&grant_type=authorization_code");
        Log.d("urlurl", loginUrl.toString());

        OkHttpClient okHttpClient = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(loginUrl.toString())
                .get()//默认就是GET请求，可以不写
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("guoyh", "onFailure: ");
                mProgressDialog.dismiss();

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseInfo = response.body().string();
                Log.d("guoyh", "onResponse: " + responseInfo);
                String access = null;
                String openId = null;
                try {
                    JSONObject jsonObject = new JSONObject(responseInfo);
                    access = jsonObject.getString("access_token");
                    openId = jsonObject.getString("openid");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                getUserInfo(access, openId);
            }
        });

    }

    private void getUserInfo(String access, String openId) {
        String getUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo?access_token=" + access + "&openid=" + openId;
        OkHttpClient okHttpClient = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(getUserInfoUrl)
                .get()//默认就是GET请求，可以不写
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("guoyh", "onFailure: ");
                mProgressDialog.dismiss();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseInfo = response.body().string();
                SharedPreferences.Editor editor = getSharedPreferences("userInfo", MODE_PRIVATE).edit();
                editor.putString("responseInfo", responseInfo);
                editor.commit();
                toFinish();
                mProgressDialog.dismiss();
            }
        });
    }

    private void toFinish() {
        Logger.d("%s+++++++++++%s", "guoyh", "去关闭");
        finish();
    }

    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }

    public static byte[] bmpToByteArray(final Bitmap bmp, final boolean needRecycle) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, output);
        if (needRecycle) {
            bmp.recycle();
        }
        byte[] result = output.toByteArray();
        try {
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
