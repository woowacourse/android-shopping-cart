package woowacourse.shopping.data.cart

import android.content.Context
import woowacourse.shopping.domain.CartProduct
import woowacourse.shopping.domain.Product
import woowacourse.shopping.local.AppDatabase
import woowacourse.shopping.local.entity.CartEntity

class DefaultCartDataSource(context: Context) : CartDataSource {
    private val cartDao = AppDatabase.getInstance(context).cartDao()
    private lateinit var cart: List<CartEntity>
    private val products: List<CartProduct> get() = cart.map { it.toCartItem() }

    init {
        val thread =
            Thread {
                cart = cartDao.findAllCartItem()
            }
        thread.start()
        thread.join()
    }

    override fun totalCartProducts(): List<CartProduct> {
        var cartItems = listOf<CartProduct>()
        val thread =
            Thread {
                cartItems =
                    cartDao.findAllCartItem().map {
                        it.toCartItem()
                    }
            }
        thread.start()
        thread.join()

        return cartItems
    }

    override fun loadCartProducts(currentPage: Int): List<CartProduct> {
        if ((currentPage - 1) * PRODUCT_AMOUNT >= products.size) return emptyList()
        val startIndex = (currentPage - 1) * PRODUCT_AMOUNT
        val endIndex = (startIndex + PRODUCT_AMOUNT).coerceAtMost(products.size)
        return products.subList(startIndex, endIndex)
    }

    override fun addCartProduct(
        product: Product,
        count: Int,
    ): Long? {
        val thread =
            Thread {
                val existingEntity = cartDao.findCartItemById(product.id)

                if (existingEntity == null) {
                    val entity = CartEntity.makeCartEntity(product, count)
                    cartDao.saveItemCart(entity)
                } else {
                    val updatedEntity =
                        existingEntity.copy(
                            product = product.copy(count = count),
                        )
                    cartDao.updateCartItem(updatedEntity)
                }

                cart = cartDao.findAllCartItem()
            }
        thread.start()
        thread.join()
        return product.id
    }

    override fun deleteCartProduct(product: Product): Long? {
        val thread =
            Thread {
                cartDao.clearCartItemById(product.id)
                cart = cartDao.findAllCartItem()
            }
        thread.start()
        thread.join()

        return product.id
    }

    override fun canLoadMoreCartProducts(currentPage: Int): Boolean {
        return loadCartProducts(currentPage + 1).isNotEmpty()
    }

    companion object {
        private const val PRODUCT_AMOUNT = 5
    }
}
