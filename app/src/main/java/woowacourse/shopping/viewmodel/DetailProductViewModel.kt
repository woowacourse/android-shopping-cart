package woowacourse.shopping.viewmodel

import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import woowacourse.shopping.model.ShoppingItem
import woowacourse.shopping.repository.ShoppingCartRepository
import woowacourse.shopping.repository.ShoppingItemRepository
import woowacourse.shopping.storage.datastore.VisitStore

class DetailProductViewModel(
    private val shoppingCartRepository: ShoppingCartRepository,
    private val shoppingItemRepository: ShoppingItemRepository,
    private val visitStore: VisitStore,
) : ViewModel() {
    private val _uiState = MutableStateFlow(DetailProductUiState())
    val uiState: StateFlow<DetailProductUiState> = _uiState.asStateFlow()

    private var cachedShoppingItems: List<ShoppingItem> = shoppingItemRepository.shoppingItems.value
    private var selectedProductId: Long? = null
    private var selectedQuantity: Int = DEFAULT_QUANTITY
    private var showLastViewedSection: Boolean = true
    private var lastViewedProductId: Long? = null

    init {
        publishUiState()
        viewModelScope.launch {
            shoppingItemRepository.shoppingItems.collect { latestShoppingItems ->
                cachedShoppingItems = latestShoppingItems
                publishUiState()
            }
        }
    }

    fun initializeFromIntentExtras(extras: Bundle?) {
        val productId = extras?.getLong(EXTRA_PRODUCT_ID, INVALID_PRODUCT_ID) ?: INVALID_PRODUCT_ID
        if (productId == INVALID_PRODUCT_ID) {
            return
        }
        val showLastViewed = extras?.getBoolean(EXTRA_SHOW_LAST_VIEWED, true) ?: true
        initialize(
            productId = productId,
            showLastViewed = showLastViewed,
        )
    }

    private fun initialize(
        productId: Long,
        showLastViewed: Boolean = true,
    ) {
        showLastViewedSection = showLastViewed
        val recentProductIds = visitStore.recentVisitedProductIds.value
        lastViewedProductId =
            if (showLastViewed) {
                resolveLastViewedProductId(
                    productId = productId,
                    recentProductIds = recentProductIds,
                )
            } else {
                null
            }
        if (selectedProductId != productId) {
            selectedProductId = productId
            selectedQuantity = DEFAULT_QUANTITY
        }
        viewModelScope.launch {
            visitStore.visit(productId)
        }
        publishUiState()
    }

    fun increaseSelectedQuantity() {
        selectedQuantity += 1
        publishUiState()
    }

    fun decreaseSelectedQuantity() {
        if (selectedQuantity <= DEFAULT_QUANTITY) {
            return
        }
        selectedQuantity -= 1
        publishUiState()
    }

    fun addSelectedProductToCart() {
        val shoppingItem = uiState.value.shoppingItem ?: return
        viewModelScope.launch {
            addSelectedProductQuantityToCart(
                productId = shoppingItem.getProductId(),
                quantity = selectedQuantity,
            )
        }
    }

    private suspend fun addSelectedProductQuantityToCart(
        productId: Long,
        quantity: Int,
    ) {
        if (quantity < 1) {
            return
        }
        shoppingCartRepository.addIfAbsent(productId)
        shoppingItemRepository.plusQuantity(productId, quantity)
    }

    private fun publishUiState() {
        _uiState.value = createUiState()
    }

    private fun createUiState(): DetailProductUiState {
        val shoppingItemByProductId = cachedShoppingItems.associateBy { shoppingItem -> shoppingItem.getProductId() }
        val currentShoppingItem = selectedProductId?.let { productId -> shoppingItemByProductId[productId] }
        val safeQuantity = selectedQuantity.coerceAtLeast(DEFAULT_QUANTITY)
        val resolvedLastViewedShoppingItem =
            lastViewedProductId
                ?.takeIf { showLastViewedSection && it != selectedProductId }
                ?.let { productId -> shoppingItemByProductId[productId] }
        return DetailProductUiState(
            shoppingItem = currentShoppingItem,
            lastViewedShoppingItem = resolvedLastViewedShoppingItem,
            selectedQuantity = safeQuantity,
            quantityPrice = currentShoppingItem?.getProductQuantityPrice(safeQuantity) ?: 0,
        )
    }

    private fun resolveLastViewedProductId(
        productId: Long,
        recentProductIds: List<Long>,
    ): Long? {
        if (recentProductIds.isEmpty()) {
            return null
        }
        val currentProductIndex = recentProductIds.indexOf(productId)
        return when {
            currentProductIndex == 0 -> null
            currentProductIndex > 0 -> recentProductIds[currentProductIndex - 1]
            else -> recentProductIds.firstOrNull()
        }
    }

    data class DetailProductUiState(
        val shoppingItem: ShoppingItem? = null,
        val lastViewedShoppingItem: ShoppingItem? = null,
        val selectedQuantity: Int = DEFAULT_QUANTITY,
        val quantityPrice: Int = 0,
    )

    companion object {
        const val EXTRA_PRODUCT_ID = "productId"
        const val EXTRA_SHOW_LAST_VIEWED = "showLastViewed"
        private const val DEFAULT_QUANTITY = 1
        private const val INVALID_PRODUCT_ID = -1L
    }
}
