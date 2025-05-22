package woowacourse.shopping.view.main.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.domain.Cart
import woowacourse.shopping.domain.Page
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.domain.repository.ProductRepository

class MainViewModel(
    private val cartRepository: CartRepository,
    private val productRepository: ProductRepository,
) : ViewModel() {
    private val page = Page(initialPage = INITIAL_PAGE_NUMBER, pageSize = PAGE_SIZE)
    private val _carts = MutableLiveData(emptyList<Cart>())
    val carts: LiveData<List<Cart>> = _carts

    private val _loadable = MutableLiveData(false)
    val loadable: LiveData<Boolean> = _loadable

    fun loadProducts() {
        val (offset, limit) = page.targetRange()
        val products = productRepository.getProducts(page.currentPage, limit)
        cartRepository.getByIds(products.map { it.id }) { cartEntries ->
            _carts.postValue(
                (carts.value ?: emptyList()) +
                    products.map { product ->
                        Cart(product, cartEntries.find { product.id == it.productId }?.quantity ?: 0)
                    },
            )
            _loadable.postValue(productRepository.notHasMoreProduct(page.currentPage, limit).not())
        }
    }

    fun moveNextPage() {
        page.moveToNextPage()
        loadProducts()
    }

    companion object {
        private const val INITIAL_PAGE_NUMBER = 1
        private const val PAGE_SIZE = 20
    }
}
