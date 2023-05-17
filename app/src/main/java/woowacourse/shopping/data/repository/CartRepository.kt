package woowacourse.shopping.data.repository

import woowacourse.shopping.data.datasource.CartDataSource
import woowacourse.shopping.domain.Cart
import woowacourse.shopping.domain.Product

class CartRepository(private val localDataSource: CartDataSource) {

    fun insertCartProduct(product: Product) {
        localDataSource.insertCartProduct(product)
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

    fun deleteCartProductByProduct(product: Product) {
        return localDataSource.deleteCartProductByOrdinal(product)
    }
}
