package woowacourse.shopping.ui.productlist.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import woowacourse.shopping.constants.MockData
import woowacourse.shopping.domain.Cart
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.Quantity
import woowacourse.shopping.ui.model.DetailProductUiModel
import woowacourse.shopping.ui.model.SimpleProductUiModel
import woowacourse.shopping.ui.state.ProductListUiState

class ProductListViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(ProductListUiState())
    val uiState = _uiState.asStateFlow()

    private val _products = mutableListOf<Product>()
    private val _recentProducts = mutableListOf<Product>()
    private var cart = Cart()
    private var currentPage = 0

    init {
        if (_products.isEmpty()) {
            fetchProducts()
        }
    }

    fun fetchProducts(pageSize: Int = PAGE_SIZE) {
        if (isEndList()) return

        val fromIndex = currentPage * pageSize
        val toIndex = minOf(fromIndex + pageSize, MockData.MOCK_PRODUCTS.size)

        _products.addAll(
            MockData.MOCK_PRODUCTS.subList(
                fromIndex = fromIndex,
                toIndex = toIndex,
            ),
        )
        currentPage++
        syncUiState()
    }

    private fun syncUiState() {
        _uiState.update { state ->
            state.copy(
                products = _products.map { product ->
                    toDetailProductUiModel(product, cart.getQuantity(product))
                },
                recentProducts = _recentProducts.map { product ->
                    toSimpleProductUiModel(product)
                },
                cartCount = cart.totalQuantity.count,
                isEnd = isEndList(),
            )
        }
    }

    private fun toDetailProductUiModel(
        product: Product,
        quantity: Quantity?,
    ): DetailProductUiModel = DetailProductUiModel.of(
        name = product.name,
        price = product.price.amount,
        imageUrl = product.imageUrl,
        id = product.id,
        quantity = quantity?.count ?: 0,
    )

    private fun toSimpleProductUiModel(product: Product): SimpleProductUiModel = SimpleProductUiModel(
        id = product.id,
        imageUrl = product.imageUrl,
        title = product.name,
    )

    private fun isEndList(): Boolean = _products.size >= MockData.MOCK_PRODUCTS.size

    companion object {
        private const val PAGE_SIZE = 20
    }
}
