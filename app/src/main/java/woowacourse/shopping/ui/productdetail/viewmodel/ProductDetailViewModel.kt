package woowacourse.shopping.ui.productdetail.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import woowacourse.shopping.constants.MockData
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.Quantity
import woowacourse.shopping.ui.model.DetailProductUiModel
import woowacourse.shopping.ui.model.LatestProductUiModel
import woowacourse.shopping.ui.productdetail.state.ProductDetailUiState

class ProductDetailViewModel(private val savedStateHandle: SavedStateHandle) : ViewModel() {
    private val productId: String = savedStateHandle["product_id"] ?: ""

    private val _uiState = MutableStateFlow(ProductDetailUiState())
    val uiState = _uiState.asStateFlow()

    private var product: Product? = null

    init {
        loadProduct()
    }

    private fun loadProduct() {
        product = MockData.MOCK_PRODUCTS.find { it.hasId(productId) }

        if (product == null) {
            _uiState.update {
                it.copy(isError = true)
            }
            return
        }

        val lastProduct = MockData.MOCK_PRODUCTS.first()

        _uiState.update { state ->
            state.copy(
                product = toDetailProductUiModel(product!!, Quantity(1)),
                selectedQuantity = 1,
                latestProduct = toLatestProductUiModel(lastProduct),
            )
        }
    }

    private fun toDetailProductUiModel(
        product: Product,
        quantity: Quantity,
    ): DetailProductUiModel {
        val price = product.price * quantity
        return DetailProductUiModel.of(
            name = product.name,
            price = price.amount,
            imageUrl = product.imageUrl,
            id = product.id,
            quantity = quantity.count,
        )
    }

    private fun toLatestProductUiModel(product: Product): LatestProductUiModel = LatestProductUiModel(
        id = product.id,
        title = product.name,
    )

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val savedStateHandle = createSavedStateHandle()
                ProductDetailViewModel(savedStateHandle)
            }
        }
    }
}
