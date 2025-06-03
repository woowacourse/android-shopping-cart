package woowacourse.shopping.data.repository

import android.os.Handler
import android.os.Looper
import woowacourse.shopping.data.dummyProducts
import woowacourse.shopping.domain.model.Product

object ProductDummyRepositoryImpl : ProductRepository {

    private val mainHandler = Handler(Looper.getMainLooper())

    override fun fetchProducts(
        count: Int,
        lastId: Int,
        onSuccess: (List<Product>) -> Unit
    ) {
        Thread {
            val result = dummyProducts
                .filter { it.id > lastId }
                .take(count)

            mainHandler.post {
                onSuccess(result)
            }
        }.start()
    }

    override fun fetchProductDetail(
        id: Int,
        onSuccess: (Product?) -> Unit
    ) {
        Thread {
            val result = dummyProducts.find { it.id == id }
            mainHandler.post {
                onSuccess(result)
            }
        }.start()
    }

    override fun fetchIsProductsLoadable(
        lastId: Int,
        onSuccess: (Boolean) -> Unit
    ) {
        Thread {
            val result = dummyProducts.any { it.id > lastId }
            mainHandler.post {
                onSuccess(result)
            }
        }.start()
    }
}