package woowacourse.shopping

import android.app.Application

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        initShoppingProvider()
    }

    private fun initShoppingProvider() {
        ShoppingProvider.initProductRepository(this)
    }
}
