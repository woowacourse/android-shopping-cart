package woowacourse.shopping.data.repository

import woowacourse.shopping.domain.model.Product

interface ProductRepository {
    fun fetchProducts(count: Int, lastId: Int, onSuccess: (List<Product>) -> Unit)
    fun fetchProductDetail(id: Int, onSuccess: (Product?) -> Unit)
    fun fetchIsProductsLoadable(lastId: Int, onSuccess: (Boolean) -> Unit)
}
