package woowacourse.shopping.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.stateIn

class AndroidNetworkStatusMonitor(
    context: Context,
    scope: CoroutineScope,
) : NetworkStatusMonitor {
    private val connectivityManager: ConnectivityManager =
        context.getSystemService(ConnectivityManager::class.java)
    private val initialConnectionState: Boolean = connectivityManager.hasInternetConnection()

    override val isConnected: StateFlow<Boolean> =
        callbackFlow {
            fun emitCurrentConnectionState() {
                trySend(connectivityManager.hasInternetConnection())
            }

            val callback =
                object : ConnectivityManager.NetworkCallback() {
                    override fun onAvailable(network: Network) {
                        emitCurrentConnectionState()
                    }

                    override fun onLost(network: Network) {
                        emitCurrentConnectionState()
                    }

                    override fun onCapabilitiesChanged(
                        network: Network,
                        networkCapabilities: NetworkCapabilities,
                    ) {
                        emitCurrentConnectionState()
                    }
                }

            emitCurrentConnectionState()
            connectivityManager.registerDefaultNetworkCallback(callback)

            awaitClose {
                runCatching { connectivityManager.unregisterNetworkCallback(callback) }
            }
        }.distinctUntilChanged()
            .stateIn(
                scope = scope,
                started = SharingStarted.Eagerly,
                initialValue = initialConnectionState,
            )

    private fun ConnectivityManager.hasInternetConnection(): Boolean {
        val network = activeNetwork ?: return false
        val capabilities = getNetworkCapabilities(network) ?: return false
        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }
}
