package woowacourse.shopping.ui.screens.product

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.launch
import woowacourse.shopping.ShoppingApplication
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.domain.repository.ProductRepository

class ProductViewModel(
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository,
) : ViewModel() {
    var products: List<Product> by mutableStateOf(emptyList())
        private set

    var hasNext by mutableStateOf(false)
        private set

    var totalCartCount by mutableIntStateOf(cartRepository.cartItemCount)
        private set
    private var isProductLoading = false

    init {
        getProducts()
    }

    fun getProducts() {
        if (isProductLoading) return

        isProductLoading = true

        viewModelScope.launch {
            try {
                products = (products + productRepository.getProducts(products.size, PAGE_SIZE))
                    .distinct()
                hasNext = productRepository.productSize > products.size
            } finally {
                isProductLoading = false
            }
        }
    }

    companion object {
        private const val PAGE_SIZE = 20

        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val app = this[APPLICATION_KEY] as ShoppingApplication
                ProductViewModel(
                    productRepository = app.productRepository,
                    cartRepository = app.cartRepository,
                )
            }
        }
    }
}
