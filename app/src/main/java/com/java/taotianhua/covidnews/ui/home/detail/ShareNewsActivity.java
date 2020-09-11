package com.java.taotianhua.covidnews.ui.home.detail;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.sina.weibo.sdk.WbSdk;
import com.sina.weibo.sdk.api.ImageObject;
import com.sina.weibo.sdk.api.TextObject;
import com.sina.weibo.sdk.api.WeiboMultiMessage;
import com.sina.weibo.sdk.auth.AccessTokenKeeper;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WbConnectErrorMessage;
import com.sina.weibo.sdk.auth.sso.SsoHandler;

import com.java.taotianhua.covidnews.R;
import com.sina.weibo.sdk.share.WbShareCallback;
import com.sina.weibo.sdk.share.WbShareHandler;

import java.text.SimpleDateFormat;


/**
 * 你要问我这里的代码为什么是这样写的
 * 我只能说无可奉告
 * 反正我也就按照 Weibo SDK 中的示例写的
 */
public class ShareNewsActivity extends AppCompatActivity implements WbShareCallback {
    private SsoHandler mSsoHandler;
    private Oauth2AccessToken mAccessToken;
    private TextView mTokenText;

    String textToShare = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_news);

        Intent intent = getIntent();
        if (intent.hasExtra("text")) {
            textToShare = intent.getStringExtra("text");
        }
        configActivity();
    }


    private void configActivity() {
        setTitle("分享新闻");
        TextView textView = findViewById(R.id.weibo_text);
        textView.setText(textToShare);


        WbSdk.install(this,
                new AuthInfo(this, ShareConstants.APP_KEY
                        , ShareConstants.REDIRECT_URL, ShareConstants.SCOPE));


        // 获取 Token View，并让提示 View 的内容可滚动（小屏幕可能显示不全）
        mTokenText = (TextView) findViewById(R.id.token_text_view);


        // 创建微博实例
        mSsoHandler = new SsoHandler(ShareNewsActivity.this);



        // SSO all
        findViewById(R.id.obtain_token_via_signature).setOnClickListener(v -> {
            Log.i("obtain_token_via_signature", "before authorize");

            mSsoHandler.authorize(new SelfWbAuthListener());
            Log.i("obtain_token_via_signature", "after authorize");

        });
        // 用户登出
        findViewById(R.id.logout).setOnClickListener(v -> {
            AccessTokenKeeper.clear(getApplicationContext());
            mAccessToken = new Oauth2AccessToken();
            updateTokenView(false);
        });


        // 从 SharedPreferences 中读取上次已保存好 AccessToken 等信息，
        // 第一次启动本应用，AccessToken 不可用
        mAccessToken = AccessTokenKeeper.readAccessToken(this);
        if (mAccessToken.isSessionValid()) {
            updateTokenView(true);
        }


    }

    /**
     * 当 SSO 授权 Activity 退出时，该函数被调用。
     *
     * @see {@link Activity#onActivityResult}
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // SSO 授权回调
        // 重要：发起 SSO 登陆的 Activity 必须重写 onActivityResults
        if (mSsoHandler != null) {
            mSsoHandler.authorizeCallBack(requestCode, resultCode, data);
        }

    }

    @Override
    public void onWbShareSuccess() {
        Toast.makeText(this, R.string.weibosdk_demo_toast_share_success, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onWbShareCancel() {
        Toast.makeText(this,
                getString(R.string.weibosdk_demo_toast_share_failed) + "Error Message: ",
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void onWbShareFail() {
        Toast.makeText(this, R.string.weibosdk_demo_toast_share_canceled, Toast.LENGTH_LONG).show();
    }

    public void onSendButtonClicked(View view) {
        sendMultiMessage(true, true);
    }

    private class SelfWbAuthListener implements com.sina.weibo.sdk.auth.WbAuthListener {
        @Override
        public void onSuccess(final Oauth2AccessToken token) {
            ShareNewsActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.i("SelfWbAuthListener", "run");
                    mAccessToken = token;
                    if (mAccessToken.isSessionValid()) {
                        // update Token
                        updateTokenView(false);
                        // save Token to SharedPreferences
                        AccessTokenKeeper.writeAccessToken(ShareNewsActivity.this, mAccessToken);
                        Toast.makeText(ShareNewsActivity.this,
                                R.string.weibosdk_demo_toast_auth_success, Toast.LENGTH_SHORT).show();

                    }
                }
            });
        }

        @Override
        public void cancel() {
            Log.i("SelfWbAuthListener", "cancel");


            Toast.makeText(ShareNewsActivity.this,
                    R.string.weibosdk_demo_toast_auth_canceled, Toast.LENGTH_LONG).show();
        }

        @Override
        public void onFailure(WbConnectErrorMessage errorMessage) {
            Log.i("SelfWbAuthListener", "onFailure");

            Toast.makeText(ShareNewsActivity.this, errorMessage.getErrorMessage(), Toast.LENGTH_LONG).show();
        }
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        shareHandler.doResultIntent(intent, this);
    }

    /**
     * 显示当前 Token 信息。
     *
     * @param hasExisted 配置文件中是否已存在 token 信息并且合法
     */
    private void updateTokenView(boolean hasExisted) {
        String date = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(
                new java.util.Date(mAccessToken.getExpiresTime()));
        String format = "Token：%1$s \n有效期：%2$s";
        mTokenText.setText(String.format(format, mAccessToken.getToken(), date));

        String message = String.format(format, mAccessToken.getToken(), date);
        if (hasExisted) {
            message = "Token 仍在有效期内，无需再次登录。" + "\n" + message;
        }
        mTokenText.setText(message);
    }


    public static final String KEY_SHARE_TYPE = "key_share_type";
    public static final int SHARE_CLIENT = 1;
    public static final int SHARE_ALL_IN_ONE = 2;
    private WbShareHandler shareHandler;
    private int mShareType = SHARE_ALL_IN_ONE;

    /**
     * 第三方应用发送请求消息到微博，唤起微博分享界面。
     */
    private void sendMultiMessage(boolean hasText, boolean hasImage) {
        shareHandler = new WbShareHandler(this);
        shareHandler.registerApp();

        WeiboMultiMessage weiboMessage = new WeiboMultiMessage();
        if (hasText) {
            weiboMessage.textObject = getTextObj();
        }
        if (hasImage) {
            weiboMessage.imageObject = getImageObj();
        }

        shareHandler.shareMessage(weiboMessage, mShareType == SHARE_CLIENT);

    }

    /**
     * 创建文本消息对象。
     *
     * @return 文本消息对象。
     */
    private TextObject getTextObj() {
        TextObject textObject = new TextObject();
        textObject.text = textToShare;
        textObject.title = "COVID-19 NEWS";
        textObject.actionUrl = "https://github.com/tth135/COVID-19-THU-NEWS";
        return textObject;
    }

    /**
     * 创建图片消息对象。
     *
     * @return 图片消息对象。
     */
    private ImageObject getImageObj() {
        ImageObject imageObject = new ImageObject();
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        imageObject.setImageObject(bitmap);
        return imageObject;
    }
}