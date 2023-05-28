package woowacourse.shopping.feature.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.domain.model.Product
import com.example.domain.model.RecentProduct
import com.example.domain.repository.CartRepository
import com.example.domain.repository.ProductRepository
import com.example.domain.repository.RecentProductRepository
import woowacourse.shopping.feature.main.MainContract.View.MainScreenEvent
import woowacourse.shopping.mapper.toDomain
import woowacourse.shopping.mapper.toPresentation
import woowacourse.shopping.model.ProductUiModel
import woowacourse.shopping.model.RecentProductUiModel
import java.time.LocalDateTime

class MainPresenter(
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository,
    private val recentProductRepository: RecentProductRepository
) : MainContract.Presenter {
    private val _products: MutableLiveData<List<ProductUiModel>> = MutableLiveData()
    override val products: LiveData<List<ProductUiModel>>
        get() = _products

    private val _recentProducts: MutableLiveData<List<RecentProductUiModel>> = MutableLiveData()
    override val recentProducts: LiveData<List<RecentProductUiModel>>
        get() = _recentProducts

    private val _badgeCount: MutableLiveData<Int> = MutableLiveData()
    override val badgeCount: LiveData<Int>
        get() = _badgeCount

    private val _mainScreenEvent: MutableLiveData<MainScreenEvent> =
        MutableLiveData()
    override val mainScreenEvent: LiveData<MainScreenEvent>
        get() = _mainScreenEvent

    override fun loadProducts() {
        productRepository.fetchFirstProducts(
            onSuccess = {
                val productUiModels = makeProductUiModels(it)
                _products.postValue(productUiModels)
                updateCartCountBadge()
            },
            onFailure = {
                _mainScreenEvent.postValue(MainScreenEvent.HideLoadMore)
            }
        )
    }

    private fun makeProductUiModels(products: List<Product>): List<ProductUiModel> {
        val cartProducts = cartRepository.getAll().map { it.toPresentation() }
        val productUiModels = products.map { product ->
            val findCartProduct = cartProducts.find { cartProduct ->
                product.id == cartProduct.productUiModel.id
            } ?: return@map product.toPresentation()
            product.toPresentation().apply { this.count = findCartProduct.productUiModel.count }
        }
        return productUiModels
    }

    override fun moveToCart() {
        _mainScreenEvent.postValue(MainScreenEvent.ShowCartScreen)
    }

    override fun loadMoreProduct() {
        val lastProductId: Long = products.value?.lastOrNull()?.id ?: 0
        productRepository.fetchNextProducts(
            lastProductId,
            onSuccess = {
                val nextProductUiModels = makeProductUiModels(it)
                val alreadyProductsList = _products.value ?: emptyList()
                _products.postValue(alreadyProductsList + nextProductUiModels)
            },
            onFailure = {
                _mainScreenEvent.postValue(MainScreenEvent.HideLoadMore)
            }
        )
    }

    override fun loadRecent() {
        val recentProductUiModels = makeRecentProductUiModels(recentProductRepository.getAll())
        _recentProducts.value = recentProductUiModels
    }

    private fun makeRecentProductUiModels(recentProducts: List<RecentProduct>): List<RecentProductUiModel> {
        val cartProducts = cartRepository.getAll().map { it.toPresentation() }
        val recentProductUiModels = recentProducts.map { recentProduct ->
            val findCartProduct = cartProducts.find { cartProduct ->
                recentProduct.product.id == cartProduct.productUiModel.id
            } ?: return@map recentProduct.toPresentation().apply { productUiModel.count = 0 }
            recentProduct.toPresentation()
                .apply { productUiModel.count = findCartProduct.productUiModel.count }
        }

        return recentProductUiModels
    }

    override fun loadCartCountSize() {
        updateCartCountBadge()
    }

    override fun showProductDetail(productId: Long) {
        val product = products.value?.find { it.id == productId } ?: return
        val recentProduct = recentProducts.value?.firstOrNull()
        showDetailScreenEvent(product, recentProduct)
        addRecentProduct(product)
        loadRecent()
    }

    override fun showRecentProductDetail(productId: Long) {
        val recentClickProduct =
            recentProducts.value?.find { it.productUiModel.id == productId } ?: return
        val recentProduct = recentProducts.value?.firstOrNull()
        showDetailScreenEvent(recentClickProduct.productUiModel, recentProduct)
        addRecentProduct(recentClickProduct)
        loadRecent()
    }

    private fun showDetailScreenEvent(
        product: ProductUiModel,
        recentProduct: RecentProductUiModel?
    ) {
        _mainScreenEvent.value = MainScreenEvent.ShowProductDetailScreen(product, recentProduct)
    }

    override fun changeProductCartCount(productId: Long, count: Int) {
        val product = products.value?.find { it.id == productId } ?: return
        product.count = count
        cartRepository.changeCartProductCount(product.toDomain(), count)
        updateCartCountBadge()
    }

    override fun resetProducts() {
        productRepository.resetCache()
        _products.postValue(listOf())
    }

    private fun addRecentProduct(recentProduct: RecentProductUiModel) {
        recentProductRepository.addRecentProduct(
            recentProduct.toDomain().copy(dateTime = LocalDateTime.now())
        )
    }

    private fun addRecentProduct(product: ProductUiModel) {
        recentProductRepository.addRecentProduct(
            RecentProduct(
                product.toDomain(),
                LocalDateTime.now()
            )
        )
    }

    private fun updateCartCountBadge() {
        _badgeCount.postValue(cartRepository.getAllCountSize())
    }
}
