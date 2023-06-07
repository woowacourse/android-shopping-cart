package woowacourse.shopping.data.repository

import com.example.data.repository.CartRepository
import com.example.domain.CartProduct
import com.example.domain.Product
import woowacourse.shopping.data.datasource.cartdatasource.CartDataSource

class CartRepositoryImpl(
    val cartDataSource: CartDataSource,
) : CartRepository {
    override fun getAll(): List<CartProduct> {
        return cartDataSource.getAll()
    }

    override fun getCartProducts(limit: Int, offset: Int): List<CartProduct> {
        return cartDataSource.getCartProducts(limit, offset)
    }

    override fun addColumn(product: Product, count: Int) {
        cartDataSource.addColumn(product, count)
    }

    override fun updateColumn(item: CartProduct) {
        cartDataSource.updateColumn(item)
    }

    override fun deleteColumn(cartProduct: CartProduct) {
        cartDataSource.deleteColumn(cartProduct)
    }

    override fun findProductById(id: Int): CartProduct? {
        return cartDataSource.findProductById(id)
    }
}
