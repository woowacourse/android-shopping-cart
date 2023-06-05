package woowacourse.shopping.data.product

import com.example.domain.Product
import com.example.domain.repository.ProductRepository

class MockRemoteProductRepositoryImpl(
    private val service: MockProductRemoteService
) : ProductRepository {

    override fun fetchNextProducts(
        lastProductId: Long,
        onSuccess: (List<Product>) -> Unit,
        onFailure: () -> Unit
    ) {
        Thread {
            service.requestProductsUnit(
                lastProductId = lastProductId.toLong(),
                onSuccess = onSuccess,
                onFailure = onFailure
            )
        }.start()
    }
}
