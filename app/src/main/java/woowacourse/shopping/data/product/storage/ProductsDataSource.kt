package woowacourse.shopping.data.product.storage

import woowacourse.shopping.data.product.entity.ProductEntity

interface ProductsDataSource {
    fun load(): List<ProductEntity>
}
