package woowacourse.shopping.data.respository.product

import woowacourse.shopping.data.local.database.CartDao
import woowacourse.shopping.data.mapper.toUIModel
import woowacourse.shopping.presentation.model.ProductModel

class ProductRepositoryImpl(private val cartDao: CartDao) :
    ProductRepository {
    override fun getData(startPosition: Int, count: Int): List<ProductModel> {
        val productData = ProductsDao.getData(startPosition, count).map { it.toUIModel() }
        val result = productData.map {
            it.copy(count = cartDao.getItemsWithProductCount(it.id) ?: 0)
        }
        return result
    }

    override fun getProductCount(id: Long): Int {
        return cartDao.getItemsWithProductCount(productId = id) ?: 0
    }

    override fun getDataById(id: Long): ProductModel {
        return (ProductsDao.getDataById(id) ?: ProductsDao.getErrorData()).toUIModel()
    }
}
