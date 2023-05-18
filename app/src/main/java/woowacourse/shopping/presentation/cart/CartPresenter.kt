package woowacourse.shopping.presentation.cart

import woowacourse.shopping.CartProductInfoList
import woowacourse.shopping.Products
import woowacourse.shopping.presentation.mapper.toPresentation
import woowacourse.shopping.presentation.model.CartProductInfoModel
import woowacourse.shopping.presentation.model.ProductModel
import woowacourse.shopping.repository.CartRepository

class CartPresenter(
    private val view: CartContract.View,
    private val cartRepository: CartRepository,
) : CartContract.Presenter {

    private val paging = CartOffsetPaging(cartRepository)

    init {
        setView()
    }

    override fun deleteProduct(cartProductInfoModel: CartProductInfoModel) {
        cartRepository.deleteCartProductId(cartProductInfoModel.productModel.id)
    }

    override fun plusPage() {
        view.setPage(paging.plusPage().value)
    }

    override fun minusPage() {
        view.setPage(paging.minusPage().value)
    }

    override fun updateCart() {
        val cartProductsInfo = CartProductInfoList(paging.getPageItems(paging.currentPage))
        val cartProductInfoModels = cartProductsInfo.items.map { it.toPresentation() }
        view.setCartItems(cartProductInfoModels)
    }

    override fun updatePlusButtonState() {
        view.setUpPlusPageButtonState(paging.isPlusPageAble())
    }

    override fun updateMinusButtonState() {
        view.setUpMinusPageButtonState(paging.isMinusPageAble())
    }

    private fun setView() {
        view.setPage(paging.currentPage.value)
        updateCart()
        updatePlusButtonState()
        updateMinusButtonState()
    }

    private fun Products.toPresentation(): List<ProductModel> {
        return items.map { it.toPresentation() }
    }
}
