package woowacourse.shopping.data.shoppingCart.storage

import woowacourse.shopping.data.product.entity.ProductEntity
import woowacourse.shopping.data.shoppingCart.entity.ShoppingCartProductEntity

object VolatileShoppingCartStorage : ShoppingCartStorage {
    private val products: MutableList<ShoppingCartProductEntity> = mutableListOf()
    override val quantity: Int
        get() {
            return products.sumOf { it.quantity }
        }
    override val size: Int
        get() = products.size

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

    override fun decreaseQuantity(product: ProductEntity) {
        val index = products.indexOfFirst { it.product == product }

        if (index != -1) {
            val currentShoppingCartProduct = products[index]
            val updatedQuantity: Int = currentShoppingCartProduct.quantity - 1

            if (isEmptyQuantity(updatedQuantity, product)) return

            val updatedShoppingCartProduct =
                currentShoppingCartProduct.copy(quantity = updatedQuantity)
            products[index] = updatedShoppingCartProduct
        }
    }

    private fun isEmptyQuantity(
        updatedQuantity: Int,
        product: ProductEntity,
    ): Boolean {
        if (updatedQuantity == 0) {
            remove(product)
            return true
        }
        return false
    }

    override fun remove(product: ProductEntity) {
        val updateShoppingCart = products.filterNot { it.product == product }
        products.clear()
        products.addAll(updateShoppingCart)
    }

    override fun fetchQuantity(product: ProductEntity): Int = products.find { it.product == product }?.quantity ?: 0
}
