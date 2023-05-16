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
        val cartProducts =
            cartRepository.getProductsByPage(page.currentPage, PageUiModel.PAGE_LOAD_SIZE)
        val items = cartProducts.map { it.toPresentation() }

        changePageState(items)
    }

    override fun deleteCartProduct(cartProduct: CartProductUiModel) {
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

        val restoreCartProducts =
            cartRepository.getProductsByPage(page.currentPage, PageUiModel.PAGE_LOAD_SIZE)
        val restoreItems = restoreCartProducts.map { it.toPresentation() }

        changePageState(restoreItems)
    }

    private fun loadCurrentPageItems(): List<CartProductUiModel> {
        val cartProducts =
            cartRepository.getProductsByPage(page.currentPage, PageUiModel.PAGE_LOAD_SIZE)

        return cartProducts.map { it.toPresentation() }
    }

    private fun changePageState(itemModels: List<CartProductUiModel>) {
        view.changeCartProducts(itemModels)
        view.setPageState(
            this.page.hasPreviousPage(),
            this.page.hasNextPage(),
            this.page.currentPage
        )
    }
}
