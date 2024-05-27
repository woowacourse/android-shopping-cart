package woowacourse.shopping.ui.products

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import woowacourse.shopping.common.Event
import woowacourse.shopping.data.cart.CartRepository
import woowacourse.shopping.data.product.ProductRepository
import woowacourse.shopping.data.product.entity.Product
import woowacourse.shopping.data.recent.RecentProductRepository
import woowacourse.shopping.data.recent.entity.RecentProduct
import woowacourse.shopping.ui.products.adapter.recent.RecentProductUiModel
import woowacourse.shopping.ui.products.adapter.type.ProductUiModel
import kotlin.math.ceil

class ProductsViewModel(
    private val productRepository: ProductRepository,
    private val recentProductRepository: RecentProductRepository,
    private val cartRepository: CartRepository,
) : ViewModel() {
    private val _productUiModels = MutableLiveData<List<ProductUiModel>>(emptyList())
    val productUiModels: LiveData<List<ProductUiModel>> = _productUiModels

    private val _showLoadMore = MutableLiveData<Boolean>(false)
    val showLoadMore: LiveData<Boolean> get() = _showLoadMore

    private var page: Int = INITIALIZE_PAGE
    private var maxPage: Int = INITIALIZE_PAGE

    val cartTotalCount: LiveData<Int> =
        _productUiModels.map { it.fold(0) { acc, productUiModel -> acc + productUiModel.quantity.count } }

    private val _recentProductUiModels = MutableLiveData<List<RecentProductUiModel>?>()
    val recentProductUiModels: LiveData<List<RecentProductUiModel>?> get() = _recentProductUiModels

    private val _pageLoadError = MutableLiveData<Event<Unit>>()
    val pageLoadError: LiveData<Event<Unit>> get() = _pageLoadError

    init {
        loadPage()
        loadMaxPage()
        loadRecentProducts()
    }

    fun loadPage() {
        page = nextPage(page)
    }

    private fun nextPage(page: Int): Int {
        val productUiModels = _productUiModels.value ?: emptyList()
        runCatching {
            productRepository.findRange(page, PAGE_SIZE)
        }.onSuccess { products ->
            _productUiModels.value = (productUiModels + products.toProductUiModels())
            _showLoadMore.value = false
            return page + 1
        }.onFailure {
            _pageLoadError.value = Event(Unit)
        }
        return page
    }

    fun loadProducts() {
        val products =
            _productUiModels.value?.map { productRepository.find(it.productId) } ?: return
        _productUiModels.value = products.toProductUiModels()
    }

    fun loadProduct(productId: Long) {
        val product = productRepository.find(productId)
        val productUiModel = product.toProductUiModel()
        updateProductUiModels(productId, productUiModel)
    }

    private fun List<Product>.toProductUiModels(): List<ProductUiModel> {
        return map { it.toProductUiModel() }
    }

    private fun Product.toProductUiModel(): ProductUiModel {
        return runCatching { cartRepository.find(id) }
            .map { ProductUiModel.from(this, it.quantity) }
            .getOrElse { ProductUiModel.from(this) }
    }

    private fun loadMaxPage() {
        val totalProductCount = productRepository.totalProductCount()
        maxPage = ceil(totalProductCount.toDouble() / PAGE_SIZE).toInt()
    }

    fun loadRecentProducts() {
        _recentProductUiModels.value =
            recentProductRepository.findRecentProducts().toRecentProductUiModels()
    }

    private fun List<RecentProduct>.toRecentProductUiModels(): List<RecentProductUiModel>? {
        val recentProductsUiModels =
            map {
                val product = productRepository.find(it.productId)
                RecentProductUiModel(product.id, product.imageUrl, product.title)
            }
        return recentProductsUiModels.ifEmpty { null }
    }

    fun changeSeeMoreVisibility(lastPosition: Int) {
        _showLoadMore.value =
            (lastPosition + 1) % PAGE_SIZE == 0 && lastPosition + 1 == _productUiModels.value?.size && page < maxPage
    }

    fun decreaseQuantity(productId: Long) {
        val productUiModel = findProductUiModel(productId) ?: return
        cartRepository.decreaseQuantity(productId)

        var oldQuantity = productUiModel.quantity
        val newProductUiModel = productUiModel.copy(quantity = --oldQuantity)
        updateProductUiModels(productId, newProductUiModel)
    }

    fun increaseQuantity(productId: Long) {
        val productUiModel = findProductUiModel(productId) ?: return
        cartRepository.increaseQuantity(productId)

        var oldQuantity = productUiModel.quantity
        val newProductUiModel = productUiModel.copy(quantity = ++oldQuantity)
        updateProductUiModels(productId, newProductUiModel)
    }

    private fun findProductUiModel(productId: Long): ProductUiModel? {
        return _productUiModels.value?.find { it.productId == productId }
    }

    private fun updateProductUiModels(
        productId: Long,
        newProductUiModel: ProductUiModel,
    ) {
        val productUiModels = _productUiModels.value?.toMutableList() ?: return
        val position = productUiModels.indexOfFirst { it.productId == productId }
        productUiModels[position] = newProductUiModel
        _productUiModels.value = productUiModels
    }

    companion object {
        private const val INITIALIZE_PAGE = 0
        private const val PAGE_SIZE = 20
    }
}
