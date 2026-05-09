package woowacourse.shopping.ui.pagination

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import woowacourse.shopping.ShoppingApplication
import woowacourse.shopping.repository.ProductRepository
import woowacourse.shopping.ui.WonMoney

class DetailProductViewModel(
    private val productRepository: ProductRepository,
) : ViewModel() {
    private val _uiState =
        MutableStateFlow(
            ProductUiModel(
                id = "-1",
                name = "존재하지 않는 상품",
                price = WonMoney(-9999),
                imageUrl = "키키 - 404(New Era)",
            ),
        )

    val uiState = _uiState.asStateFlow()

    fun loadProduct(productId: String) {
        viewModelScope.launch {
            val product = productRepository.getProduct(productId) ?: return@launch

            _uiState.value =
                ProductUiModel(
                    id = product.id,
                    name = product.getTitle(),
                    price = WonMoney(product.getPrice()),
                    imageUrl = product.imageUrl,
                )
        }
    }

    companion object {
        fun factory(shoppingApplication: ShoppingApplication) =
            viewModelFactory {
                initializer {
                    DetailProductViewModel(
                        shoppingApplication.productRepository,
                    )
                }
            }
    }
}
