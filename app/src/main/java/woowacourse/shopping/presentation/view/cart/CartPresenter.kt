package woowacourse.shopping.presentation.view.cart

import woowacourse.shopping.data.mapper.toUIModel
import woowacourse.shopping.data.respository.cart.CartRepository
import woowacourse.shopping.data.respository.product.ProductRepositoryImpl
import woowacourse.shopping.presentation.model.CartModel

class CartPresenter(
    private val view: CartContract.View,
    private val cartRepository: CartRepository,
    private val productRepository: ProductRepositoryImpl = ProductRepositoryImpl(),
    private var currentPage: Int = 1
) : CartContract.Presenter {
    private val carts: MutableList<CartModel> =
        cartRepository.getAllCarts().map { cartEntity ->
            val product = productRepository.loadDataById(cartEntity.productId).toUIModel()
                .apply { count = cartEntity.count }

            CartModel(
                cartEntity.id,
                product,
                cartEntity.checked == 1
            )
        }.toMutableList()

    private val startPosition: Int
        get() = (currentPage - 1) * DISPLAY_CART_COUNT_CONDITION

    override fun loadCartItems() {
        view.setEnableLeftButton(currentPage != FIRST_PAGE_NUMBER)
        view.setEnableRightButton(carts.size > startPosition + DISPLAY_CART_COUNT_CONDITION)

        val newCarts = getCurrentPageCarts()
        view.setCartItemsView(newCarts)
        view.setAllCartChecked(isAllChecked())
    }

    private fun getCurrentPageCarts(): List<CartModel> {
        return carts.subList(startPosition, getCurrentPageCartLastIndex())
    }

    private fun getCurrentPageCartLastIndex(): Int =
        if (carts.size > startPosition + DISPLAY_CART_COUNT_CONDITION)
            startPosition + DISPLAY_CART_COUNT_CONDITION
        else
            carts.size

    override fun deleteCartItem(itemId: Long) {
        cartRepository.deleteCartByCartId(itemId)
        carts.removeIf { it.id == itemId }

        view.setEnableLeftButton(currentPage != FIRST_PAGE_NUMBER)
        view.setEnableRightButton(carts.size > startPosition + DISPLAY_CART_COUNT_CONDITION)

        view.setChangedCartItemsView(carts.subList(startPosition, getCurrentPageCartLastIndex()))
    }

    override fun calculatePreviousPage() {
        view.setPageCountView(--currentPage)
    }

    override fun calculateNextPage() {
        view.setPageCountView(++currentPage)
    }

    override fun calculateTotalPrice() {
        val totalPrice = carts.sumOf {
            if (it.checked)
                (it.product.price * it.product.count)
            else
                0
        }
        view.setTotalPriceView(totalPrice)
    }

    override fun updateProductCount(cartId: Long, count: Int) {
        if (count == 0) {
            deleteCartItem(cartId)
        }
        cartRepository.updateCartCountByCartId(cartId, count)
        carts.find { it.id == cartId }?.product?.count = count
    }

    override fun updateProductChecked(cartId: Long, isChecked: Boolean) {
        carts.find { it.id == cartId }?.run {
            if (checked == isChecked) return@run
            checked = isChecked
            cartRepository.updateCartCheckedByCartId(id, checked)
            view.setAllCartChecked(isAllChecked())
        }
    }

    private fun isAllChecked(): Boolean {
        return getCurrentPageCarts().all { it.checked }
    }

    override fun updateCurrentPageAllProductChecked(isChecked: Boolean) {
        for (index in startPosition until getCurrentPageCartLastIndex()) {
            updateProductChecked(carts[index].id, isChecked)
        }
        view.updateAllChecking(0, DISPLAY_CART_COUNT_CONDITION)
    }

    companion object {
        private const val FIRST_PAGE_NUMBER = 1
        private const val DISPLAY_CART_COUNT_CONDITION = 3
    }
}
