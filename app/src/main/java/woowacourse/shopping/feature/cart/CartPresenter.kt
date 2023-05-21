package woowacourse.shopping.feature.cart

import com.example.domain.model.CartProducts
import com.example.domain.model.Pagination
import com.example.domain.repository.CartRepository
import woowacourse.shopping.mapper.toDomain
import woowacourse.shopping.mapper.toPresentation
import woowacourse.shopping.model.PaginationUiModel
import woowacourse.shopping.util.convertToMoneyFormat

class CartPresenter(
    val view: CartContract.View,
    private val cartRepository: CartRepository,
) : CartContract.Presenter {
    override lateinit var page: PaginationUiModel
        private set

    override fun loadInitCartProduct() {
        page = Pagination(CartProducts(cartRepository.getAll()), 1).toPresentation()

        changePageState()
    }

    override fun loadPreviousPage() {
        if (page.hasPreviousPage.not()) return
        page = page.toDomain().previousPage().toPresentation()

        changePageState()
    }

    override fun loadNextPage() {
        if (page.hasNextPage.not()) return
        page = page.toDomain().nextPage().toPresentation()

        changePageState()
    }

    override fun handleDeleteCartProductClick(cartId: Long) {
        val cartProduct = page.currentPageCartProducts.find { it.cartId == cartId } ?: return
        cartRepository.deleteProduct(cartProduct.toDomain())
        page = page.toDomain().remove(cartId).toPresentation()
        changePageState()
    }

    override fun handleCartProductCartCountChange(cartId: Long, count: Int) {
        val findCartProduct =
            page.currentPageCartProducts.find { it.cartId == cartId } ?: return

        page = page.toDomain().changeCountState(cartId, count).toPresentation()

        cartRepository.changeCartProductCount(findCartProduct.productUiModel.toDomain(), count)

        changePageState()
    }

    override fun handlePurchaseSelectedCheckedChange(cartId: Long, checked: Boolean) {
        val findCartProduct =
            page.currentPageCartProducts.find { it.cartId == cartId } ?: return
        page = page.toDomain().changeChecked(cartId, checked).toPresentation()

        cartRepository.changeCartProductCheckedState(
            findCartProduct.productUiModel.toDomain(),
            checked
        )

        changePageState()
    }

    override fun handleCurrentPageAllCheckedChange(checked: Boolean) {
        page = page.toDomain().setCurrentPageAllChecked(checked).toPresentation()
        val currentIds = page.currentPageCartProducts.map { it.cartId }
        cartRepository.changeCurrentPageAllCheckedState(currentIds, checked)
        changePageState()
    }

    override fun processOrderClick() {
        if (page.isAnyChecked.not()) return
        cartRepository.deleteAllCheckedCartProduct()
        page = page.toDomain().removeAllChecked().toPresentation()
        changePageState()
    }

    override fun setPage(restorePage: Int) {
        page = Pagination(CartProducts(cartRepository.getAll()), restorePage).toPresentation()
        changePageState()
    }

    override fun exit() {
        view.exitCartScreen()
    }

    private fun changePageState() {
        view.updateCartProducts(page.currentPageCartProducts)
        view.setPreviousButtonState(page.hasPreviousPage)
        view.setNextButtonState(page.hasNextPage)
        view.setPageCount(page.currentPage)

        if (page.checkedCount > 0) {
            view.setOrderButtonState(true, page.checkedCount)
        } else {
            view.setOrderButtonState(false, page.checkedCount)
        }

        view.setTotalMoney(convertToMoneyFormat(page.totalCheckedMoney))
        view.setAllCheckedButtonState(page.currentPageCartProducts.all { it.checked })
    }
}
