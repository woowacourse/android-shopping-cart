package woowacourse.shopping.data.shoppingCart.storage

import woowacourse.shopping.data.product.entity.ProductEntity
import woowacourse.shopping.data.shoppingCart.entity.ShoppingCartProductEntity

object VolatileShoppingCartStorage : ShoppingCartStorage {
    private val products: MutableList<ShoppingCartProductEntity> = mutableListOf()
    override val size: Int
        get() {
            return products.size
        }

    override fun load(
        offset: Int,
        limit: Int,
    ): List<ShoppingCartProductEntity> {
        if (limit > size) return products.subList(offset, size)
        return products.subList(offset, limit).toList()
    }

    override fun add(shoppingCartProductEntity: ShoppingCartProductEntity) {
        val index = products.indexOfFirst { it.product == shoppingCartProductEntity.product }

        if (index != -1) {
            val currentShoppingCartProduct = products[index]
            val updatedShoppingCartProduct =
                currentShoppingCartProduct.copy(
                    quantity = currentShoppingCartProduct.quantity + shoppingCartProductEntity.quantity,
                )
            products[index] = updatedShoppingCartProduct
        } else {
            products += shoppingCartProductEntity
        }
    }

    override fun remove(product: ProductEntity) {
        val index = products.indexOfFirst { it.product == product }

        if (index != -1) {
            val currentShoppingCartProduct = products[index]
            val updatedShoppingCartProduct =
                currentShoppingCartProduct.copy(
                    quantity = currentShoppingCartProduct.quantity - 1,
                )
            products[index] = updatedShoppingCartProduct
        }
    }
}
