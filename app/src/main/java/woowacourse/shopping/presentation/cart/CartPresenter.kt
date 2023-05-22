package woowacourse.shopping.presentation.cart

import woowacourse.shopping.CartProductInfoList
import woowacourse.shopping.presentation.mapper.toPresentation
import woowacourse.shopping.repository.CartRepository
import woowacourse.shopping.util.SafeLiveData
import woowacourse.shopping.util.SafeMutableLiveData

class CartPresenter(
    private val view: CartContract.View,
    private val cartRepository: CartRepository,
    initCartProductList: CartProductInfoList = CartProductInfoList(listOf()),
    initPage: Int = DEFAULT_INIT_PAGE,
) : CartContract.Presenter {

    override val paging = CartOffsetPaging(cartRepository = cartRepository, startPage = initPage)
    private val offset: Int get() = paging.currentPage.value.getOffset(paging.limit)

    private val _loadedCartProducts = SafeMutableLiveData(initCartProductList)
    override val loadedCartProducts: SafeLiveData<CartProductInfoList> get() = _loadedCartProducts

    private val _pageProducts = SafeMutableLiveData(initCartProductList)
    override val pageProducts: SafeLiveData<CartProductInfoList> get() = _pageProducts

    init {
        initView()
    }

    private fun initView() {
        checkPlusPageAble()
        checkMinusPageAble()
    }

    override fun checkPlusPageAble() {
        view.setUpPlusPageState(paging.isPlusPageAble())
    }

    override fun checkMinusPageAble() {
        view.setUpMinusPageState(paging.isMinusPageAble())
    }

    override fun addProductInOrder(position: Int) {
        if (position == PREVIOUS_PAGE_POSITION) return
        val index = offset + position
        _loadedCartProducts.value =
            _loadedCartProducts.value.updateItemOrdered(index, true)
        updateCurrentPageProducts()
    }

    private fun updateCurrentPageProducts() {
        _pageProducts.value =
            loadedCartProducts.value.getItemsInRange(
                offset,
                paging.limit,
            )
    }

    override fun deleteProductInOrder(position: Int) {
        if (position == PREVIOUS_PAGE_POSITION) return
        val index = offset + position
        _loadedCartProducts.value =
            _loadedCartProducts.value.updateItemOrdered(index, false)
        updateCurrentPageProducts()
    }

    override fun updateProductCount(position: Int, count: Int) {
        val productInfo = pageProducts.value.items[position]
        cartRepository.updateCartProductCount(
            productInfo.product.id,
            count,
        )
        val index = offset + position
        _loadedCartProducts.value = _loadedCartProducts.value.updateItemCount(index, count)
        updateCurrentPageProducts()
    }

    override fun changeCurrentPageProductsOrder() {
        if (pageProducts.value.isAllOrdered) {
            _pageProducts.value = _pageProducts.value.updateAllItemOrdered(false)
        } else {
            _pageProducts.value = _pageProducts.value.updateAllItemOrdered(true)
        }
        _loadedCartProducts.value = _loadedCartProducts.value.replaceItemList(_pageProducts.value)
    }

    override fun deleteProductItem(position: Int) {
        val productInfo = pageProducts.value.items[position]
        cartRepository.deleteCartProductId(productInfo.product.id)
        _loadedCartProducts.value = _loadedCartProducts.value.delete(productInfo)
    }

    override fun plusPage() {
        paging.plusPage()
    }

    override fun minusPage() {
        paging.minusPage()
    }

    override fun loadCurrentPageProducts() {
        val currentPage = paging.currentPage.value
        _loadedCartProducts.value =
            _loadedCartProducts.value.addAll(paging.loadPageItems(currentPage))
    }

    override fun updateCurrentPageCartView() {
        updateCurrentPageProducts()
        val cartProductInfoModels = pageProducts.value.items.map { it.toPresentation() }
        view.setCartItems(cartProductInfoModels)
    }

    companion object {
        private const val DEFAULT_INIT_PAGE = 1
        private const val PREVIOUS_PAGE_POSITION = -1
    }
}
