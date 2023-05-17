package woowacourse.shopping.data.repository

import woowacourse.shopping.data.datasource.CartDataSource
import woowacourse.shopping.domain.Cart
import woowacourse.shopping.domain.CartOrdinalProduct

class CartRepository(private val localDataSource: CartDataSource) {

    fun insertCartProduct(cartOrdinalProduct: CartOrdinalProduct) {
        localDataSource.insertCartProduct(cartOrdinalProduct)
    }

    fun selectAllCount(): Int {
        return localDataSource.selectAllCount()
    }

    fun selectAll(): Cart {
        return localDataSource.selectAll()
    }

    fun selectPage(page: Int, countPerPage: Int): Cart {
        return localDataSource.selectPage(page, countPerPage)
    }

    fun deleteCartProductByOrdinal(ordinal: Int) {
        return localDataSource.deleteCartProductByOrdinal(ordinal)
    }
}
