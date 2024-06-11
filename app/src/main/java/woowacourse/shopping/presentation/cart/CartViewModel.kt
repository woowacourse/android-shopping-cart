package woowacourse.shopping.presentation.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.map
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.presentation.base.BaseViewModelFactory

class CartViewModel(
    private val cartRepository: CartRepository,
) : ViewModel(), CartAction{
    private val _currentPage = MutableLiveData(START_PAGE)
    val currentPage: LiveData<Int> get() = _currentPage
    val products: LiveData<List<CartProductUi>> =
        _currentPage.map { page ->
            cartRepository.cartProducts(page).map {
                it.toUiModel(true)
            }
        }
    val canLoadPrevPage: LiveData<Boolean> =
        _currentPage.map { page ->
            page > 1
        }
    val canLoadNextPage: LiveData<Boolean> =
        _currentPage.map { page -> cartRepository.canLoadMoreCartProducts(page) }

    fun moveToNextPage() {
        val currentPage = _currentPage.value ?: return
        if (cartRepository.canLoadMoreCartProducts(currentPage)) {
            refreshCurrentPage(INCREMENT_AMOUNT)
        }
    }

    fun moveToPreviousPage() {
        val currentPage = _currentPage.value ?: return
        if (currentPage > 1) {
            refreshCurrentPage(-INCREMENT_AMOUNT)
        }
    }

    private fun refreshCurrentPage(increment: Int = 0) {
        val currentPage = _currentPage.value ?: return
        _currentPage.value = currentPage + increment
    }

    override fun deleteProduct(product: CartProductUi) {
        val currentProducts = products.value ?: return
        if (currentProducts.contains(product)) {
            cartRepository.deleteCartProduct(product.product.id)
            refreshCurrentPage()
        }
    }

    override fun increaseCount(product: CartProductUi) {
        val updateItem = product.increaseCount()
        cartRepository.addCartProduct(updateItem.product.id, updateItem.count)
        refreshCurrentPage()
    }

    override fun decreaseCount(product: CartProductUi) {
        val updateItem = product.decreaseCount()
        cartRepository.addCartProduct(updateItem.product.id, updateItem.count)
        refreshCurrentPage()
    }

    companion object {
        private const val START_PAGE: Int = 1
        private const val INCREMENT_AMOUNT = 1

        fun factory(repository: CartRepository): ViewModelProvider.Factory {
            return BaseViewModelFactory { CartViewModel(repository) }
        }
    }
}
