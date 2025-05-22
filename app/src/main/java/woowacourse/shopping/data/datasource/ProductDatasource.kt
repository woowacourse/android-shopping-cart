package woowacourse.shopping.data.datasource

import woowacourse.shopping.data.network.ProductService

class ProductDatasource(
    private val service: ProductService,
) {
    fun getProducts(productId: Long) = service.getProduct(productId)

    fun singlePage(
        fromIndex: Int,
        toIndex: Int,
    ) = service.singlePage(fromIndex, toIndex)
}
