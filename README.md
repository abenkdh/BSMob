# BSMob

                final BSMob bsMob = new BSMob.AdmobInterstitial(this)
                .setAdRequest(new AdRequest.Builder().build())
                .setIntersttitialId("ca-app-pub-3940256099942544/1033173712")
                .repeatRequest(false)
                .setListener(new BSMobInterstitialListener() {
                    @Override
                    public void onAdLoaded(InterstitialAd interstitialAd) {
                        if(interstitialAd.isLoaded()){
                            interstitialAd.show();
                        }
                    }
                })
                .build();
        bsMob.load();
