package com.waleedsarwar.todoist.data.util.connectivity;

import rx.Single;

public interface NetworkUtils {

    Single<Boolean> isConnectedToInternet();

    Single<com.waleedsarwar.todoist.data.util.connectivity.NetworkData> getActiveNetworkData();
}