package woowacourse.shopping.ui.cart

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
    private lateinit var currentPage: List<CartProductUIModel>

    override fun setUpCarts() {
        currentPage = cartRepository.getPage(index, STEP).toUIModel()
        view.setCarts(
            currentPage,
            PageUIModel(
                cartRepository.hasNextPage(index, STEP),
                cartRepository.hasPrevPage(index, STEP),
                index + 1
            )
        )
        setUpAllItemCheck()
    }

    private fun setUpAllItemCheck() {
        view.setAllItemCheck(currentPage.all { it.checked })
        (currentPage.count { it.checked } == currentPage.size)
    }

    override fun moveToPageNext() {
        index += 1
        setUpCarts()
    }

    override fun moveToPagePrev() {
        index -= 1
        setUpCarts()
    }

    override fun removeItem(id: Int) {
        cartRepository.remove(id)
        if (cartRepository.getPage(index, STEP).toUIModel().isEmpty()) {
            index -= 1
        }
        setUpCarts()
    }

    override fun navigateToItemDetail(productId: Int) {
        productRepository.findById(productId).let { view.navigateToItemDetail(it.toUIModel()) }
    }

    override fun getPageIndex(): Int {
        return index
    }

    override fun updateItem(productId: Int, count: Int): Int {
        val updatedCount = when {
            count > 0 -> cartRepository.updateCount(productId, count)
            else -> 1
        }
        updatePriceAndCount()
        return updatedCount
    }

    override fun updateItemCheck(productId: Int, selected: Boolean) {
        cartRepository.updateChecked(productId, selected)
        updatePriceAndCount()
        currentPage = cartRepository.getPage(index, STEP).toUIModel()
        setUpAllItemCheck()
    }

    override fun updatePriceAndCount() {
        view.updateBottom(
            cartRepository.getTotalPrice(),
            cartRepository.getTotalSelectedCount()
        )
    }

    override fun updateAllItemCheck(checked: Boolean) {
        cartRepository.updateAllChecked(index, STEP, checked)
        setUpCarts()
    }

    companion object {
        private const val STEP = 5
    }
}
