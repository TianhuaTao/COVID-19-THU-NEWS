package com.java.taotianhua.covidnews.ui.home.detail;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.sina.weibo.sdk.WbSdk;
import com.sina.weibo.sdk.auth.AccessTokenKeeper;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WbConnectErrorMessage;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.RequestListener;
import com.java.taotianhua.covidnews.R;

import java.text.SimpleDateFormat;

public class ShareNewsActivity extends AppCompatActivity   {
    private SsoHandler mSsoHandler;
    private Oauth2AccessToken mAccessToken;
    private TextView mTokenText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_news);


        configActivity();
    }

    private void configActivity(){
        setTitle("分享新闻");
        WbSdk.install(this,
                new AuthInfo(this, ShareConstants.APP_KEY
                        ,ShareConstants.REDIRECT_URL,ShareConstants.SCOPE));




        // 获取 Token View，并让提示 View 的内容可滚动（小屏幕可能显示不全）
        mTokenText = (TextView) findViewById(R.id.token_text_view);
        TextView hintView = (TextView) findViewById(R.id.obtain_token_hint);
        hintView.setMovementMethod(new ScrollingMovementMethod());

        // 创建微博实例
        mSsoHandler = new SsoHandler(ShareNewsActivity.this);
// SSO client


        // SSO all
        findViewById(R.id.obtain_token_via_signature).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("obtain_token_via_signature","before authorize");

                mSsoHandler.authorize(new SelfWbAuthListener());
                Log.i("obtain_token_via_signature","after authorize");

            }
        });
        // 用户登出
        findViewById(R.id.logout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AccessTokenKeeper.clear(getApplicationContext());
                mAccessToken = new Oauth2AccessToken();
                updateTokenView(false);
            }
        });


        //更新token
        findViewById(R.id.refresh).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(mAccessToken.getRefreshToken())) {
                    AccessTokenKeeper.refreshToken(ShareConstants.APP_KEY,
                            ShareNewsActivity.this, new RequestListener() {
                        @Override
                        public void onComplete(String response) {
                            Log.i("","onComplete");
                        }

                        @Override
                        public void onWeiboException(WeiboException e) {
                            Log.i("","onWeiboException");

                        }
                    });
                }
            }
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
    private class SelfWbAuthListener implements com.sina.weibo.sdk.auth.WbAuthListener {
        @Override
        public void onSuccess(final Oauth2AccessToken token) {
            ShareNewsActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.i("SelfWbAuthListener","run");
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
            Log.i("SelfWbAuthListener","cancel");


            Toast.makeText(ShareNewsActivity.this,
                    R.string.weibosdk_demo_toast_auth_canceled, Toast.LENGTH_LONG).show();
        }

        @Override
        public void onFailure(WbConnectErrorMessage errorMessage) {
            Log.i("SelfWbAuthListener","onFailure");

            Toast.makeText(ShareNewsActivity.this, errorMessage.getErrorMessage(), Toast.LENGTH_LONG).show();
        }
    }


    /**
     * 显示当前 Token 信息。
     *
     * @param hasExisted 配置文件中是否已存在 token 信息并且合法
     */
    private void updateTokenView(boolean hasExisted) {
        String date = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(
                new java.util.Date(mAccessToken.getExpiresTime()));
        String format = "Token：%1$s \\n有效期：%2$s";
        mTokenText.setText(String.format(format, mAccessToken.getToken(), date));

        String message = String.format(format, mAccessToken.getToken(), date);
        if (hasExisted) {
            message = "Token 仍在有效期内，无需再次登录。" + "\n" + message;
        }
        mTokenText.setText(message);
    }
}