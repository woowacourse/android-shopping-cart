package woowacourse.shopping.data.respository.product

import woowacourse.shopping.presentation.model.ProductModel

interface ProductRepository {
    fun getData(startPosition: Int, count: Int): List<ProductModel>
    fun getDataById(id: Long): ProductModel
}
