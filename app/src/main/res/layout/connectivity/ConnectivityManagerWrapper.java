package com.waleedsarwar.todoist.data.util.connectivity;

public interface ConnectivityManagerWrapper {

    boolean isConnectedToNetwork();

    NetworkData getNetworkData();
}
