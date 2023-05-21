package woowacourse.shopping.data.respository.cart

import woowacourse.shopping.data.local.database.CartDao
import woowacourse.shopping.data.respository.product.ProductsDao

class CartTotalPriceRepositoryImpl(private val cartDao: CartDao) :
    CartTotalPriceRepository {
    override fun getTotalPrice(): Int {
        val items = cartDao.getDataForTotalPrice()
        return items.sumOf {
            (ProductsDao.getDataById(it.productId) ?: ProductsDao.getErrorData())
                .price * it.count
        }
    }
}
