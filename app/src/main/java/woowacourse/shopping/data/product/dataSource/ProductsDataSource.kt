package woowacourse.shopping.data.product.dataSource

import woowacourse.shopping.data.product.entity.ProductEntity

interface ProductsDataSource {
    fun load(): List<ProductEntity>

    fun getById(id: Long): ProductEntity?
}
