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
        changePageState(getCurrentPageItems())
    }

    override fun deleteCartProduct(cartProduct: CartProductUiModel) {
        cartRepository.deleteProduct(cartProduct.productUiModel.toDomain())
        this.page = this.page.copy(allSize = this.page.allSize - 1)

        var loadedItems = getCurrentPageItems()
        if (loadedItems.isEmpty() && this.page.currentPage != 1) {
            this.page = this.page.previousPage()
            loadedItems = getCurrentPageItems()
        }
        changePageState(loadedItems)
    }

    override fun loadPreviousPage() {
        this.page = this.page.previousPage()

        val loadedItems = getCurrentPageItems()
        changePageState(loadedItems)
    }

    override fun loadNextPage() {
        this.page = this.page.nextPage()

        val loadedItems = getCurrentPageItems()
        changePageState(loadedItems)
    }

    override fun setPage(page: PageUiModel) {
        this.page = page
        changePageState(getCurrentPageItems())
    }

    private fun getCurrentPageItems(): List<CartProductUiModel> {
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
