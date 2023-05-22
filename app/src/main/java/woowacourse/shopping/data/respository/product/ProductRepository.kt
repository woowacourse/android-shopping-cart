package woowacourse.shopping.data.respository.product

import woowacourse.shopping.data.model.ProductEntity

interface ProductRepository {
    fun loadData(): List<ProductEntity>
    fun loadDataById(id: Long): ProductEntity
}
