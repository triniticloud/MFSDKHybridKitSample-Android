package com.activeai.MFSDKHybridKitSample.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.activeai.MFSDKHybridKitSample.App;
import com.activeai.MFSDKHybridKitSample.R;
import com.morfeus.android.websdk.core.MFSDKHeader;
import com.morfeus.android.websdk.core.MFSDKSessionProperties;

import java.util.HashMap;

import static com.activeai.MFSDKHybridKitSample.utils.Constants.BOT_ID;
import static com.activeai.MFSDKHybridKitSample.utils.Constants.CUSTOMER_ID;
import static com.activeai.MFSDKHybridKitSample.utils.Constants.HEADER_HEIGHT;
import static com.activeai.MFSDKHybridKitSample.utils.Constants.LANGUAGE_CODE;
import static com.activeai.MFSDKHybridKitSample.utils.Constants.LEFT_BUTTON_IMAGE;
import static com.activeai.MFSDKHybridKitSample.utils.Constants.RIGHT_BUTTON_IMAGE;
import static com.activeai.MFSDKHybridKitSample.utils.Constants.SESSION_ID;
import static com.activeai.MFSDKHybridKitSample.utils.Constants.STT_LANGUAGE;
import static com.activeai.MFSDKHybridKitSample.utils.Constants.TITLE_FONT_SIZE;
import static com.activeai.MFSDKHybridKitSample.utils.Constants.TITLE_LEFT_MARGIN;
import static com.activeai.MFSDKHybridKitSample.utils.Constants.TTS_LANGUAGE;

/**
 * PostLoginActivity is the second screen of App
 * @author  Active.Ai
 * @version 1.0
 * @since   2020-01-09
 */
public class PostLoginActivity extends AppCompatActivity {

    private EditText mCustomerID;
    private EditText mSessionID;
    private Button mLogin;

    public static void start(Context context) {
        Intent intent = new Intent(context, PostLoginActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_login);

        init();
        onClick();
    }

    private void init() {
        mCustomerID = findViewById(R.id.et_customerID);
        mSessionID = findViewById(R.id.et_sessionID);
        mLogin = findViewById(R.id.btn_post_login);
    }

    private void onClick() {
        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openChatActivity(getSessionProperties());
            }
        });
    }

    /**
     * This method is used for opening ChatScreen
     * @param sessionProperties the sessionProperties is passed to the MFSDKMessagingManager
     */
    private void openChatActivity(MFSDKSessionProperties sessionProperties) {
        App.getMFSDK().showScreen(this, BOT_ID, sessionProperties);
    }

    /**
     * Return an {@link MFSDKSessionProperties} object that can be used by MFSDK
     * @return the SDK Session Properties
     */
    private MFSDKSessionProperties getSessionProperties() {
        return new MFSDKSessionProperties
                .Builder()
                .setUserInfo(getUserInfo())
                .setHeader(getHeader())
                .setLanguageCode(LANGUAGE_CODE)
                .setSpeechToTextLanguage(STT_LANGUAGE)
                .setTextToSpeechLanguage(TTS_LANGUAGE)
                .setSpeechProviderForVoice(
                        MFSDKSessionProperties.SpeechProviderForVoice.ANDROID_SPEECH_PROVIDER)
                .enableNativeVoiceOffline(false)
                .build();
    }

    /**
     * Return an {@link MFSDKHeader} object that can be set on the ChatScreen.
     * @return the header properties
     */
    private MFSDKHeader getHeader() {
        MFSDKHeader header = new MFSDKHeader();
        header.setStatusBarColor(getResources().getString(R.color.statusBarColor));
        header.setHeaderHeight(HEADER_HEIGHT);
        header.setBackgroundColor(getResources().getString(R.color.headerColor));
        header.setTitleAlignment(MFSDKHeader.Alignment.CENTER_ALIGN);

        header.setTitle(getString(R.string.chat_title));
        header.setTitleColor(getResources().getString(android.R.color.background_light));
        header.setTitleFontSize(TITLE_FONT_SIZE);

        header.setTitleLeftMargin(TITLE_LEFT_MARGIN);

        header.setLeftButtonImage(LEFT_BUTTON_IMAGE);
        header.setLeftButtonAction(MFSDKHeader.ButtonAction.BACK_BUTTON);

        header.setRightButtonImage(RIGHT_BUTTON_IMAGE);
        header.setRightButtonAction(MFSDKHeader.ButtonAction.LOGOUT_BUTTON);
        return header;
    }

    /**
     * Return an {@link HashMap < String ,  String >} object that contains userInfo like
     * @see #mCustomerID {CUSTOMER_ID},
     * @see #mSessionID {SESSION_ID}
     * @return userInfo
     */
    @NonNull
    private HashMap<String, String> getUserInfo() {
        HashMap<String, String> userInfo = new HashMap<>();

        String customerId = mCustomerID.getText().toString();
        if (!TextUtils.isEmpty(customerId)) {
            userInfo.put(CUSTOMER_ID, customerId);
        }

        String sessionId = mSessionID.getText().toString();
        if (!TextUtils.isEmpty(sessionId)) {
            userInfo.put(SESSION_ID, sessionId);
        }

        return userInfo;
    }
}
