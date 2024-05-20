package woowacourse.shopping.data

import woowacourse.shopping.domain.model.Product

interface ShoppingItemsDataSource {
    fun getProductItem(id: Long): Product?

    fun getProducts(
        page: Int,
        pageSize: Int,
    ): List<Product>
}
