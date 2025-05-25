package woowacourse.shopping.data.repository

import woowacourse.shopping.domain.model.Product

interface ProductRepository {
    fun fetchProducts(count: Int, lastId: Int, callback: (List<Product>) -> Unit)
    fun fetchProductDetail(id: Int, callback: (Product?) -> Unit)
    fun fetchIsProductsLoadable(lastId: Int, callback: (Boolean) -> Unit)
}
