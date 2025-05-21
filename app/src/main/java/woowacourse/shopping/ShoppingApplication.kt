package woowacourse.shopping

import android.app.Application
import android.util.Log
import woowacourse.shopping.data.repository.ShoppingRepositoryImpl
import woowacourse.shopping.di.DataSourceProvider
import woowacourse.shopping.di.RepositoryProvider

class ShoppingApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        Log.i("TEST", "ShoppingApplication onCreate")
        _instance = this
        initRepository()
    }

    private fun initRepository() {
        val productDataSource = DataSourceProvider.productDataSource
        val cartDataSource = DataSourceProvider.cartDataSource
        val repository = ShoppingRepositoryImpl(productDataSource, cartDataSource)
        RepositoryProvider.initShoppingRepository(repository)
    }

    companion object {
        private var _instance: ShoppingApplication? = null
        val instance get() = _instance!!
    }
}
