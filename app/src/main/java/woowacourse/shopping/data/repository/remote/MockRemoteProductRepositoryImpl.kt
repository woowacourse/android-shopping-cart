package woowacourse.shopping.data.repository.remote

import com.example.domain.cache.ProductCache
import com.example.domain.cache.ProductLocalCache
import com.example.domain.model.Product
import com.example.domain.repository.ProductRepository
import woowacourse.shopping.data.service.MockProductRemoteService

class MockRemoteProductRepositoryImpl(
    private val service: MockProductRemoteService,
    override val cache: ProductCache = ProductLocalCache
) : ProductRepository {
    override fun fetchFirstProducts(
        onSuccess: (List<Product>) -> Unit,
        onFailure: () -> Unit
    ) {
        if (cache.productList.isEmpty()) {
            Thread {
                service.request(
                    lastProductId = 0,
                    onSuccess = {
                        cache.addProducts(it)
                        onSuccess(it)
                    },
                    onFailure = onFailure
                )
            }.start()
        } else {
            onSuccess(cache.productList)
        }
    }

    override fun fetchNextProducts(
        lastProductId: Long,
        onSuccess: (List<Product>) -> Unit,
        onFailure: () -> Unit
    ) {
        Thread {
            service.request(
                lastProductId = lastProductId,
                onSuccess = {
                    cache.addProducts(it)
                    onSuccess(it)
                },
                onFailure = onFailure
            )
        }.start()
    }

    override fun resetCache() {
        cache.clear()
    }
}
