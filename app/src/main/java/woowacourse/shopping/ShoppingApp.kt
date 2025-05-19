package woowacourse.shopping

import android.app.Application
import woowacourse.shopping.data.database.ShoppingDatabase
import woowacourse.shopping.data.repository.LocalProductRepository
import woowacourse.shopping.domain.repository.ProductRepository

class ShoppingApp : Application() {
    val productRepository: ProductRepository by lazy { LocalProductRepository(database.productDao()) }
    private val database: ShoppingDatabase by lazy { ShoppingDatabase.getInstance(this) }
}
