package woowacourse.shopping.database

import model.Product
import woowacourse.shopping.database.product.ProductRepository
import java.util.concurrent.CountDownLatch

class MockRemoteProductRepositoryImpl(
    private val service: MockProductRemoteService,
) : ProductRepository {

    override fun loadProducts(
        lastProductId: Long,
        onSuccess: (List<Product>) -> Unit,
        onFailure: () -> Unit,
    ) {
        val latch = CountDownLatch(1)
        Thread {
            service.request(
                lastProductId = lastProductId,
                onSuccess = onSuccess,
                onFailure = onFailure,
            )
            latch.countDown()
        }.start()

        latch.await()
    }
}
