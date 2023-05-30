package woowacourse.shopping.data.repository

import android.util.Log
import com.shopping.domain.Product
import com.shopping.repository.ProductRepository
import woowacourse.shopping.data.db.ProductService

class ProductRepositoryImpl(
    private val service: ProductService
) : ProductRepository {

    override fun loadProducts(
        index: Pair<Int, Int>,
        onSuccess: (List<Product>) -> Unit,
        onFailure: () -> Unit
    ) {

        Thread {
            service.request(
                onSuccess = { products ->
                    if (index.first >= products.size) {
                        onSuccess(emptyList())
                    }
                    onSuccess(products.subList(index.first, minOf(index.second, products.size)))
                },
                onFailure = onFailure
            )
        }.start()
    }
}
