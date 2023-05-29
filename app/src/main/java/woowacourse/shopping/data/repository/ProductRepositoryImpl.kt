package woowacourse.shopping.data.repository

import com.shopping.domain.Product
import com.shopping.repository.ProductRepository
import woowacourse.shopping.data.db.MockProductService

class ProductRepositoryImpl(
    private val service: MockProductService
) : ProductRepository {
    private val products = emptyList<Product>()

    fun getAllProducts(
        onSuccess: (List<Product>) -> Unit,
        onFailure: () -> Unit
    ) {
        if (products.isEmpty()) {
            Thread {
                service.request(
                    onSuccess = onSuccess,
                    onFailure = onFailure
                )
            }.start()
        } else {
            onSuccess(products)
        }
    }

    override fun loadProducts(index: Pair<Int, Int>): List<Product> {
        if (index.first >= products.size) {
            return emptyList()
        }
        return products.subList(index.first, minOf(index.second, products.size))
    }
}
