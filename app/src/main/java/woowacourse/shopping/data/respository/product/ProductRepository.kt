package woowacourse.shopping.data.respository.product

import woowacourse.shopping.data.model.ProductEntity

interface ProductRepository {
    fun getData(startPosition: Int, count: Int): List<ProductEntity>
    fun getDataById(id: Long): ProductEntity
}
