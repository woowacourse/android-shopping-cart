package woowacourse.shopping

import android.app.Application
import woowacourse.shopping.data.ProductRepositoryImpl
import woowacourse.shopping.data.db.CartDao
import woowacourse.shopping.data.db.CartDatabase
import woowacourse.shopping.domain.ProductRepository

class ShoppingApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    fun provideProductRepository(): ProductRepository = ProductRepositoryImpl(providerCartDao())

    fun providerCartDao(): CartDao {
        val db = CartDatabase.getInstance(applicationContext)
        return db.cartDao()
    }

    companion object {
        lateinit var instance: ShoppingApplication
            private set
    }
}
