package woowacourse.shopping.domain.model

import woowacourse.shopping.domain.model.page.Page

typealias DomainCart = Cart

data class Cart(
    val items: List<CartProduct> = emptyList(),
    val minProductSize: Int = 0,
) {
    fun increaseProductCount(product: Product, count: Int = 1): Cart =
        copy(items = items
            .map { item -> if (item.product.id == product.id) item.plusCount(count) else item }
            .distinctBy { it.product.id })

    fun decreaseProductCount(product: Product, count: Int = 1): Cart =
        copy(items = items
            .map { item -> if (item.canDecreaseCount(product)) item.minusCount(count) else item }
            .filter { it.selectedCount.value >= minProductSize }
            .distinctBy { it.product.id })

    private fun CartProduct.canDecreaseCount(product: Product): Boolean =
        this.product.id == product.id && selectedCount.value > minProductSize

    fun select(product: Product): Cart =
        copy(items = items.map { item ->
            if (item.product.id == product.id) item.select() else item
        })

    fun unselect(product: Product): Cart =
        copy(items = items.map { item ->
            if (item.product.id == product.id) item.unselect() else item
        })

    fun selectAll(page: Page): Cart {
        val cartProductsOfPage = page.takeItems(this)
        return copy(items = items.map { item ->
            cartProductsOfPage.find { it.id == item.id }?.select() ?: item
        })
    }

    fun unselectAll(page: Page): Cart {
        val cartProductsOfPage = page.takeItems(this)
        return copy(items = items.map { item ->
            cartProductsOfPage.find { it.id == item.id }?.unselect() ?: item
        })
    }

    fun update(cart: Cart): Cart =
        copy(items = cart.items.distinctBy { it.product.id })

    fun update(cartProducts: List<CartProduct>): Cart =
        copy(items = cartProducts.distinctBy { it.product.id })

    fun getCheckedProductTotalPrice(): Int = items.sumOf { it.getTotalPrice(true) }

    operator fun plus(items: Cart): Cart =
        copy(items = (this.items + items.items).distinctBy { it.product.id })

    operator fun plus(items: List<CartProduct>): Cart =
        copy(items = (this.items + items).distinctBy { it.product.id })
}
