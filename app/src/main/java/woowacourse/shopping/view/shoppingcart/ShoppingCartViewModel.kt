package woowacourse.shopping.view.shoppingcart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.data.inventory.InventoryRepository
import woowacourse.shopping.data.shoppingcart.ShoppingCartRepository
import woowacourse.shopping.domain.CartProduct
import woowacourse.shopping.domain.Page

class ShoppingCartViewModel(
    private val inventoryRepository: InventoryRepository,
    private val shoppingCartRepository: ShoppingCartRepository,
) : ViewModel() {
    private val _products = MutableLiveData<Page<CartProduct>>()
    val products: LiveData<Page<CartProduct>> get() = _products

    private val _modifiedProductIds = MutableLiveData<Set<Int>>()
    val modifiedProductIds: LiveData<Set<Int>> = _modifiedProductIds

    fun requestPage(pageIndex: Int) {
        shoppingCartRepository.getPage(PAGE_SIZE, pageIndex) { page ->
            if (page.items.isEmpty()) {
                requestPreviousPage()
            } else {
                _products.postValue(page)
            }
        }
    }

    fun requestPreviousPage() {
        val currentIndex = (_products.value?.pageIndex) ?: 0
        shoppingCartRepository.getPage(PAGE_SIZE, currentIndex - 1) { page ->
            _products.postValue(page)
        }
    }

    fun requestNextPage() {
        val currentIndex = (_products.value?.pageIndex) ?: 0
        shoppingCartRepository.getPage(PAGE_SIZE, currentIndex + 1) { page ->
            _products.postValue(page)
        }
    }

    fun increaseQuantity(
        position: Int,
        product: CartProduct,
    ) {
        val updatedItem = product.copy(quantity = product.quantity + 1)
        (_products.value?.items ?: emptyList()).toMutableList().let { newList ->
            newList[position] = updatedItem
            _products.postValue(_products.value?.copy(items = newList))
        }
        shoppingCartRepository.insert(updatedItem)
        _modifiedProductIds.postValue(_modifiedProductIds.value.orEmpty().plus(product.id))
    }

    fun decreaseQuantity(
        position: Int,
        product: CartProduct,
    ) {
        if (product.quantity == MINIMUM_CART_ITEM_QUANTITY) {
            removeCartItem(product)
            return
        }
        val updatedItem = product.copy(quantity = product.quantity - 1)
        (_products.value?.items ?: emptyList()).toMutableList().let { newList ->
            newList[position] = updatedItem
            _products.postValue(_products.value?.copy(items = newList))
        }
        shoppingCartRepository.insert(updatedItem)
        _modifiedProductIds.postValue(_modifiedProductIds.value.orEmpty().plus(product.id))
    }

    fun removeCartItem(product: CartProduct) {
        shoppingCartRepository.delete(product)
        requestPage(_products.value?.pageIndex ?: 0)
        _modifiedProductIds.postValue(_modifiedProductIds.value.orEmpty().plus(product.id))
    }

    companion object {
        private const val PAGE_SIZE = 5
        private const val MINIMUM_CART_ITEM_QUANTITY = 1

        @Suppress("UNCHECKED_CAST")
        fun createFactory(
            inventoryRepository: InventoryRepository,
            shoppingCartRepository: ShoppingCartRepository,
        ): ViewModelProvider.Factory {
            return object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return (
                        ShoppingCartViewModel(
                            inventoryRepository,
                            shoppingCartRepository,
                        ) as T
                    )
                }
            }
        }
    }
}
