package woowacourse.shopping.view.shoppingcart

import com.shopping.domain.Count
import com.shopping.repository.CartProductRepository
import woowacourse.shopping.model.Paging
import woowacourse.shopping.model.uimodel.CartProductUIModel
import woowacourse.shopping.model.uimodel.mapper.toDomain

class ShoppingCartPresenter(
    private val view: ShoppingCartContract.View,
    private val cartProductRepository: CartProductRepository
) : ShoppingCartContract.Presenter {
    override val paging: Paging = Paging(cartProductRepository)

    init {
        view.updateCartProduct(loadCartProducts())
        setButtonViews()
    }

    override fun loadCartProducts(): List<CartProductUIModel> =
        paging.loadPageProducts()

    override fun removeCartProduct(cartProductUIModel: CartProductUIModel) {
        cartProductRepository.remove(cartProductUIModel.toDomain())

        if (paging.isLastIndexOfCurrentPage()) {
            paging.subPage()
            view.updatePageCounter(paging.getPageCount())
        }
        setButtonViews()
        view.updateCartProduct(loadCartProducts())
    }

    override fun loadNextPage(isActivated: Boolean) {
        if (!isActivated) {
            return
        }
        paging.addPage()
        setButtonViews()
        view.updateCartProduct(loadCartProducts())
        view.updatePageCounter(paging.getPageCount())
    }

    override fun loadPreviousPage(isActivated: Boolean) {
        if (!isActivated) {
            return
        }
        paging.subPage()
        setButtonViews()
        view.updateCartProduct(loadCartProducts())
        view.updatePageCounter(paging.getPageCount())
    }

    private fun setButtonViews() {
        if (paging.isPossiblePageUp()) {
            view.activatePageUpCounter()
        } else {
            view.deactivatePageUpCounter()
        }

        if (paging.isPossiblePageDown()) {
            view.activatePageDownCounter()
        } else {
            view.deactivatePageDownCounter()
        }
    }
}
