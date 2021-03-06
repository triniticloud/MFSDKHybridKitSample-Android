package com.activeai.MFSDKHybridKitSample.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.activeai.MFSDKHybridKitSample.App;
import com.activeai.MFSDKHybridKitSample.R;
import com.morfeus.android.push.MFNotification;
import com.morfeus.android.push.NotificationData;
import com.morfeus.android.websdk.core.MFSDKHeader;
import com.morfeus.android.websdk.core.MFSDKSessionProperties;

import static com.activeai.MFSDKHybridKitSample.utils.Constants.BOT_ID;
import static com.activeai.MFSDKHybridKitSample.utils.Constants.HEADER_HEIGHT;
import static com.activeai.MFSDKHybridKitSample.utils.Constants.LANGUAGE_CODE;
import static com.activeai.MFSDKHybridKitSample.utils.Constants.LEFT_BUTTON_IMAGE;
import static com.activeai.MFSDKHybridKitSample.utils.Constants.RIGHT_BUTTON_IMAGE;
import static com.activeai.MFSDKHybridKitSample.utils.Constants.STT_LANGUAGE;
import static com.activeai.MFSDKHybridKitSample.utils.Constants.TITLE_FONT_SIZE;
import static com.activeai.MFSDKHybridKitSample.utils.Constants.TITLE_LEFT_MARGIN;
import static com.activeai.MFSDKHybridKitSample.utils.Constants.TTS_LANGUAGE;

/**
 * MainActivity is the first screen of App
 * @author  Active.Ai
 * @version 1.0
 * @since   2020-01-09
 */
public class MainActivity extends AppCompatActivity {

    private Button mLoginButton;
    private Button mPostLoginButton;
    private NotificationData mNotificationData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        onClick();

        if(hasNotificationExtras()){
            App.setNotificationData(mNotificationData);
            openChatActivity(getSessionProperties());
        }

    }

    private void init(){
        mLoginButton = findViewById(R.id.btn_login);
        mPostLoginButton = findViewById(R.id.btn_login_post);
    }

    private void onClick() {
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openChatActivity(getSessionProperties());
            }
        });

        mPostLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PostLoginActivity.start(MainActivity.this);
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
     * This method is used to verifying that Intent having Extra NotificationData or not.
     * @return true when NotificationData
     */
    private boolean hasNotificationExtras() {
        if (getIntent().getExtras() != null){
            mNotificationData = getIntent().getParcelableExtra(MFNotification.EXTRA_NOTIFICATION_DATA);
            return mNotificationData != null;
        }
        return false;
    }
}
