package woowacourse.shopping.data.respository.product

import woowacourse.shopping.presentation.model.ProductModel

interface ProductRepository {
    fun getData(): List<ProductModel>
    fun getDataById(id: Long): ProductModel
}
