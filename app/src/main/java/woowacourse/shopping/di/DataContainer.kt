package woowacourse.shopping.di

import android.content.Context
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.data.repository.cart.MockCartRepository
import woowacourse.shopping.data.repository.product.MockProductRepository
import woowacourse.shopping.domain.repository.ProductRepository

object DataContainer {
    private var appContext: Context? = null
    fun init(context: Context){
        if(appContext==null){
            appContext = context.applicationContext
        }
    }
    val cartRepository: CartRepository by lazy { MockCartRepository() }
    val productRepository: ProductRepository by lazy { MockProductRepository() }
}
