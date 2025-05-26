package woowacourse.shopping.ui.productlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.domain.cart.CartProduct
import woowacourse.shopping.domain.product.ProductOverViewRepository
import woowacourse.shopping.providers.RepositoryProvider

class ProductListViewModel(
    private val repository: ProductOverViewRepository,
) : ViewModel() {
    private val _productsUiState = MutableLiveData(ProductListUiModel())
    val productsUiState: LiveData<ProductListUiModel> get() = _productsUiState

    init {
        loadInfos()
    }

    fun loadInfos() {
        val pageNumber = _productsUiState.value?.pageNumber ?: 0
        repository.findInRange(
            limit = PAGE_FETCH_SIZE,
            offset = (pageNumber) * PAGE_SIZE
        ) { cartProductsResult ->
            cartProductsResult.mapCatching { cartProducts ->
                val newCartProducts = cartProducts.take(PAGE_SIZE)
                val newProductsViewType = newCartProducts.map { cartProduct ->
                    ProductListViewType.ProductItemType(
                        cartProduct.product,
                        cartProduct.quantity
                    )
                }
                val isAddLoadMore = hasNextPage(cartProductsResult.getOrNull())
                _productsUiState.postValue(
                    productsUiState.value?.addProducts(
                        newProductsViewType,
                        isAddLoadMore
                    )
                )
            }.onFailure {
                // TODO : 데이터 가져오기 실패
            }
        }
    }

    fun increaseQuantity(productId: Long, delta: Int) {
        repository.insertOrAddQuantity(productId, delta) { result ->
            result.onSuccess {
                _productsUiState.postValue(
                    productsUiState.value?.updateQuantityByProductId(productId, delta)
                )
            }.onFailure {
                // TODO : 데이터 가져오기 실패
            }
        }
    }

    fun decreaseQuantity(productId: Long, delta: Int) {
        val uiStateValue = productsUiState.value ?: return
        val quantity = uiStateValue.getQuantityByProductId(productId)
        if (quantity + delta <= 0) {
            repository.removeInCart(productId) { result ->
                result.onSuccess {
                    _productsUiState.postValue(
                        uiStateValue.updateQuantityByProductId(productId, delta)
                    )
                }
                    .onFailure {
                        // TODO : 데이터 가져오기 실패 }
                    }
            }
            return
        }

        repository.updateQuantityByProductId(productId, delta) { result ->
            result.onSuccess {
                _productsUiState.postValue(
                    uiStateValue.updateQuantityByProductId(productId, delta)
                )
            }.onFailure {

            }
        }
    }

    private fun hasNextPage(loadProducts: List<CartProduct>?): Boolean {
        if (loadProducts == null) return false
        return loadProducts.size > PAGE_SIZE
    }

    fun loadMore() {
        productsUiState.value?.pageUp()
        loadInfos()
    }

    companion object {
        private const val PAGE_FETCH_SIZE = 21
        private const val PAGE_SIZE = 20

        val Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return ProductListViewModel(
                        repository = RepositoryProvider.provideProductOverViewRepository(),
                    ) as T
                }
            }
    }
}
