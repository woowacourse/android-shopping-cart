package woowacourse.shopping.view.shoppingcart

import android.widget.TextView
import com.shopping.domain.CartProduct
import com.shopping.repository.CartProductRepository
import woowacourse.shopping.model.Paging
import woowacourse.shopping.model.uimodel.CartProductUIModel
import woowacourse.shopping.model.uimodel.mapper.toDomain
import woowacourse.shopping.model.uimodel.mapper.toUIModel

class ShoppingCartPresenter(
    private val view: ShoppingCartContract.View,
    private val cartProductRepository: CartProductRepository
) : ShoppingCartContract.Presenter {
    override val paging: Paging = Paging(cartProductRepository.getAll())

    init {
        view.updateCartProduct(loadCartProducts())
        setButtonViews()
    }

    override fun loadCartProducts(): List<CartProductUIModel> =
        paging.loadPageProducts().map { it.toUIModel() }

    override fun removeCartProduct(cartProductUIModel: CartProductUIModel) {
        cartProductRepository.remove(cartProductUIModel.toDomain())
        paging.updateCartProducts(cartProductRepository.getAll())

        if (paging.isLastIndexOfCurrentPage()) {
            paging.subPage()
            view.updatePageCounter(paging.getPageCount())
        }
        setButtonViews()
        view.updateCartProduct(loadCartProducts())
        updateSelectedTotal()
    }

    override fun loadNextPage(isActivated: Boolean) {
        if (!isActivated) {
            return
        }
        paging.addPage()
        updatePageMove()
    }

    override fun loadPreviousPage(isActivated: Boolean) {
        if (!isActivated) {
            return
        }
        paging.subPage()
        updatePageMove()
    }

    private fun updatePageMove() {
        setButtonViews()
        view.updateCartProduct(loadCartProducts())
        view.updatePageCounter(paging.getPageCount())
        view.updateTotalCheckbox(paging.isAllItemProductSelected())
    }

    override fun updateCartProductCount(cartProductUIModel: CartProductUIModel, tvPrice: TextView) {
        cartProductRepository.update(cartProductUIModel.toDomain())
        view.updateProductItemPrice(cartProductUIModel, tvPrice)
        updateSelectedTotal()
    }

    override fun updateCartProductChecked(cartProductUIModel: CartProductUIModel) {
        cartProductRepository.update(cartProductUIModel.toDomain())
        updateSelectedTotal()
    }

    override fun changeProductsCheckedState(isSelected: Boolean) {
        paging.loadPageProducts().forEach { cartProduct ->
            cartProductRepository.update(
                CartProduct(cartProduct.product, cartProduct.count, isSelected)
            )
        }
        updateSelectedTotal()
    }

    override fun updateSelectedTotal() {
        view.updateTotalCheckbox(paging.isAllItemProductSelected())
        view.updateTotalPrice(getTotalPrice())
        view.updateTotalCount(getTotalCount())
    }

    override fun getTotalPrice(): Int {
        return cartProductRepository.getAll()
            .filter { product -> product.isSelected }
            .sumOf { product -> product.product.price * product.count.value }
    }

    override fun getTotalCount(): Int {
        return cartProductRepository.getAll()
            .filter { product -> product.isSelected }
            .sumOf { product -> product.count.value }
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
