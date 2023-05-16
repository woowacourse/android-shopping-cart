package woowacourse.shopping.presentation.cart

import woowacourse.shopping.Products
import woowacourse.shopping.presentation.mapper.toPresentation
import woowacourse.shopping.presentation.model.ProductModel
import woowacourse.shopping.repository.CartRepository
import woowacourse.shopping.repository.ProductRepository

class CartPresenter(
    private val view: CartContract.View,
    private val cartRepository: CartRepository,
    productRepository: ProductRepository,
) : CartContract.Presenter {

    private val paging = CartOffsetPaging(cartRepository, productRepository)

    init {
        setView()
    }

    override fun deleteProduct(productModel: ProductModel) {
        cartRepository.deleteCartProductId(productModel.id)
    }

    override fun plusPage() {
        view.setPage(paging.plusPage().value)
    }

    override fun minusPage() {
        view.setPage(paging.minusPage().value)
    }

    override fun updateCart() {
        val cartProducts = Products(paging.getPageItems(paging.currentPage))
        view.setCartItems(cartProducts.toPresentation())
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
