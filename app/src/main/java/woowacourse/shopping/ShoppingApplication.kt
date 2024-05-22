package woowacourse.shopping

import android.app.Application
import woowacourse.shopping.data.local.ShoppingDatabase
import woowacourse.shopping.data.repository.CartRepositoryImpl
import woowacourse.shopping.data.repository.ProductRepositoryImpl

class ShoppingApplication : Application() {
//    val productRepository: ProductRepository by lazy {
//        ProductRepositoryImpl(DefaultProducts)
//    }
//    val cartRepository: CartRepository by lazy {
//        CartRepositoryImpl(DefaultCart)
//    }
    val database by lazy { ShoppingDatabase.getDatabase(this) }
    val productRepository by lazy { ProductRepositoryImpl(database.productDao()) }
    val cartRepository by lazy { CartRepositoryImpl(database.cartDao()) }
}
