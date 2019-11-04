package com.benkkstudio.bsmob;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.google.ads.consent.ConsentForm;
import com.google.ads.consent.ConsentFormListener;
import com.google.ads.consent.ConsentInfoUpdateListener;
import com.google.ads.consent.ConsentInformation;
import com.google.ads.consent.ConsentStatus;
import com.google.ads.consent.DebugGeography;
import com.google.ads.mediation.admob.AdMobAdapter;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

import java.net.MalformedURLException;
import java.net.URL;

public class BSMobConsent {

    public abstract static class ConsentCallback {
        abstract public void onResult(boolean isRequestLocationInEeaOrUnknown);
    }

    public abstract static class ConsentStatusCallback {
        abstract public void onResult(boolean isRequestLocationInEeaOrUnknown, int isConsentPersonalized);
    }

    public abstract static class ConsentInformationCallback {
        abstract public void onResult(ConsentInformation consentInformation, ConsentStatus consentStatus);
        abstract public void onFailed(ConsentInformation consentInformation, String reason);
    }

    public abstract static class LocationIsEeaOrUnknownCallback {
        abstract public void onResult(boolean isRequestLocationInEeaOrUnknown);
    }

    private static String ads_preference = "ads_preference";
    private static String user_status = "user_status";
    private static boolean PERSONALIZED = true;
    private static boolean NON_PERSONALIZED = false;
    private static String preferences_name = "com.ayoubfletcher.consentsdk";
    private Context context;
    private ConsentForm form;
    private String LOG_TAG = "ID_LOG";
    private String DEVICE_ID = "";
    private boolean DEBUG = false;
    private String privacyURL;
    private String publisherId;
    public BSMobConsent BSMobConsent;
    private SharedPreferences settings;
    public static class Builder {

        private Context context;
        private String LOG_TAG = "ID_LOG";
        private String DEVICE_ID = "";
        private boolean DEBUG = false;
        private String privacyURL;
        private String publisherId;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder addTestDeviceId(String device_id) {
            this.DEVICE_ID = device_id;
            this.DEBUG = true;
            return this;
        }

        public Builder addPrivacyPolicy(String privacyURL) {
            this.privacyURL = privacyURL;
            return this;
        }

        public Builder addPublisherId(String publisherId) {
            this.publisherId = publisherId;
            return this;
        }

        public Builder addCustomLogTag(String LOG_TAG) {
            this.LOG_TAG = LOG_TAG;
            return this;
        }

        public BSMobConsent build() {
            if(this.DEBUG) {
                BSMobConsent BSMobConsent = new BSMobConsent(context, publisherId, privacyURL, true);
                BSMobConsent.LOG_TAG = this.LOG_TAG;
                BSMobConsent.DEVICE_ID = this.DEVICE_ID;
                return BSMobConsent;
            } else {
                return new BSMobConsent(context, publisherId, privacyURL);
            }
        }
    }

    public static void initDummyBanner(Context context) {
        AdView adView = new AdView(context);
        adView.setAdSize(AdSize.BANNER);
        adView.setAdUnitId(BSMob.DUMMY_BANNER);
        adView.loadAd(new AdRequest.Builder().build());
    }

    public BSMobConsent(Context context, String publisherId, String privacyURL, boolean DEBUG) {
        this.context = context;
        this.settings = initPreferences(context);
        this.publisherId = publisherId;
        this.privacyURL = privacyURL;
        this.DEBUG = DEBUG;
        this.BSMobConsent = this;
    }

    private static SharedPreferences initPreferences(Context context) {
        return context.getSharedPreferences(preferences_name, Context.MODE_PRIVATE);
    }

    public BSMobConsent(Context context, String publisherId, String privacyURL) {
        this.context = context;
        this.settings = context.getSharedPreferences(preferences_name, Context.MODE_PRIVATE);
        this.publisherId = publisherId;
        this.privacyURL = privacyURL;
        this.BSMobConsent = this;
    }

    public static boolean isConsentPersonalized(Context context) {
        SharedPreferences settings = initPreferences(context);
        return settings.getBoolean(ads_preference, PERSONALIZED);
    }

    private void consentIsPersonalized() {
        settings.edit().putBoolean(ads_preference, PERSONALIZED).apply();
    }

    private void consentIsNonPersonalized() {
        settings.edit().putBoolean(ads_preference, NON_PERSONALIZED).apply();
    }

    private void updateUserStatus(boolean status) {
        settings.edit().putBoolean(user_status, status).apply();
    }

    public AdRequest getAdRequest() {
        if(isConsentPersonalized(context)) {
            return new AdRequest.Builder().build();
        } else {
            return new AdRequest.Builder().addNetworkExtrasBundle(AdMobAdapter.class, getNonPersonalizedAdsBundle()).build();
        }
    }

    private static Bundle getNonPersonalizedAdsBundle() {
        Bundle extras = new Bundle();
        extras.putString("npa", "1");
        return extras;
    }

