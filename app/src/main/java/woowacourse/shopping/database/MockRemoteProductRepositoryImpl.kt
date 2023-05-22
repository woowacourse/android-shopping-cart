package woowacourse.shopping.database

import model.Product
import woowacourse.shopping.database.product.ProductRepository

class MockRemoteProductRepositoryImpl(
    private val service: MockProductRemoteService,
) : ProductRepository {

    override fun loadProducts(
        lastProductId: Long,
        onSuccess: (List<Product>) -> Unit,
        onFailure: () -> Unit,
    ) {
        Thread {
            service.request(
                lastProductId = lastProductId,
                onSuccess = onSuccess,
                onFailure = onFailure,
            )
        }.start()

        Thread.sleep(10)
    }
}
