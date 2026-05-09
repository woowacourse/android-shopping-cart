package woowacourse.shopping.di

import android.content.Context
import woowacourse.shopping.data.remote.HttpClientProvider
import woowacourse.shopping.data.remote.MockWebServerProvider
import woowacourse.shopping.data.remote.api.ProductService
import woowacourse.shopping.data.remote.api.ProductServiceImpl
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.data.repository.cart.MockCartRepository
import woowacourse.shopping.data.repository.product.RemoteProductRepository
import woowacourse.shopping.domain.repository.ProductRepository

object DataContainer {
    private var appContext: Context? = null
    fun init(context: Context){
        if(appContext==null){
            appContext = context.applicationContext
        }
    }
    val cartRepository: CartRepository by lazy { MockCartRepository() }
    val productRepository: ProductRepository by lazy { RemoteProductRepository(productService) }

    val productService: ProductService by lazy {
        ProductServiceImpl(
            client = HttpClientProvider.okHttpClient,
            baseUrlProvider = { MockWebServerProvider.baseUrl },
        )
    }
}
