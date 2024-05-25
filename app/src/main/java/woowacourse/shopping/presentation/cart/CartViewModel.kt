package woowacourse.shopping.presentation.cart

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.map
import woowacourse.shopping.data.cart.CartRepository
import woowacourse.shopping.presentation.base.BaseViewModelFactory

class CartViewModel(
    private val cartRepository: CartRepository,
) : ViewModel() {
    private val _currentPage = MutableLiveData(START_PAGE)
    val currentPage: LiveData<Int> get() = _currentPage

    // todo 캐싱 update
    private val cachedProducts = mutableMapOf<Int, List<CartProductUi>>()
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

    fun deleteProduct(item: CartProductUi) {
        val currentProducts = products.value ?: return
        if (currentProducts.contains(item)) {
            cartRepository.deleteCartProduct(item.product.id)
            refreshCurrentPage()
        }
    }

    fun increaseCount(item: CartProductUi) {
        val updateItem = item.increaseCount()
        Log.d("cart increase", "${updateItem.count}")
        cartRepository.addCartProduct(updateItem.product.id, updateItem.count)
        refreshCurrentPage()
    }

    fun decreaseCount(item: CartProductUi) {
        val updateItem = item.decreaseCount()
        when (item.product.count == 1) {
            true -> deleteProduct(item)
            false -> cartRepository.addCartProduct(updateItem.product.id, updateItem.count)
        }
        refreshCurrentPage()
    }

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

    companion object {
        private const val START_PAGE: Int = 1
        private const val INCREMENT_AMOUNT = 1

        fun factory(repository: CartRepository): ViewModelProvider.Factory {
            return BaseViewModelFactory { CartViewModel(repository) }
        }
    }
}
