package woowacourse.shopping.presentation.shopping.product

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.domain.entity.CartProduct
import woowacourse.shopping.domain.entity.Product
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.domain.repository.ShoppingRepository
import woowacourse.shopping.presentation.base.BaseViewModelFactory
import woowacourse.shopping.presentation.common.MutableSingleLiveData
import woowacourse.shopping.presentation.common.SingleLiveData
import woowacourse.shopping.presentation.shopping.toShoppingUiModel

class ProductListViewModel(
    private val shoppingRepository: ShoppingRepository,
    private val cartRepository: CartRepository,
) : ViewModel(), ProductItemListener {
    private val _products = MutableLiveData<List<ShoppingUiModel>>(emptyList())
    val products: LiveData<List<ShoppingUiModel>> = _products
    private var currentPage = 1

    private val _navigateToDetailEvent = MutableSingleLiveData<Long>()
    val navigateToDetailEvent: SingleLiveData<Long> = _navigateToDetailEvent

    init {
        loadProducts()
        loadCartProducts()
    }

    override fun loadProducts() {
        val currentProducts = _products.value.orEmpty().filterIsInstance<ShoppingUiModel.Product>()
        // TODO : 현재 Executor cancel 처리가 되지 않음
        shoppingRepository.products(currentPage, PAGE_SIZE)
            .onSuccess {
                val newProducts = it.map(Product::toShoppingUiModel)
                _products.value = currentProducts + newProducts + getLoadMore(++currentPage)
            }.onFailure {
                // TODO : Handle error
            }
    }

    fun loadCartProducts() {
        val loadMore = _products.value.orEmpty().filterIsInstance<ShoppingUiModel.LoadMore>()
        val currentProducts = _products.value.orEmpty().filterIsInstance<ShoppingUiModel.Product>()
        val ids = currentProducts.map { it.id }
        cartRepository.filterCarProducts(ids)
            .onSuccess { newCartProducts ->
                val newProducts = newCartProducts.map(CartProduct::toShoppingUiModel)
                _products.value = currentProducts.map {
                    val newProduct = newProducts.find { newProduct -> newProduct.id == it.id }
                        ?: return@map it.copy(count = 0)
                    it.copy(count = newProduct.count)
                } + loadMore
                Log.e("로그", "loadCartProducts: ${_products.value}")
            }.onFailure {
                // TODO : Handle error
            }
    }

    override fun increaseProductCount(id: Long) {
        val loadMore = _products.value.orEmpty().filterIsInstance<ShoppingUiModel.LoadMore>()
        val currentProducts = _products.value.orEmpty().filterIsInstance<ShoppingUiModel.Product>()
        val product = currentProducts.find { it.id == id } ?: return
        val newProduct = product.copy(count = product.count + 1)
        cartRepository.updateCartProduct(id, newProduct.count).onSuccess {
            val updatedProducts = currentProducts.map {
                if (it.id == id) newProduct
                else it
            }
            _products.value = updatedProducts + loadMore

        }.onFailure {
            // TODO : Handle error
        }
    }

    override fun decreaseProductCount(id: Long) {
        val loadMore = _products.value.orEmpty().filterIsInstance<ShoppingUiModel.LoadMore>()
        val currentProducts = _products.value.orEmpty().filterIsInstance<ShoppingUiModel.Product>()
        val product = currentProducts.find { it.id == id } ?: return
        val newProduct = product.copy(count = product.count - 1)
        val updatedProducts = currentProducts.map {
            if (it.id == id) newProduct
            else it
        }
        if (newProduct.count == 0) {
            cartRepository.deleteCartProduct(id).onSuccess {
                _products.value = updatedProducts + loadMore
            }
            return
        }
        cartRepository.updateCartProduct(id, newProduct.count).onSuccess {
            _products.value = updatedProducts + loadMore
        }
    }

    override fun navigateToDetail(id: Long) {
        shoppingRepository.saveRecentProduct(id)
        _navigateToDetailEvent.setValue(id)
    }

    private fun getLoadMore(page: Int): List<ShoppingUiModel.LoadMore> {
        return if (shoppingRepository.canLoadMore(page, PAGE_SIZE).getOrNull() == true) {
            listOf(ShoppingUiModel.LoadMore)
        } else {
            emptyList()
        }
    }

    companion object {
        private const val PAGE_SIZE = 20

        fun factory(
            shoppingRepository: ShoppingRepository,
            cartRepository: CartRepository
        ): ViewModelProvider.Factory {
            return BaseViewModelFactory { ProductListViewModel(shoppingRepository, cartRepository) }
        }
    }
}
