package woowacourse.shopping.ui.cart

import woowacourse.shopping.mapper.toUIModel
import woowacourse.shopping.model.PageUIModel
import woowacourse.shopping.repository.CartRepository
import woowacourse.shopping.repository.ProductRepository
import woowacourse.shopping.utils.NonNullLiveData
import woowacourse.shopping.utils.NonNullMutableLiveData

class CartPresenter(
    private val view: CartContract.View,
    private val cartRepository: CartRepository,
    private val productRepository: ProductRepository,
    private var index: Int = 0
) : CartContract.Presenter {
    private val _totalPrice: NonNullMutableLiveData<Int> = NonNullMutableLiveData(0)
    override val totalPrice: NonNullLiveData<Int> get() = _totalPrice

    private val _checkedCount: NonNullMutableLiveData<Int> = NonNullMutableLiveData(0)
    override val checkedCount: NonNullLiveData<Int> get() = _checkedCount

    private val currentPage get() = cartRepository.getPage(index, STEP).toUIModel()

    private val pageUIModel get() = PageUIModel(
        cartRepository.hasNextPage(index, STEP),
        cartRepository.hasPrevPage(index, STEP),
        index + 1
    )

    init {
        setBottom()
    }

    override fun setUpCarts() {
        view.setCarts(currentPage, pageUIModel)
        setAllItemCheck()
    }

    private fun setBottom() {
        _totalPrice.value = cartRepository.getTotalPrice()
        _checkedCount.value = cartRepository.getTotalSelectedCount()
    }

    private fun setAllItemCheck() {
        view.setAllItemCheck(currentPage.all { it.checked })
    }

    override fun moveToPageNext() { index += 1 ; setUpCarts() }

    override fun moveToPagePrev() { index -= 1; setUpCarts() }

    override fun setProductsCheck(checked: Boolean) {
        currentPage.forEach { cartRepository.updateChecked(it.id, checked) }
        setUpCarts()
    }

    override fun removeProduct(productId: Int) {
        cartRepository.remove(productId)
        if (currentPage.isEmpty() && index > 0) { index -= 1 }
        setUpCarts()
    }

    override fun navigateToItemDetail(productId: Int) {
        productRepository.findById(productId).let { view.navigateToItemDetail(it.toUIModel()) }
    }

    override fun getPageIndex(): Int {
        return index
    }
    override fun updateItemCount(productId: Int, count: Int) {
        cartRepository.updateCount(productId, count)
        setBottom()
    }

    override fun updateItemCheck(productId: Int, checked: Boolean) {
        cartRepository.updateChecked(productId, checked)
        setBottom()
        setAllItemCheck()
    }

    companion object {
        private const val STEP = 5
    }
}
