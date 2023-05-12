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
    private var _page = PageUiModel(cartRepository.getAll().size, 1)
    override val page: PageUiModel
        get() = _page.copy()

    override fun loadInitCartProduct() {
        val updatedProducts = cartRepository.getAll().take(PageUiModel.PAGE_LOAD_SIZE)
        val updateItems = updatedProducts.map {
            it.toPresentation()
                .toItemModel { position -> view.deleteCartProductFromScreen(position) }
        }

        changePageState(updateItems)
    }

    private fun loadCartProductFromId(startId: Long) {
        val updatedProducts = cartRepository.getProductFromId(PageUiModel.PAGE_LOAD_SIZE, startId)
        if (updatedProducts.isEmpty() && _page.currentPage != 1) return loadPreviousPage(startId)
        val updateItems = updatedProducts.map {
            it.toPresentation()
                .toItemModel { position -> view.deleteCartProductFromScreen(position) }
        }

        changePageState(updateItems)
    }

    override fun deleteCartProduct(cartProduct: CartProductUiModel, topId: Long) {
        cartRepository.deleteProduct(cartProduct.toDomain())
        _page = _page.copy(allSize = _page.allSize - 1)

        loadCartProductFromId(topId)
    }

    override fun loadPreviousPage(topId: Long) {
        val previousProducts = cartRepository.getPreviousProducts(PageUiModel.PAGE_LOAD_SIZE, topId)
        if (previousProducts.isEmpty()) return
        val previousItems = previousProducts.map {
            it.toPresentation()
                .toItemModel { position -> view.deleteCartProductFromScreen(position) }
        }

        _page = _page.copy(currentPage = _page.currentPage - 1)
        changePageState(previousItems)
    }

    override fun loadNextPage(bottomId: Long) {
        val nextProducts = cartRepository.getNextProducts(PageUiModel.PAGE_LOAD_SIZE, bottomId)
        if (nextProducts.isEmpty()) return
        val nextItems = nextProducts.map {
            it.toPresentation()
                .toItemModel { position -> view.deleteCartProductFromScreen(position) }
        }

        _page = _page.copy(currentPage = _page.currentPage + 1)
        changePageState(nextItems)
    }

    override fun setPage(page: PageUiModel) {
        _page = page
        val allCartProducts = cartRepository.getAll()
        val startIndex = (_page.currentPage - 1) * PageUiModel.PAGE_LOAD_SIZE
        val endIndex =
            if (startIndex + PageUiModel.PAGE_LOAD_SIZE >= allCartProducts.size) allCartProducts.size
            else startIndex + PageUiModel.PAGE_LOAD_SIZE

        val restoreCartProducts = cartRepository.getAll()
            .subList(startIndex, endIndex)

        val restoreItems = restoreCartProducts.map {
            it.toPresentation()
                .toItemModel { position -> view.deleteCartProductFromScreen(position) }
        }
        changePageState(restoreItems)
    }

    private fun changePageState(itemModels: List<CartProductItemModel>) {
        view.changeCartProducts(itemModels)
        view.setPreviousButtonState(_page.hasPreviousPage())
        view.setNextButtonState(_page.hasNextPage())
        view.setCount(_page.currentPage)
    }
}
