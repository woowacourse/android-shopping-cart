package woowacourse.shopping.presentation.shopping.product

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.data.cart.CartRepository
import woowacourse.shopping.data.shopping.ShoppingRepository
import woowacourse.shopping.presentation.base.BaseViewModelFactory
import woowacourse.shopping.presentation.shopping.toShoppingUiModel

class ProductListViewModel(
    private val shoppingRepository: ShoppingRepository,
    private val cartRepository: CartRepository,
) : ViewModel() {
    private val _products = MutableLiveData<List<ShoppingUiModel>>(emptyList())
    val products: LiveData<List<ShoppingUiModel>> = _products
    private var currentPage = 0

    fun loadProducts() {
        val currentProducts = _products.value.orEmpty().filterIsInstance<ShoppingUiModel.Product>()
        val loadProducts =
            shoppingRepository.products(currentPage++, PRODUCT_AMOUNT)
                .map { it.toShoppingUiModel(false) }

        if (shoppingRepository.canLoadMoreProducts(currentPage, PRODUCT_AMOUNT)) {
            _products.value = currentProducts + loadProducts + ShoppingUiModel.LoadMore
        } else {
            _products.value = currentProducts + loadProducts
        }
    }

    fun increaseCount(id: Long) {
        val currentProducts = _products.value?.filterIsInstance<ShoppingUiModel.Product>() ?: return
        val newProducts = currentProducts.map {
            if (it.id == id) {
                val newCount = it.count + 1
                addCartProduct(id, newCount)
                it.copy(count = newCount)
            } else {
                it
            }
        }
        if (shoppingRepository.canLoadMoreProducts(currentPage, PRODUCT_AMOUNT)) {
            _products.value = newProducts + ShoppingUiModel.LoadMore
        } else {
            _products.value = newProducts
        }
    }

    fun decreaseCount(id: Long) {
        val currentProducts = _products.value?.filterIsInstance<ShoppingUiModel.Product>() ?: return
        val newProducts = currentProducts.map {
            if (it.id == id) {
                val newCount = it.count - 1
                addCartProduct(id, newCount)
                it.copy(count = newCount)
            } else {
                it
            }
        }
        if (shoppingRepository.canLoadMoreProducts(currentPage, PRODUCT_AMOUNT)) {
            _products.value = newProducts + ShoppingUiModel.LoadMore
        } else {
            _products.value = newProducts
        }
    }

    fun addCartProduct(
        id: Long,
        count: Int,
    ) {
        cartRepository.addCartProduct(id, count)
    }

    companion object {
        private const val PRODUCT_AMOUNT: Int = 20

        fun factory(
            shopping: ShoppingRepository,
            cart: CartRepository,
        ): ViewModelProvider.Factory {
            return BaseViewModelFactory { ProductListViewModel(shopping, cart) }
        }
    }
}
