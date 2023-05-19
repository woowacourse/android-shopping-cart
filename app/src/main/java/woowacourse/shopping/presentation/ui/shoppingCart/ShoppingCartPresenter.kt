package woowacourse.shopping.presentation.ui.shoppingCart

import woowacourse.shopping.domain.PageNumber
import woowacourse.shopping.domain.model.ProductInCart
import woowacourse.shopping.domain.repository.ShoppingCartRepository

class ShoppingCartPresenter(
    private val view: ShoppingCartContract.View,
    private val shoppingCartRepository: ShoppingCartRepository,
) : ShoppingCartContract.Presenter {
    private val shoppingCart = mutableListOf<ProductInCart>()
    private var pageNumber = PageNumber()
    private val pageStartIdx: Int get() = (pageNumber.value - 1) * PAGE_UNIT
    private val pageEndIdx: Int
        get() {
            val toIndex = pageNumber.value * PAGE_UNIT - 1
            return if (toIndex >= shoppingCart.size) (shoppingCart.size - 1) else toIndex
        }

    init {
        shoppingCart.addAll(shoppingCartRepository.getAll())
    }

    private fun getAbsolutePosition(index: Int): Int {
        return (pageNumber.value - 1) * PAGE_UNIT + index
    }

    override fun getShoppingCart() {
        val pagedShoppingCart =
            if (shoppingCart.isEmpty()) {
                emptyList()
            } else {
                shoppingCart.slice(IntRange(pageStartIdx, pageEndIdx))
            }
        view.setShoppingCart(pagedShoppingCart)
    }

    override fun setPageNumber() {
        view.setPage(pageNumber.value)
    }

    private fun goOtherPage() {
        checkPageMovement()
        setPageNumber()
        getShoppingCart()
    }

    override fun goNextPage() {
        pageNumber = pageNumber.nextPage()
        goOtherPage()
    }

    override fun goPreviousPage() {
        pageNumber = pageNumber.previousPage()
        goOtherPage()
    }

    override fun checkPageMovement() {
        val size = shoppingCartRepository.getShoppingCartSize()
        val nextEnable = size > pageNumber.value * PAGE_UNIT
        val previousEnable = pageNumber.value > 1
        view.setPageButtonEnable(previousEnable, nextEnable)
    }

    override fun deleteProductInCart(index: Int) {
        val position = getAbsolutePosition(index)
        val result = shoppingCartRepository.deleteProductInCart(shoppingCart[position].product.id)
        if (result) {
            shoppingCart.removeAt(position)
            updatePageNumber()
            getShoppingCart()
        }
    }

    private fun updatePageNumber() {
        if (shoppingCart.size < pageStartIdx) goPreviousPage()
    }

    companion object {
        private const val PAGE_UNIT = 5
    }
}
