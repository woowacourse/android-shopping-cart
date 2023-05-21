package woowacourse.shopping.data.respository.product

import woowacourse.shopping.presentation.model.ProductModel

interface ProductRepository {
    fun getData(startPosition: Int, count: Int): List<ProductModel>
    fun getProductCount(id: Long): Int
    fun getDataById(id: Long): ProductModel
}
