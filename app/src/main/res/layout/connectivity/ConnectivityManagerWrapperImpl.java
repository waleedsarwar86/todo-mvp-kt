package com.waleedsarwar.todoist.data.util.connectivity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public final class ConnectivityManagerWrapperImpl implements com.waleedsarwar.todoist.data.util.connectivity.ConnectivityManagerWrapper {

    private final ConnectivityManager connectivityManager;

    public ConnectivityManagerWrapperImpl(final Context context) {
        this.connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    @Override
    public boolean isConnectedToNetwork() {
        final NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public com.waleedsarwar.todoist.data.util.connectivity.NetworkData getNetworkData() {
        final NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        final boolean hasInternetConnection = (activeNetworkInfo != null && activeNetworkInfo.isConnected());
        final boolean isMobileConnection = (activeNetworkInfo != null && activeNetworkInfo.getType() == ConnectivityManager.TYPE_MOBILE);
        return new com.waleedsarwar.todoist.data.util.connectivity.NetworkData(hasInternetConnection, isMobileConnection);
    }
}
