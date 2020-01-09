package com.activeai.MFSDKHybridKitSample;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.morfeus.android.push.MFNotification;
import com.morfeus.android.push.MFNotificationInterceptor;
import com.morfeus.android.push.NotificationData;
import com.morfeus.android.websdk.MFSDKInitializationException;
import com.morfeus.android.websdk.MFSDKMessagingHandler;
import com.morfeus.android.websdk.MFSDKMessagingManager;
import com.morfeus.android.websdk.MFSDKMessagingManagerKit;
import com.morfeus.android.websdk.MFSDKProperties;
import com.morfeus.android.websdk.MFSearchResponseModel;

import java.util.HashMap;

import static com.activeai.MFSDKHybridKitSample.utils.Constants.BOT_URL;
import static com.activeai.MFSDKHybridKitSample.utils.Constants.BOT_ID;
import static com.activeai.MFSDKHybridKitSample.utils.Constants.BOT_NAME;
import static com.activeai.MFSDKHybridKitSample.utils.Constants.DISABLE_SCREENSHOT;
import static com.activeai.MFSDKHybridKitSample.utils.Constants.ENABLE_ROOTED_DEVICE;
import static com.activeai.MFSDKHybridKitSample.utils.Constants.ENABLE_SSL;
import static com.activeai.MFSDKHybridKitSample.utils.Constants.SPEECH_API_KEY;

/**
 * App is the Application Class
 * @author  Active.Ai
 * @version 1.0
 * @since   2020-01-09
 */
public class App extends Application {

    private static final String TAG = "MFSDK_APP_LOG";
    private static final String TITLE = "title";
    private static final String BODY = "body";
    private static final String NOTIFICATION_ID = "notificationId";
    private static final String NOTIFICATION_TYPE = "notificationType";
    private static HashMap<String, String> notificationDataHM = new HashMap<>();

    private static MFSDKMessagingManager sMFSDK;

    @Override
    public void onCreate() {
        super.onCreate();

        initMFNotification(this);
        initMFSDKMessagingKit(this);

    }

    /**
     * This method is used to initialize the {@link MFNotification}
     * @param context
     */
    public static void initMFNotification(Context context){
        Log.d(TAG, " >>> Initializing MFNotification");
        MFNotification.create(context)
                .setDisplayOnScreenNotification(MFNotification.OnScreenNotificationOption.None)
                .setNotificationHandler(new MFNotification.NotificationHandler() {
                    @Override
                    public void onNotificationReceived(NotificationData notificationData) {
                        Log.d(TAG, "Notification Received");
                        setNotificationData(notificationData);
                    }

                    @Override
                    public void onNotificationTapped(NotificationData notificationData) {
                        Log.d(TAG, "Notification Tapped");
                        setNotificationData(notificationData);
                    }
                })
                .init();
    }

    /**
     * This method is used to initialize the {@link MFSDKMessagingManagerKit}
     * @param context
     */
    private void initMFSDKMessagingKit(Context context) {
        Log.d(TAG, " >>> Initializing MFSDKMessagingKit");
        MFSDKProperties sdkProperties = getMFSDKProperties();

        try {
            MFNotificationInterceptor interceptor = new MFNotificationInterceptor();
            interceptor.registerNotificationListener(context);
            sMFSDK = new MFSDKMessagingManagerKit
                    .Builder(context)
                    .setSdkProperties(sdkProperties)
                    .build();

            sMFSDK.addMFNotificationInterceptor(interceptor);
            sMFSDK.initWithProperties();
        } catch (MFSDKInitializationException e) {
            Log.e(TAG, " >>> Failed to initialize MFSDKMessagingKit", e);
        }
    }

    /**
     * Return an {@link MFSDKProperties} object that is pass to the {@link MFSDKMessagingManagerKit}
     * @return MFSDKProperties
     */
    private MFSDKProperties getMFSDKProperties() {
        return new MFSDKProperties
                .Builder(BOT_URL)
                .addBot(BOT_ID, BOT_NAME)
                .enableSSL(ENABLE_SSL, new String[]{})
                .disableScreenShot(DISABLE_SCREENSHOT)
                .enableRootedDeviceCheck(ENABLE_ROOTED_DEVICE)
                .showNativeHeader(true)
                .setSpeechAPIKey(SPEECH_API_KEY)
                .showExitDialog(false)
                .setAppVersion(BuildConfig.VERSION_NAME)
                .setFileProviderAuthority(getString(R.string.file_provider_authority))
                .setMFSDKMessagingHandler(new MFSDKMessagingHandler() {
                    @Override
                    public void onSearchResponse(MFSearchResponseModel model) {

                    }

                    @Override
                    public void onSSLCheck(String url, String requestCode) {

                    }

                    @Override
                    public void onChatClose() {

                    }

                    @Override
                    public void onEvent(String eventType, String payloadMap) {

                    }

                    @Override
                    public void onHomemenubtnclick() {

                    }
                })
                .build();
    }

    /**
     * This method is used to set notification data to {@link MFSDKMessagingManagerKit}
     * @param notificationData
     */
    public static void setNotificationData(NotificationData notificationData) {
        if(sMFSDK != null && notificationData != null){

            sMFSDK.setNotificationData(notificationData);

            notificationDataHM.put(TITLE, notificationData.getTitle());
            notificationDataHM.put(BODY, notificationData.getBody());
            notificationDataHM.put(NOTIFICATION_ID, notificationData.getNotificationId());
            notificationDataHM.put(NOTIFICATION_TYPE, notificationData.getNotificationType());
        }
    }

    /**
     * Return an {@link HashMap<String, String>} object that contains notification data
     * @return notificationDataHM
     */
    public static HashMap<String, String> getNotificationData() {
        Log.d(TAG, " >>> Getting notification data");
        return notificationDataHM;
    }

    public static void clearNotificationDataHM(){
        notificationDataHM.clear();
    }

    /**
     * Return an {@link MFSDKMessagingManagerKit} instance of MFSDKMessagingManagerKit
     * @return sMFSDK is the static object of MFSDKMessagingManagerKit
     */
    public static MFSDKMessagingManager getMFSDK() {
        Log.d(TAG, " >>> Getting MFSDKMessagingKit instance");
        return sMFSDK;
    }
}
