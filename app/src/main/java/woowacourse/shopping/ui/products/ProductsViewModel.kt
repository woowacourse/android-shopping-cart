package woowacourse.shopping.ui.products

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import woowacourse.shopping.data.cart.CartRepository
import woowacourse.shopping.data.product.ProductRepository
import woowacourse.shopping.model.Product
import kotlin.math.ceil

class ProductsViewModel(
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository,
) : ViewModel() {
    private val _productUiModels = MutableLiveData<MutableList<ProductUiModel>>(mutableListOf())
    val productUiModels: LiveData<List<ProductUiModel>> = _productUiModels.map { it.toList() }

    private val _showLoadMore = MutableLiveData<Boolean>(false)
    val showLoadMore: LiveData<Boolean> get() = _showLoadMore

    private var page: Int = INITIALIZE_PAGE
    private var maxPage: Int = INITIALIZE_PAGE

    private val _changedProductQuantity = MutableLiveData<ProductUiModel>()
    val changedProductQuantity: LiveData<ProductUiModel> get() = _changedProductQuantity

    private val _cartTotalCount = MutableLiveData<Int>(0)
    val cartTotalCount: LiveData<Int> get() = _cartTotalCount

    init {
        loadPage()
        loadMaxPage()
    }

    fun loadPage() {
        val currentProductsUiModel = _productUiModels.value ?: emptyList()
        val products = productRepository.findRange(page++, PAGE_SIZE)
        _productUiModels.value =
            (currentProductsUiModel + products.toProductsUiModel()).toMutableList()
        _showLoadMore.value = false
    }

    private fun List<Product>.toProductsUiModel(): List<ProductUiModel> {
        return map { product ->
            runCatching { cartRepository.find(product.id) }
                .map { ProductUiModel.from(product, it.quantity) }
                .getOrElse { ProductUiModel.from(product) }
        }
    }

    private fun loadMaxPage() {
        val totalProductCount = productRepository.totalCount()
        maxPage = ceil(totalProductCount.toDouble() / PAGE_SIZE).toInt()
    }

    fun changeSeeMoreVisibility(lastPosition: Int) {
        _showLoadMore.value =
            (lastPosition + 1) % PAGE_SIZE == 0 && lastPosition + 1 == _productUiModels.value?.size && page < maxPage
    }

    fun decreaseQuantity(productId: Long) {
        val productUiModel = _productUiModels.value?.find { it.productId == productId } ?: return
        cartRepository.decreaseQuantity(productId)
        _cartTotalCount.value = (_cartTotalCount.value ?: 0) - 1
        var changedQuantity = productUiModel.quantity
        val new = productUiModel.copy(quantity = --changedQuantity)
        _changedProductQuantity.value = new
        _productUiModels.value =
            _productUiModels.value?.apply {
                val index = indexOfFirst { it.productId == productId }
                this[index] = new
            }
    }

    fun increaseQuantity(productId: Long) {
        val productUiModel = _productUiModels.value?.find { it.productId == productId } ?: return
        cartRepository.increaseQuantity(productId)
        _cartTotalCount.value = (_cartTotalCount.value ?: 0) + 1
        var changedQuantity = productUiModel.quantity
        val newProductUiModel = productUiModel.copy(quantity = ++changedQuantity)
        _changedProductQuantity.value = newProductUiModel
        _productUiModels.value =
            _productUiModels.value?.apply {
                val index = indexOfFirst { it.productId == productId }
                this[index] = newProductUiModel
            }
    }

    companion object {
        private const val INITIALIZE_PAGE = 0
        private const val PAGE_SIZE = 20
    }
}
