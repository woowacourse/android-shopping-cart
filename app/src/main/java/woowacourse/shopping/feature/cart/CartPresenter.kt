package woowacourse.shopping.feature.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.domain.model.CartProducts
import com.example.domain.model.Pagination
import com.example.domain.repository.CartRepository
import woowacourse.shopping.mapper.toDomain
import woowacourse.shopping.mapper.toPresentation
import woowacourse.shopping.model.CartBottomNavigationUiModel
import woowacourse.shopping.model.CartProductUiModel
import woowacourse.shopping.model.PageBottomNavigationUiModel
import woowacourse.shopping.model.PaginationUiModel

class CartPresenter(
    val view: CartContract.View,
    private val cartRepository: CartRepository,
) : CartContract.Presenter {
    private var _page: PaginationUiModel? = null
        set(value) {
            value?.let {
                _currentPageCartProducts.postValue(it.currentPageCartProducts)
                _pageBottomNavigationUiModel.postValue(it.pageBottomNavigationUiModel)
                _cartBottomNavigationUiModel.postValue(it.cartBottomNavigationUiModel)
            }
            field = value
        }
    private val page: PaginationUiModel
        get() = _page!!

    private val _currentPageCartProducts: MutableLiveData<List<CartProductUiModel>> =
        MutableLiveData()
    override val currentPageCartProducts: LiveData<List<CartProductUiModel>>
        get() = _currentPageCartProducts

    private val _pageBottomNavigationUiModel: MutableLiveData<PageBottomNavigationUiModel> =
        MutableLiveData()
    override val pageBottomNavigationUiModel: LiveData<PageBottomNavigationUiModel>
        get() = _pageBottomNavigationUiModel

    private val _cartBottomNavigationUiModel: MutableLiveData<CartBottomNavigationUiModel> =
        MutableLiveData()
    override val cartBottomNavigationUiModel: LiveData<CartBottomNavigationUiModel>
        get() = _cartBottomNavigationUiModel

    override fun loadInitCartProduct() {
        _page = Pagination(CartProducts(cartRepository.getAll()), 1).toPresentation()
    }

    override fun loadPreviousPage() {
        if (page.pageBottomNavigationUiModel.hasPreviousPage.not()) return
        _page = page.toDomain().previousPage().toPresentation()
    }

    override fun loadNextPage() {
        if (page.pageBottomNavigationUiModel.hasNextPage.not()) return
        _page = page.toDomain().nextPage().toPresentation()
    }

    override fun handleDeleteCartProductClick(cartId: Long) {
        val cartProduct = page.currentPageCartProducts.find { it.cartId == cartId } ?: return
        cartRepository.deleteProduct(cartProduct.toDomain())
        _page = page.toDomain().remove(cartId).toPresentation()
    }

    override fun handleCartProductCartCountChange(cartId: Long, count: Int) {
        val findCartProduct =
            page.currentPageCartProducts.find { it.cartId == cartId } ?: return

        _page = page.toDomain().changeCountState(cartId, count).toPresentation()

        cartRepository.changeCartProductCount(findCartProduct.productUiModel.toDomain(), count)
    }

    override fun handlePurchaseSelectedCheckedChange(cartId: Long, checked: Boolean) {
        val findCartProduct =
            page.currentPageCartProducts.find { it.cartId == cartId } ?: return
        _page = page.toDomain().changeChecked(cartId, checked).toPresentation()

        cartRepository.changeCartProductCheckedState(
            findCartProduct.productUiModel.toDomain(),
            checked
        )
    }

    override fun handleCurrentPageAllCheckedChange(checked: Boolean) {
        _page = page.toDomain().setCurrentPageAllChecked(checked).toPresentation()
        val currentIds = page.currentPageCartProducts.map { it.cartId }
        cartRepository.changeCurrentPageAllCheckedState(currentIds, checked)
    }

    override fun processOrderClick() {
        if (page.cartBottomNavigationUiModel.isAnyChecked.not()) return
        cartRepository.deleteAllCheckedCartProduct()
        _page = page.toDomain().removeAllChecked().toPresentation()
    }

    override fun setPage(restorePage: Int) {
        _page = Pagination(CartProducts(cartRepository.getAll()), restorePage).toPresentation()
    }

    override fun exit() {
        view.exitCartScreen()
    }
}
