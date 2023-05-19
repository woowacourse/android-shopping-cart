package woowacourse.shopping.presentation.ui.shoppingCart

import woowacourse.shopping.domain.PageNumber
import woowacourse.shopping.domain.model.Operator
import woowacourse.shopping.domain.model.ProductInCart
import woowacourse.shopping.domain.repository.ShoppingCartRepository
import woowacourse.shopping.domain.util.WoowaResult

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

    override fun goNextPage() {
        pageNumber = pageNumber.nextPage()
        goOtherPage()
    }

    override fun goPreviousPage() {
        pageNumber = pageNumber.previousPage()
        goOtherPage()
    }

    private fun goOtherPage() {
        checkPageMovement()
        setPageNumber()
        getShoppingCart()
    }

    override fun setPageNumber() {
        view.setPage(pageNumber.value)
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
            setOrderCount()
            setPayment()
        }
    }

    private fun updatePageNumber() {
        if (shoppingCart.size < pageStartIdx) goPreviousPage()
    }

    override fun changeSelection(index: Int, isSelected: Boolean) {
        val position = getAbsolutePosition(index)
        shoppingCart[position] = shoppingCart[position].copy(isChecked = isSelected)
        updateView()
    }

    override fun selectAll(isSelected: Boolean) {
        for (idx in pageStartIdx..pageEndIdx) {
            shoppingCart[idx] = shoppingCart[idx].copy(isChecked = isSelected)
        }
        updateView()
    }

    override fun updateProductQuantity(index: Int, operator: Operator) {
        val position = getAbsolutePosition(index)
        val productInCart = operator.operate(shoppingCart[position])
        val result = shoppingCartRepository.updateProductCount(
            productInCart.product.id,
            productInCart.quantity,
        )
        when (result) {
            is WoowaResult.SUCCESS -> {
                shoppingCart[position] = productInCart
                updateView()
            }
            is WoowaResult.FAIL -> {
                view.showUnExpectedError()
            }
        }
    }

    private fun updateView() {
        getShoppingCart()
        setOrderCount()
        setPayment()
        setAllCheck()
    }

    override fun setAllCheck() {
        val allChecked: Boolean = shoppingCart
            .slice(IntRange(pageStartIdx, pageEndIdx))
            .all { it.isChecked }
        view.updateAllCheck(allChecked)
    }

    override fun setPayment() {
        val payment = shoppingCart.asSequence()
            .filter { it.isChecked }
            .sumOf { it.quantity * it.product.price }
        view.updatePayment(payment)
    }

    override fun showProductDetail(index: Int) {
        view.goProductDetailActivity(shoppingCart[getAbsolutePosition(index)])
    }

    override fun setOrderCount() {
        val orderCount = shoppingCart.asSequence()
            .filter { it.isChecked }
            .sumOf { it.quantity }
        view.updateOrder(orderCount)
    }

    companion object {
        private const val PAGE_UNIT = 5
    }
}
