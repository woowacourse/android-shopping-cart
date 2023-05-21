package woowacourse.shopping.data.repository

import woowacourse.shopping.data.datasource.CartDataSource
import woowacourse.shopping.domain.CartProduct
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.Shop

class CartRepository(private val dataSource: CartDataSource) {

    fun addCartProduct(cartProduct: CartProduct) {
        dataSource.addCartProduct(cartProduct)
    }

    fun plusCartProduct(product: Product) {
        dataSource.plusCartProduct(product)
    }

    fun minusCartProduct(product: Product) {
        dataSource.minusCartProduct(product)
    }

    fun deleteCartProduct(product: Product) {
        dataSource.deleteCartProduct(product)
    }

    fun selectAllCount(): Int {
        return dataSource.selectAllCount()
    }

    fun selectAll(): Shop {
        return dataSource.selectAll()
    }
}
