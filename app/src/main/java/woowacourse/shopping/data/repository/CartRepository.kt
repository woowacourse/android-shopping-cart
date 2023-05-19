package woowacourse.shopping.data.repository

import woowacourse.shopping.data.datasource.CartDataSource
import woowacourse.shopping.domain.CartProduct
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.Shop

class CartRepository(private val localDataSource: CartDataSource) {

    fun addCartProduct(cartProduct: CartProduct) {
        localDataSource.addCartProduct(cartProduct)
    }

    fun plusCartProduct(product: Product) {
        localDataSource.plusCartProduct(product)
    }

    fun minusCartProduct(product: Product) {
        localDataSource.minusCartProduct(product)
    }

    fun deleteCartProduct(product: Product) {
        localDataSource.deleteCartProduct(product)
    }

    fun selectAllCount(): Int {
        return localDataSource.selectAllCount()
    }

    fun selectAll(): Shop {
        return localDataSource.selectAll()
    }
}
