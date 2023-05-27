package woowacourse.shopping.presentation.cart

import woowacourse.shopping.CartProductInfoList
import woowacourse.shopping.presentation.mapper.toDomain
import woowacourse.shopping.presentation.mapper.toPresentation
import woowacourse.shopping.presentation.model.CartProductInfoModel
import woowacourse.shopping.repository.CartRepository

class CartPresenter(
    private val view: CartContract.View,
    private val cartRepository: CartRepository,
    initPage: Int = DEFAULT_INIT_PAGE,
) : CartContract.Presenter {

    private val paging = CartOffsetPaging(cartRepository = cartRepository, startPage = initPage)
    private val offset: Int get() = paging.currentPage.getOffset(paging.limit)

    private var cartProducts = cartRepository.getAllCartProductsInfo()
    private val pageProducts get() = cartProducts.getItemsInRange(offset, paging.limit)

    init {
        updateOrderCount()
        updateOrderPrice()
        view.setPage(paging.currentPage.value.toString())
    }

    override fun checkPlusPageAble() {
        view.setUpPlusPageState(paging.isPlusPageAble())
    }

    override fun checkMinusPageAble() {
        view.setUpMinusPageState(paging.isMinusPageAble())
    }

    override fun addProductInOrder(cartProductModel: CartProductInfoModel) {
        cartProducts = cartProducts.updateItemOrdered(cartProductModel.toDomain(), true)
        view.setCartItems(pageProducts.toPresentationList())
    }

    private fun CartProductInfoList.toPresentationList(): List<CartProductInfoModel> {
        return this.items.map {
            it.toPresentation()
        }
    }

    override fun deleteProductInOrder(cartProductModel: CartProductInfoModel) {
        cartProducts = cartProducts.updateItemOrdered(cartProductModel.toDomain(), false)
        view.setCartItems(pageProducts.toPresentationList())
    }

    override fun updateProductCount(cartProductModel: CartProductInfoModel, count: Int) {
        cartRepository.updateCartProductCount(
            cartProductModel.productModel.id,
            count,
        )
        cartProducts = cartProducts.updateItemCount(cartProductModel.toDomain(), count)
    }

    override fun updateProductPrice(cartProductModel: CartProductInfoModel) {
        val price = cartProductModel.toDomain().totalPrice
        view.setProductPrice(price)
    }

    override fun changeCurrentPageProductsOrder(isOrdered: Boolean) {
        if (isOrdered) {
            cartProducts = cartProducts.replaceItemList(pageProducts.updateAllItemOrdered(true))
        } else if (!isOrdered && pageProducts.isAllOrdered) {
            cartProducts = cartProducts.replaceItemList(pageProducts.updateAllItemOrdered(false))
        }
        view.setCartItems(pageProducts.toPresentationList())
    }

    override fun checkCurrentPageProductsOrderState() {
        if (pageProducts.isAllOrdered) {
            view.setAllOrderState(true)
        } else {
            view.setAllOrderState(false)
        }
    }

    override fun updateOrderPrice() {
        val price = cartProducts.orders.totalPrice
        view.setOrderPrice(price)
    }

    override fun updateOrderCount() {
        val count = cartProducts.orders.count
        view.setOrderCount(count)
    }

    override fun deleteProductItem(cartProductModel: CartProductInfoModel) {
        cartRepository.deleteCartProductId(cartProductModel.productModel.id)
        cartProducts = cartProducts.delete(cartProductModel.toDomain())
        view.setCartItems(pageProducts.toPresentationList())
    }

    override fun plusPage() {
        paging.plusPage()
        view.setPage(paging.currentPage.value.toString())
        view.setCartItems(pageProducts.toPresentationList())
    }

    override fun minusPage() {
        paging.minusPage()
        view.setPage(paging.currentPage.value.toString())
        view.setCartItems(pageProducts.toPresentationList())
    }

    override fun refreshCurrentPage() {
        view.setCartItems(pageProducts.toPresentationList())
    }

    companion object {
        private const val DEFAULT_INIT_PAGE = 1
    }
}
