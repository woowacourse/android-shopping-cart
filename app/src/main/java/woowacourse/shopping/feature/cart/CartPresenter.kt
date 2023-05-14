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
                .toItemModel { cartProduct -> deleteCartProduct(cartProduct) }
        }

        changePageState(items)
    }

    private fun deleteCartProduct(cartProduct: CartProductUiModel) {
        cartRepository.deleteProduct(cartProduct.toDomain())
        this.page = this.page.copy(allSize = this.page.allSize - 1)

        var loadedItems = loadCurrentPageItems()
        if (loadedItems.isEmpty() && this.page.currentPage != 1) {
            this.page = this.page.copy(currentPage = this.page.currentPage - 1)
            loadedItems = loadCurrentPageItems()
        }
        changePageState(loadedItems)
    }

    override fun loadPreviousPage() {
        this.page = this.page.copy(currentPage = this.page.currentPage - 1)

        val loadedItems = loadCurrentPageItems()
        changePageState(loadedItems)
    }

    override fun loadNextPage() {
        this.page = this.page.copy(currentPage = this.page.currentPage + 1)

        val loadedItems = loadCurrentPageItems()
        changePageState(loadedItems)
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
                .toItemModel { cartProduct -> deleteCartProduct(cartProduct) }
        }
        changePageState(restoreItems)
    }

    private fun loadCurrentPageItems(): List<CartProductItemModel> {
        val allCartProductsSize = page.allSize
        val startIndex = (page.currentPage - 1) * PageUiModel.PAGE_LOAD_SIZE
        val endIndex =
            if (startIndex + PageUiModel.PAGE_LOAD_SIZE >= allCartProductsSize) allCartProductsSize
            else startIndex + PageUiModel.PAGE_LOAD_SIZE

        val cartProducts = cartRepository.getProductsByRange(startIndex, endIndex)

        val cartProductItems = cartProducts.map {
            it.toPresentation()
                .toItemModel { cartProduct -> deleteCartProduct(cartProduct) }
        }
        return cartProductItems
    }

    private fun changePageState(itemModels: List<CartProductItemModel>) {
        view.changeCartProducts(itemModels)
        view.setPreviousButtonState(this.page.hasPreviousPage())
        view.setNextButtonState(this.page.hasNextPage())
        view.setCount(this.page.currentPage)
    }
}
