package woowacourse.shopping.data.respository.product

import woowacourse.shopping.presentation.model.ProductModel

interface ProductRepository {
    fun getData(startPosition: Int, count: Int, callBack: (List<ProductModel>) -> Unit)
    fun getProductCount(id: Long): Int
    fun getDataById(id: Long): ProductModel
}
