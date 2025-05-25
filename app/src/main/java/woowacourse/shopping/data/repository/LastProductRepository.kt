package woowacourse.shopping.data.repository

import woowacourse.shopping.domain.model.LastProduct
import woowacourse.shopping.domain.model.Product

interface LastProductRepository {
    fun fetchProducts(callback: (List<LastProduct>) -> Unit)
    fun insertProduct(product: Product)
    fun deleteLastProduct()
    fun fetchLatestProduct(callback: (Product?) -> Unit)
}