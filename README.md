<!DOCTYPE html>
<html>

<body class="stackedit">
  <div class="stackedit__html"><p><strong># IMPLEMEN</strong></p>
<pre><code>implementation("com.github.abenkdh:BSMob:1.2",{
    exclude group: 'com.google.android.gms'
})
</code></pre>
<p><strong>#USAGE</strong></p>
<pre><code>BSMob bsMob = new BSMob.AdmobInterstitial(this)  
        .setAdRequest(AdRequest)  
        .setIntersttitialId(InterstitialID)  
        .repeatRequest(true/false)  
        .build();  
bsMob.load();
</code></pre>
<p><strong>#SHOW ADS</strong></p>
<pre><code>bsMob.show();
</code></pre>
</div>
</body>

</html>
