package woowacourse.shopping.data.cart.repository

import android.content.Context
import model.CartProduct
import model.Count
import model.Name
import model.Price
import model.Product
import woowacourse.shopping.data.cart.datasource.CartDataSource
import woowacourse.shopping.data.cart.datasource.CartDataSourceImpl
import woowacourse.shopping.data.product.datasource.ProductDataSource
import woowacourse.shopping.data.product.datasource.ProductDataSourceImpl

class CartRepositoryImpl(
    context: Context,
    private val productDataSource: ProductDataSource = ProductDataSourceImpl(context),
    private val cartDataSource: CartDataSource = CartDataSourceImpl(context),
) : CartRepository {

    override fun getCartProducts(): List<CartProduct> {
        val cartProductsEntity = cartDataSource.getCartProducts()
        val cartProducts = cartProductsEntity.map { cartProductEntity ->
            val product = productDataSource.getProductById(cartProductEntity.id).run {
                Product(
                    id = id,
                    name = Name(name),
                    price = Price(price),
                    imageUrl = imageUrl,
                )
            }
            CartProduct(
                product = product,
                count = Count(cartProductEntity.count)
            )
        }

        return cartProducts
    }

    override fun getCartProductById(id: Int): CartProduct {
        val cartProductEntity = cartDataSource.getCartProductById(id)
        val cartProduct = productDataSource.getProductById(id).run {
            val product = Product(
                id = id,
                name = Name(name),
                price = Price(price),
                imageUrl = imageUrl,
            )

            CartProduct(
                product = product,
                count = Count(cartProductEntity.count)
            )
        }

        return cartProduct
    }

    override fun getCountOfCartProducts(): Int {

        return cartDataSource.getCountOfCartProducts()
    }

    override fun addToCart(id: Int, count: Int) {

        return cartDataSource.addToCart(id, count)
    }

    override fun removeCartProductById(id: Int) {

        return cartDataSource.removeCartProductById(id)
    }
}
