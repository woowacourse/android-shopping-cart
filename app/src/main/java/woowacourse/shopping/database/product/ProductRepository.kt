package woowacourse.shopping.database.product

import model.Product

interface ProductRepository {

    fun loadProducts(
        lastProductId: Long,
        onSuccess: (List<Product>) -> Unit,
        onFailure: () -> Unit,
    )
}
