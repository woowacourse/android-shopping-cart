package woowacourse.shopping.feature.cart

import com.example.domain.repository.CartRepository
import woowacourse.shopping.mapper.toDomain
import woowacourse.shopping.mapper.toPresentation
import woowacourse.shopping.model.CartProductUiModel
import woowacourse.shopping.model.PageUiModel

class CartPresenter(
    val view: CartContract.View,
    private val cartRepository: CartRepository,
) : CartContract.Presenter {
    private lateinit var _page: PageUiModel
    override val page: PageUiModel
        get() = _page.copy()

    private val currentPageCartProductUiModel: MutableList<CartProductUiModel> = mutableListOf()
    private val firstIdInCurrentPage: Long?
        get() = currentPageCartProductUiModel.firstOrNull()?.cartId
    private val lastIdInCurrentPage: Long?
        get() = currentPageCartProductUiModel.lastOrNull()?.cartId

    private fun updatePageItemsInfo(cartProducts: List<CartProductUiModel>) {
        currentPageCartProductUiModel.clear()
        currentPageCartProductUiModel.addAll(cartProducts)
    }

    override fun loadInitCartProduct() {
        _page = PageUiModel(cartRepository.getAll().size, 1)
        val initCartProducts = cartRepository.getInitPageProducts(PageUiModel.PAGE_LOAD_SIZE)
        val initCartProductUiModels = initCartProducts.map { it.toPresentation() }
        updatePageItemsInfo(initCartProductUiModels)

        changePageState(initCartProductUiModels)
    }

    override fun loadPreviousPage() {
        val previousCartProducts =
            cartRepository.getPreviousProducts(PageUiModel.PAGE_LOAD_SIZE, firstIdInCurrentPage)
        if (previousCartProducts.isEmpty()) return
        val previousCartProductUiModels = previousCartProducts.map { it.toPresentation() }
        updatePageItemsInfo(previousCartProductUiModels)

        _page = _page.copy(currentPage = _page.currentPage - 1)
        changePageState(previousCartProductUiModels)
    }

    override fun loadNextPage() {
        val nextCartProducts =
            cartRepository.getNextProducts(PageUiModel.PAGE_LOAD_SIZE, lastIdInCurrentPage)
        if (nextCartProducts.isEmpty()) return
        val nextCartProductUiModels = nextCartProducts.map { it.toPresentation() }
        updatePageItemsInfo(nextCartProductUiModels)

        _page = _page.copy(currentPage = _page.currentPage + 1)
        changePageState(nextCartProductUiModels)
    }

    override fun deleteCartProduct(cartProduct: CartProductUiModel) {
        cartRepository.deleteProduct(cartProduct.toDomain())
        _page = _page.copy(allSize = _page.allSize - 1)
        val updateCartProducts = if (currentPageCartProductUiModel.size == 1) {
            if (page.currentPage != 1) _page = _page.copy(currentPage = _page.currentPage - 1)
            cartRepository.getPreviousProducts(PageUiModel.PAGE_LOAD_SIZE, firstIdInCurrentPage)
        } else {
            cartRepository.getPageCartProductsFromFirstId(
                PageUiModel.PAGE_LOAD_SIZE,
                firstIdInCurrentPage
            )
        }

        val updateCartProductUiModels = updateCartProducts.map { it.toPresentation() }
        updatePageItemsInfo(updateCartProductUiModels)
        changePageState(updateCartProductUiModels)
    }

    override fun setPage(page: PageUiModel) {
        _page = page
        val restoreCartProducts =
            cartRepository.getCartProductsFromPage(PageUiModel.PAGE_LOAD_SIZE, _page.currentPage)
        val restoreCartProductUiModels = restoreCartProducts.map { it.toPresentation() }

        updatePageItemsInfo(restoreCartProductUiModels)
        changePageState(restoreCartProductUiModels)
    }

    private fun changePageState(cartProductUiModels: List<CartProductUiModel>) {
        val itemModels = cartProductUiModels.map {
            it.toItemModel { position -> view.deleteCartProductFromScreen(position) }
        }
        view.changeCartProducts(itemModels)
        view.setPreviousButtonState(_page.hasPreviousPage())
        view.setNextButtonState(_page.hasNextPage())
        view.setCount(_page.currentPage)
    }
}
