package woowacourse.shopping.data.datasource.product

import woowacourse.shopping.data.okhttp.api.ProductApi
import woowacourse.shopping.domain.datasource.ProductDataSource
import woowacourse.shopping.domain.model.Product

class RemoteProductDataSource(
    private val productApi: ProductApi,
) : ProductDataSource {
    override fun findProductById(id: Int): Result<Product> = productApi.findById(id)

    override fun getOffsetRanged(
        offset: Int,
        size: Int,
    ): Result<List<Product>> = productApi.findByOffsetAndSize(offset, size)
}
