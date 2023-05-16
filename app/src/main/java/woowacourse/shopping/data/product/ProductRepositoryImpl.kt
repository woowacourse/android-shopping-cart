package woowacourse.shopping.data.product

import woowacourse.shopping.domain.Products
import woowacourse.shopping.domain.repository.ProductRepository

class ProductRepositoryImpl(private val localProductDataSource: ProductDataSource) : ProductRepository {
    override fun getProducts(startIndex: Int, size: Int): Products {
        return localProductDataSource.getByRange(start = startIndex, range = size)
    }
}
