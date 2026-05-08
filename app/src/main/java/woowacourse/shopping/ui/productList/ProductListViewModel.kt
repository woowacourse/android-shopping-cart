package woowacourse.shopping.ui.productList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import woowacourse.shopping.di.DataContainer.cartRepository
import woowacourse.shopping.domain.cart.Cart
import woowacourse.shopping.domain.product.Product
import woowacourse.shopping.repository.cart.CartRepository
import woowacourse.shopping.repository.product.ProductRepository

class ProductListViewModel(
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow<ProductListUiState>(ProductListUiState.Loading)
    val uiState: StateFlow<ProductListUiState> = _uiState.asStateFlow()

    private var currentPage = 0
    private val accumulatedProducts = mutableListOf<Product>()
    private val canLoadMore = true
    private var isLoading = false

    init {
        observeCart()
        loadNextPage()
    }

    fun moreProducts() {
        loadNextPage()
    }

    fun addProduct(product:Product){
        viewModelScope.launch{
            cartRepository.addProduct(product)
        }
    }

    fun increase(productId:String){
        viewModelScope.launch{
            cartRepository.increase(productId)
        }
    }

    fun decrease(productId:String){
        viewModelScope.launch{
            cartRepository.decrease(productId)
        }
    }

    private fun observeCart(){
        viewModelScope.launch{
            cartRepository.cartFlow.collect{cart->
                updateUiState(cart)
            }
        }
    }

    private fun loadNextPage() {
        if(isLoading || !canLoadMore)   return

        viewModelScope.launch {
            isLoading = true
            setLoadingMore(true)
            runCatching { productRepository.getProducts(currentPage, PAGE_SIZE) }
                .onSuccess { newProducts ->
                    accumulatedProducts.addAll(newProducts)
                    currentPage++
                    _uiState.value =
                        ProductListUiState.Success(
                            products = accumulatedProducts.toList(),
                            canLoadMore = newProducts.size == PAGE_SIZE,
                            isLoadingMore = false,
                        )
                }.onFailure { throwable ->
                    _uiState.value = ProductListUiState.Error(throwable)
                }
            isLoading = false
        }
    }

    private fun setLoadingMore(loading: Boolean){
        val  current = _uiState.value
        _uiState.value = when(current){
            is ProductListUiState.Success ->  current.copy(isLoadingMore = loading)
            else -> if (loading) ProductListUiState.Loading else current
        }
    }

    private fun updateUiState(cart: Cart){
        if(accumulatedProducts.isEmpty())   return

        val quantities = accumulatedProducts.associate{product ->
            product.id to cart.findQuantity(product.id).value
        }

        _uiState.value = ProductListUiState.Success(
            products = accumulatedProducts.toList(),
            quantitiesByProductId = quantities,
            canLoadMore = canLoadMore,
            isLoadingMore = false,
            totalCartCount = cart.totalQuantity,
        )
    }

    companion object {
        private const val PAGE_SIZE = 20

        fun factory(productRepository: ProductRepository, cartRepository: CartRepository): ViewModelProvider.Factory =
            viewModelFactory {
                initializer {
                    ProductListViewModel(productRepository, cartRepository)
                }
            }
    }
}