    private void initConsentInformation(final ConsentInformationCallback callback) {
        final ConsentInformation consentInformation = ConsentInformation.getInstance(context);
        if(DEBUG) {
            if(!DEVICE_ID.isEmpty()) {
                consentInformation.addTestDevice(DEVICE_ID);
            }
            consentInformation.setDebugGeography(DebugGeography.DEBUG_GEOGRAPHY_EEA);
        }
        String[] publisherIds = {publisherId};
        consentInformation.requestConsentInfoUpdate(publisherIds, new ConsentInfoUpdateListener() {
            @Override
            public void onConsentInfoUpdated(ConsentStatus consentStatus) {
                if(callback != null) {
                    callback.onResult(consentInformation, consentStatus);
                }
            }

            @Override
            public void onFailedToUpdateConsentInfo(String reason) {
                callback.onFailed(consentInformation, reason);
            }
        });
    }

    public void isRequestLocationIsEeaOrUnknown(final LocationIsEeaOrUnknownCallback callback) {
        initConsentInformation(new ConsentInformationCallback() {
            @Override
            public void onResult(ConsentInformation consentInformation, ConsentStatus consentStatus) {
                callback.onResult(consentInformation.isRequestLocationInEeaOrUnknown());
            }

            @Override
            public void onFailed(ConsentInformation consentInformation, String reason) {
                callback.onResult(false);
            }
        });
    }

    public static boolean isUserLocationWithinEea(Context context) {
        return initPreferences(context).getBoolean(user_status, false);
    }

    public void checkConsent(final ConsentCallback callback) {
        initConsentInformation(new ConsentInformationCallback() {
            @Override
            public void onResult(ConsentInformation consentInformation, ConsentStatus consentStatus) {
                switch(consentStatus) {
                    case UNKNOWN:
                        if (DEBUG) {
                            Log.wtf(LOG_TAG, "Unknown Consent");
                            Log.wtf(LOG_TAG, "User location within EEA: " + consentInformation.isRequestLocationInEeaOrUnknown());
                        }
                        if(consentInformation.isRequestLocationInEeaOrUnknown()) {
                            requestConsent(new ConsentStatusCallback() {
                                @Override
                                public void onResult(boolean isRequestLocationInEeaOrUnknown, int isConsentPersonalized) {
                                    callback.onResult(isRequestLocationInEeaOrUnknown);
                                }
                            });
                        } else {
                            consentIsPersonalized();
                            callback.onResult(consentInformation.isRequestLocationInEeaOrUnknown());
                        }
                        break;
                    case NON_PERSONALIZED:
                        consentIsNonPersonalized();
                        callback.onResult(consentInformation.isRequestLocationInEeaOrUnknown());
                        break;
                    default:
                        consentIsPersonalized();
                        callback.onResult(consentInformation.isRequestLocationInEeaOrUnknown());
                        break;
                }
                updateUserStatus(consentInformation.isRequestLocationInEeaOrUnknown());
            }

            @Override
            public void onFailed(ConsentInformation consentInformation, String reason) {
                if(DEBUG) {
                    Log.wtf(LOG_TAG, "Failed to update: " + reason);
                }
                updateUserStatus(consentInformation.isRequestLocationInEeaOrUnknown());
                callback.onResult(consentInformation.isRequestLocationInEeaOrUnknown());
            }
        });
    }

    public void requestConsent(final ConsentStatusCallback callback) {
        URL privacyUrl = null;
        try {
            privacyUrl = new URL(privacyURL);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        form = new ConsentForm.Builder(context, privacyUrl)
                .withListener(new ConsentFormListener() {
                    @Override
                    public void onConsentFormLoaded() {
                        if(DEBUG) {
                            Log.wtf(LOG_TAG, "Consent Form is loaded!");
                        }
                        form.show();
                    }

                    @Override
                    public void onConsentFormError(String reason) {
                        if(DEBUG) {
                            Log.wtf(LOG_TAG, "Consent Form ERROR: $reason");
                        }
                        // Callback on Error
                        if (callback != null) {
                            BSMobConsent.isRequestLocationIsEeaOrUnknown(new LocationIsEeaOrUnknownCallback() {
                                @Override
                                public void onResult(boolean isRequestLocationInEeaOrUnknown) {
                                    callback.onResult(isRequestLocationInEeaOrUnknown, -1);
                                }
                            });
                        }
                    }

                    @Override
                    public void onConsentFormOpened() {
                        if(DEBUG) {
                            Log.wtf(LOG_TAG, "Consent Form is opened!");
                        }
                    }

                    @Override
                    public void onConsentFormClosed(ConsentStatus consentStatus, Boolean userPrefersAdFree) {
                        if(DEBUG) {
                            Log.wtf(LOG_TAG, "Consent Form Closed!");
                        }
                        final int isConsentPersonalized;
                        if (consentStatus == ConsentStatus.NON_PERSONALIZED) {
                            consentIsNonPersonalized();
                            isConsentPersonalized = 0;
                        } else {
                            consentIsPersonalized();
                            isConsentPersonalized = 1;
                        }
                        if(callback != null) {
                            BSMobConsent.isRequestLocationIsEeaOrUnknown(new LocationIsEeaOrUnknownCallback() {
                                @Override
                                public void onResult(boolean isRequestLocationInEeaOrUnknown) {
                                    callback.onResult(isRequestLocationInEeaOrUnknown, isConsentPersonalized);
                                }
                            });
                        }
                    }
                })
                .withPersonalizedAdsOption()
                .withNonPersonalizedAdsOption()
                .build();
        form.load();
    }

}
