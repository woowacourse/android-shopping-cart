package woowacourse.shopping.ui.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import woowacourse.shopping.mapper.toUIModel
import woowacourse.shopping.model.CartProductUIModel
import woowacourse.shopping.model.PageUIModel
import woowacourse.shopping.repository.CartRepository
import woowacourse.shopping.repository.ProductRepository

class CartPresenter(
    private val view: CartContract.View,
    private val cartRepository: CartRepository,
    private val productRepository: ProductRepository,
    private var index: Int = 0
) : CartContract.Presenter {
    private val _totalPrice = MutableLiveData<Int>(0)
    override val totalPrice: LiveData<Int> get() = _totalPrice

    private val _checkedCount = MutableLiveData<Int>(0)
    override val checkedCount: LiveData<Int> get() = _checkedCount

    private val _allCheck = MutableLiveData<Boolean>(false)
    override val allCheck: LiveData<Boolean> get() = _allCheck

    private val currentPage = mutableListOf<CartProductUIModel>()

    private val pageUIModel get() = PageUIModel(
        cartRepository.hasNextPage(index, STEP),
        cartRepository.hasPrevPage(index, STEP),
        index + 1
    )

    private var isChangingItemCheck = false

    override fun setUpView() {
        fetchCartProducts()
        setUpCarts()
        setUPTotalPrice()
        setUpCheckedCount()
        setUpAllButton()
    }

    override fun setUpProductsCheck(checked: Boolean) {
        if (isChangingItemCheck) { return }
        currentPage.replaceAll {
            cartRepository.updateChecked(it.id, checked)
            it.copy(checked = checked)
        }
        setUpCarts()
    }

    override fun moveToPageNext() {
        index += 1
        fetchCartProducts()
        setUpCarts()
        setUpAllButton()
    }

    override fun moveToPagePrev() {
        index -= 1
        fetchCartProducts()
        setUpCarts()
        setUpAllButton()
    }

    override fun updateItemCount(productId: Int, count: Int) {
        cartRepository.updateCount(productId, count)
        currentPage.indexOfFirst { it.id == productId }
            .takeIf { it != -1 }
            ?.let { currentPage[it] = currentPage[it].copy(count = count) }
        setUpCheckedCount()
        setUPTotalPrice()
    }

    override fun updateItemCheck(productId: Int, checked: Boolean) {
        isChangingItemCheck = true
        cartRepository.updateChecked(productId, checked)
        currentPage.indexOfFirst { it.id == productId }
            .takeIf { it != -1 }
            ?.let { currentPage[it] = currentPage[it].copy(checked = checked) }
        setUpCheckedCount()
        setUPTotalPrice()
        setUpAllButton()
        isChangingItemCheck = false
    }

    override fun removeItem(productId: Int) {
        cartRepository.remove(productId)
        currentPage.removeIf { it.id == productId }
        if (currentPage.isEmpty() && index > 0) {
            moveToPagePrev()
        } else {
            fetchCartProducts()
            setUpCarts()
            setUpCheckedCount()
            setUPTotalPrice()
        }
    }

    override fun getPageIndex(): Int {
        return index
    }

    override fun navigateToItemDetail(productId: Int) {
        view.navigateToItemDetail(
            productRepository.findById(productId).toUIModel()
        )
    }

    private fun fetchCartProducts() {
        currentPage.clear()
        currentPage.addAll(cartRepository.getPage(index, STEP).toUIModel())
    }

    private fun setUpCarts() {
        view.setPage(currentPage, pageUIModel)
    }

    private fun setUPTotalPrice() {
        _totalPrice.value = cartRepository.getTotalPrice()
    }

    private fun setUpCheckedCount() {
        _checkedCount.value = cartRepository.getTotalSelectedCount()
    }

    private fun setUpAllButton() {
        _allCheck.value = currentPage.all { it.checked }
    }

    companion object {
        private const val STEP = 5
    }
}
