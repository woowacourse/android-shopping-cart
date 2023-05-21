package woowacourse.shopping.domain.model

import woowacourse.shopping.domain.util.safeSubList

typealias DomainCart = Cart

data class Cart(
    val cartProducts: List<CartProduct> = emptyList(),
    val loadUnit: Int,
    val minProductSize: Int = 0,
) {
    fun increaseProductCount(product: Product, count: Int = 1): Cart =
        copy(cartProducts = cartProducts
            .map { item -> if (item.product.id == product.id) item.plusCount(count) else item }
            .distinctBy { it.product.id })

    fun decreaseProductCount(product: Product, count: Int = 1): Cart =
        copy(cartProducts = cartProducts
            .map { item -> if (item.canDecreaseCount(product)) item.minusCount(count) else item }
            .filter { it.selectedCount.value >= minProductSize }
            .distinctBy { it.product.id })

    private fun CartProduct.canDecreaseCount(product: Product): Boolean =
        this.product.id == product.id && selectedCount.value > minProductSize

    fun select(product: Product): Cart =
        copy(cartProducts = cartProducts.map { item ->
            if (item.product.id == product.id) item.select() else item
        })

    fun unselect(product: Product): Cart =
        copy(cartProducts = cartProducts.map { item ->
            if (item.product.id == product.id) item.unselect() else item
        })

    fun selectAll(): Cart =
        copy(cartProducts = cartProducts.map { it.select() })

    fun unselectAll(): Cart =
        copy(cartProducts = cartProducts.map { it.unselect() })

    /* Shopping */
    fun canLoadMore(page: Page): Boolean =
        cartProducts.size >= page.value * loadUnit

    fun takeItemsUpTo(page: Page): List<CartProduct> =
        cartProducts.take(page.value * loadUnit)

    /* Cart */
    fun canLoadNextPage(page: Page): Boolean =
        cartProducts.size > page.sizePerPage

    fun takeItemsUpToPage(page: Page): List<CartProduct> =
        cartProducts.safeSubList(0, page.sizePerPage)

    fun takeCartUpToPage(page: Page): Cart =
        copy(cartProducts = cartProducts.safeSubList(0, page.sizePerPage))

    fun getCheckedSize(page: Page): Int = cartProducts
        .safeSubList(0, page.sizePerPage)
        .count { it.isChecked }

    fun update(cart: Cart): Cart =
        copy(cartProducts = cart.cartProducts.distinctBy { it.product.id })

    operator fun plus(items: Cart): Cart =
        copy(cartProducts = (cartProducts + items.cartProducts).distinctBy { it.product.id })
}
