package woowacourse.shopping.data.repository

import woowacourse.shopping.data.datasource.CartDataSource
import woowacourse.shopping.domain.Cart
import woowacourse.shopping.domain.CartProduct

class CartRepository(private val localDataSource: CartDataSource) {

    fun insertCartProduct(cartProduct: CartProduct) {
        localDataSource.insertCartProduct(cartProduct)
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
