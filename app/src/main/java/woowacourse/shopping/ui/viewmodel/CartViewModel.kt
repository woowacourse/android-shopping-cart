package woowacourse.shopping.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okio.IOException
import woowacourse.shopping.data.local.entity.PurchaseProductEntity
import woowacourse.shopping.data.local.repository.PurchaseProductsRepository
import woowacourse.shopping.data.remote.repository.ProductRepository
import woowacourse.shopping.domain.Cart
import woowacourse.shopping.domain.PurchaseProduct
import woowacourse.shopping.domain.PurchaseProducts

class CartViewModel(
    private val purchaseProductsRepository: PurchaseProductsRepository,
    private val productRepository: ProductRepository,
) : ViewModel() {
    private val _currentPage: MutableStateFlow<Int> = MutableStateFlow(0)

    val currentPage: StateFlow<Int> = _currentPage.asStateFlow()

    val cartEntities: StateFlow<List<PurchaseProductEntity>?> = purchaseProductsRepository
        .getAll()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList(),
        )

    @OptIn(ExperimentalCoroutinesApi::class)
    val pagedCart: StateFlow<Cart> =
        combine(_currentPage, cartEntities) { page, entities ->
            entities?.drop(page * PAGE_SIZE)?.take(PAGE_SIZE) ?: emptyList()
        }.flatMapLatest { entities ->
            flow {
                try {
                    val purchaseProducts = coroutineScope {
                        entities.map { entity ->
                            async {
                                val product = productRepository.getProduct(entity.id)
                                PurchaseProduct(product, entity.count)
                            }
                        }.awaitAll()
                    }
                    emit(Cart(PurchaseProducts(purchaseProducts)))
                } catch (e: IOException){
                    Log.e("Web Server Error", e.message!!)
                }
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = Cart()
        )

    val productCount: StateFlow<Int> =
        purchaseProductsRepository.getProductCount().stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = 0,
        )

    init {
        viewModelScope.launch {
            cartEntities.collect { items ->
                val count = items?.size ?: 0
                if (currentPage.value > 0 && count <= currentPage.value * PAGE_SIZE) {
                    prev()
                }
            }
        }
    }

    fun next() {
        _currentPage.update {
            if (productCount.value > (it + 1) * PAGE_SIZE) it + 1 else it
        }
    }

    fun prev() {
        _currentPage.update { if (it > 0) it - 1 else 0 }
    }

    val nextEnable: StateFlow<Boolean> =
        combine(currentPage, productCount) { page, count ->
            page < (count - 1) / PAGE_SIZE
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = false,
        )

    val prevEnable: StateFlow<Boolean> =
        currentPage
            .map { it > 0 }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = false,
            )

    val isPageable: StateFlow<Boolean> =
        productCount
            .map { it > PAGE_SIZE }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = false,
            )

    fun updateCountWithID(
        id: String,
        updateAmount: Int,
    ) {
        viewModelScope.launch {
            purchaseProductsRepository.updateCount(id, updateAmount)
        }
    }

    fun removeWithID(id: String) {
        viewModelScope.launch {
            purchaseProductsRepository.deletePurchaseProduct(id)
        }
    }

    companion object {
        private val PAGE_SIZE = 5
    }
}

class CartViewModelFactory(
    private val purchaseProductsRepository: PurchaseProductsRepository,
    private val productRepository: ProductRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CartViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CartViewModel(purchaseProductsRepository, productRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
