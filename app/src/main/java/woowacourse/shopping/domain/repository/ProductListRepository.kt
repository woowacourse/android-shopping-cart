package woowacourse.shopping.domain.repository

import woowacourse.shopping.domain.model.PagingProduct
import woowacourse.shopping.domain.model.Product

interface ProductListRepository {
    fun getProductList(): List<Product>

    fun findProductById(id: Int): Result<Product>

    fun getProducts(
        page: Int,
        pageSize: Int,
    ): List<Product>

    fun getPagingProduct(
        page: Int,
        pageSize: Int,
    ): Result<PagingProduct>
}
