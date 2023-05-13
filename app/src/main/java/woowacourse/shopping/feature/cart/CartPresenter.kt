package woowacourse.shopping.feature.cart

import com.example.domain.repository.CartRepository
import woowacourse.shopping.mapper.toDomain
import woowacourse.shopping.mapper.toPresentation
import woowacourse.shopping.model.CartProductUiModel
import woowacourse.shopping.model.PageUiModel

class CartPresenter(
    private val view: CartContract.View,
    private val cartRepository: CartRepository,
) : CartContract.Presenter {

    override var page: PageUiModel = PageUiModel(cartRepository.getAll().size, 1)
        private set

    override fun loadInitCartProduct() {
        val cartProducts = cartRepository.getProducts(PageUiModel.PAGE_LOAD_SIZE)
        val items = cartProducts.map {
            it.toPresentation()
                .toItemModel { position -> view.deleteCartProductFromScreen(position) }
        }

        changePageState(items)
    }

    private fun loadCartProductFromId(startId: Long) {
        val updatedProducts = cartRepository.getProductsFromId(PageUiModel.PAGE_LOAD_SIZE, startId)
        if (updatedProducts.isEmpty() && this.page.currentPage != 1) return loadPreviousPage(startId)
        val updateItems = updatedProducts.map {
            it.toPresentation()
                .toItemModel { position -> view.deleteCartProductFromScreen(position) }
        }

        changePageState(updateItems)
    }

    override fun deleteCartProduct(cartProduct: CartProductUiModel, topId: Long) {
        cartRepository.deleteProduct(cartProduct.toDomain())
        this.page = this.page.copy(allSize = this.page.allSize - 1)

        loadCartProductFromId(topId)
    }

    override fun loadPreviousPage(topId: Long) {
        val previousProducts = cartRepository.getPreviousProducts(PageUiModel.PAGE_LOAD_SIZE, topId)
        if (previousProducts.isEmpty()) return
        val previousItems = previousProducts.map {
            it.toPresentation()
                .toItemModel { position -> view.deleteCartProductFromScreen(position) }
        }

        this.page = this.page.copy(currentPage = this.page.currentPage - 1)
        changePageState(previousItems)
    }

    override fun loadNextPage(bottomId: Long) {
        val nextProducts = cartRepository.getNextProducts(PageUiModel.PAGE_LOAD_SIZE, bottomId)
        if (nextProducts.isEmpty()) return
        val nextItems = nextProducts.map {
            it.toPresentation()
                .toItemModel { position -> view.deleteCartProductFromScreen(position) }
        }

        this.page = this.page.copy(currentPage = this.page.currentPage + 1)
        changePageState(nextItems)
    }

    override fun setPage(page: PageUiModel) {
        this.page = page
        val allCartProductsSize = page.allSize
        val startIndex = (this.page.currentPage - 1) * PageUiModel.PAGE_LOAD_SIZE
        val endIndex =
            if (startIndex + PageUiModel.PAGE_LOAD_SIZE >= allCartProductsSize) allCartProductsSize
            else startIndex + PageUiModel.PAGE_LOAD_SIZE

        val restoreCartProducts = cartRepository.getProductsByRange(startIndex, endIndex)

        val restoreItems = restoreCartProducts.map {
            it.toPresentation()
                .toItemModel { position -> view.deleteCartProductFromScreen(position) }
        }
        changePageState(restoreItems)
    }

    private fun changePageState(itemModels: List<CartProductItemModel>) {
        view.changeCartProducts(itemModels)
        view.setPreviousButtonState(this.page.hasPreviousPage())
        view.setNextButtonState(this.page.hasNextPage())
        view.setCount(this.page.currentPage)
    }
}
