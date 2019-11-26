
package org.cordova.quasar.app;
import android.net.http.SslError;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
public class EnableHttpsSelfSigned {
  public static void enable(WebView webview) {
    webview.setWebViewClient(new WebViewClient() {
      @Override
      public void onReceivedSslError(WebView view, final SslErrorHandler handler, SslError error) {
        handler.proceed();
      }
    });
  }
}