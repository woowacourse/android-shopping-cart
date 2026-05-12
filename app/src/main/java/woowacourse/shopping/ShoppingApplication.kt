package woowacourse.shopping

import android.app.Application
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import androidx.room.Room
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import woowacourse.shopping.data.local.AppDatabase
import woowacourse.shopping.data.remote.MockServer

class ShoppingApplication : Application() {
    companion object {
        lateinit var instance: ShoppingApplication
            private set
    }

    lateinit var database: AppDatabase
        private set

    private val _isOnline = MutableStateFlow(true)
    val isOnline: StateFlow<Boolean> = _isOnline.asStateFlow()

    override fun onCreate() {
        super.onCreate()
        instance = this
        MockServer.start()
        database = Room.databaseBuilder(
                context = this,
                klass = AppDatabase::class.java,
                name = "shopping-database"
            ).fallbackToDestructiveMigration(false)
            .build()

        registerNetworkCallback()
    }

    private fun registerNetworkCallback() {
        val connectivityManager = getSystemService(ConnectivityManager::class.java) ?: return
        
        val activeNetwork = connectivityManager.activeNetwork
        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork)
        _isOnline.value = capabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true

        connectivityManager.registerDefaultNetworkCallback(object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                _isOnline.value = true
            }

            override fun onLost(network: Network) {
                _isOnline.value = false
            }
        })
    }
}
