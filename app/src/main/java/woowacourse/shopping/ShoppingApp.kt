package woowacourse.shopping

import android.app.Application
import woowacourse.shopping.data.source.ProductDataSource
import woowacourse.shopping.data.source.RemoteProductDataSource
import woowacourse.shopping.remote.MockProductApiService
import woowacourse.shopping.remote.ProductApiService

class ShoppingApp : Application() {
    private val productsApi: ProductApiService by lazy { MockProductApiService() }

    val productSource: ProductDataSource by lazy { RemoteProductDataSource(productsApi) }

    override fun onTerminate() {
        super.onTerminate()
        productSource.shutDown()
    }
}
