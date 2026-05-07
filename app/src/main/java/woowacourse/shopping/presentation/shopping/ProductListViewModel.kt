package woowacourse.shopping.presentation.shopping

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.app.AppContainer.cartRepository
import woowacourse.shopping.domain.model.product.Product
import woowacourse.shopping.domain.model.product.Products
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.domain.repository.ProductRepository
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class ProductListViewModel(
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository,
    private val pageSize: Int = DEFAULT_PAGE_SIZE,
) : ViewModel() {
    var currentPageIndex by mutableStateOf(0)

    var products by mutableStateOf(Products())
        private set

    var cart by mutableStateOf(cartRepository.getItems())
        private set

    val hasNextPage: Boolean
        get() =
            productRepository.hasNextPage(
                currentPage = currentPageIndex,
                pageSize = pageSize,
            )

    init {
        loadPages(currentPageIndex)
        refreshCart()
    }

    fun loadMore() {
        if (!hasNextPage) return

        currentPageIndex++

        val nextProducts =
            productRepository.getPagingProducts(
                page = currentPageIndex,
                pageSize = pageSize,
            )

        products += nextProducts
    }

    fun increaseQuantity(product: Product) {
        cartRepository.increaseQuantity(product)
        refreshCart()
    }

    @OptIn(ExperimentalUuidApi::class)
    fun getQuantity(productId: Uuid): Int =
        cart.cartItems
            .find { it.product.productId == productId }
            ?.quantity ?: 0

    private fun loadPages(currentPageIndex: Int) {
        products = Products()

        for (page in 0..currentPageIndex) {
            products +=
                productRepository.getPagingProducts(
                    page = page,
                    pageSize = pageSize,
                )
        }
    }

    private fun refreshCart() {
        cart = cartRepository.getItems()
    }

    companion object {
        private const val DEFAULT_PAGE_SIZE = 20
    }
}

class ProductListViewModelFactory(
    private val productRepository: ProductRepository,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProductListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ProductListViewModel(
                productRepository = productRepository,
                cartRepository = cartRepository,
            ) as T
        } else {
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
