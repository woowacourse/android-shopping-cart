package woowacourse.shopping.data

import woowacourse.shopping.data.model.ProductEntity

interface ProductRepository {
    fun getData(): List<ProductEntity>
    fun getDataById(id: Long): ProductEntity
}
