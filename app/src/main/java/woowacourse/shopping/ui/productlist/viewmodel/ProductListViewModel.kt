package woowacourse.shopping.ui.productlist.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import woowacourse.shopping.data.remote.source.ProductRemoteDataSource
import woowacourse.shopping.data.repository.ProductRepositoryImpl
import woowacourse.shopping.domain.Cart
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.Quantity
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.ui.model.DetailProductUiModel
import woowacourse.shopping.ui.model.SimpleProductUiModel
import woowacourse.shopping.ui.productlist.state.ProductListUiState

class ProductListViewModel(private val productRepository: ProductRepository) : ViewModel() {
    private val _uiState = MutableStateFlow(ProductListUiState())
    val uiState = _uiState.asStateFlow()

    private var allProducts = listOf<Product>()

    private val _products = mutableListOf<Product>()
    private val _recentProducts = mutableListOf<Product>()
    private var cart = Cart()
    private var currentPage = 0

    init {
        loadInitData()
    }

    private fun loadInitData() {
        viewModelScope.launch {
            try {
                allProducts = productRepository.getProducts()
                fetchProducts()
            } catch (e: Exception) {
                println("ProductListViewModel Exception: $e.message")
            }
        }
    }

    fun fetchProducts(pageSize: Int = PAGE_SIZE) {
        require(pageSize > 0) { "PAGE_SIZE의 크기는 0보다 커야한다" }
        if (isEndList()) return

        val fromIndex = currentPage * pageSize
        val toIndex = minOf(fromIndex + pageSize, allProducts.size)

        _products.addAll(
            allProducts.subList(fromIndex, toIndex),
        )
        currentPage++
        syncUiState()
    }

    fun addCartItem(productId: String) {
        val product = _products.find { it.hasId(productId) } ?: return
        cart = cart.plusProduct(product, Quantity(1))
        syncUiState()
    }

    fun removeCartItem(productId: String) {
        val product = _products.find { it.hasId(productId) } ?: return
        if (!cart.contains(product)) return
        cart = cart.minusProduct(product, Quantity(1))
        syncUiState()
    }

    fun onClickProduct(productId: String) {
        val product = _products.find { it.hasId(productId) } ?: return
        _recentProducts.remove(product)
        _recentProducts.add(0, product)
        syncUiState()
    }

    private fun syncUiState() {
        _uiState.update { state ->
            state.copy(
                products = _products.map { product ->
                    toDetailProductUiModel(product, cart.getQuantity(product) ?: Quantity(0))
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
        quantity: Quantity,
    ): DetailProductUiModel = DetailProductUiModel.of(
        name = product.name,
        price = product.price.amount,
        imageUrl = product.imageUrl,
        id = product.id,
        quantity = quantity.count,
    )

    private fun toSimpleProductUiModel(product: Product): SimpleProductUiModel = SimpleProductUiModel(
        id = product.id,
        imageUrl = product.imageUrl,
        title = product.name,
    )

    private fun isEndList(): Boolean = allProducts.isNotEmpty() && _products.size >= allProducts.size

    companion object {
        private const val PAGE_SIZE = 20

        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val dataSource = ProductRemoteDataSource()
                val repository = ProductRepositoryImpl(dataSource)
                ProductListViewModel(repository)
            }
        }
    }
}
