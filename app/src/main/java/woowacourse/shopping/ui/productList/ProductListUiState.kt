package woowacourse.shopping.ui.productList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import woowacourse.shopping.domain.model.Product

abstract class ProductListUiState {
    abstract val page: LiveData<Int>
    abstract val loadedProducts: LiveData<List<Product>>
    abstract val productsHistory: LiveData<List<Product>>
    abstract val cartProductTotalCount: LiveData<Int>
    abstract val isLastPage: LiveData<Boolean>

    abstract fun addLoadedProducts(products: List<Product>)

    abstract fun currentPage(): Int

    abstract fun increaseProductQuantity(productId: Long)

    abstract fun decreaseProductQuantity(productId: Long)

    abstract fun nextPage(): Int

    abstract fun postLoadedProducts(products: List<Product>)

    abstract fun postLastPage(isLastPage: Boolean)

    abstract fun postCartProductTotalCount(count: Int)

    abstract fun postProductsHistory(history: List<Product>)
}

data class DefaultProductListUiState(
    override val page: MutableLiveData<Int> = MutableLiveData(FIRST_PAGE),
    override val loadedProducts: MutableLiveData<List<Product>> = MutableLiveData(),
    override val productsHistory: MutableLiveData<List<Product>> = MutableLiveData(),
    override val cartProductTotalCount: MutableLiveData<Int> = MutableLiveData(),
    override val isLastPage: MutableLiveData<Boolean> = MutableLiveData(),
) : ProductListUiState() {
    override fun addLoadedProducts(products: List<Product>) {
        loadedProducts.postValue(
            loadedProducts.value?.toMutableList()?.apply { addAll(products) } ?: products,
        )
    }

    override fun currentPage(): Int = page.value ?: throw IllegalStateException("Current page is null")

    override fun nextPage(): Int {
        if (isLastPage.value == true) throw IllegalStateException("마지막 페이지입니다.")

        page.value = page.value?.plus(PAGE_MOVE_COUNT)
        return page.value ?: throw IllegalStateException("currentPage is null")
    }

    override fun increaseProductQuantity(productId: Long) {
        changeProductQuantity(productId, INCREASE_AMOUNT)
    }

    override fun decreaseProductQuantity(productId: Long) {
        changeProductQuantity(productId, DECREASE_AMOUNT)
    }

    private fun changeProductQuantity(
        productId: Long,
        changeAmount: Int,
    ) {
        loadedProducts.postValue(
            loadedProducts.value?.map { product ->
                if (product.id == productId) {
                    product.copy(quantity = product.quantity + changeAmount)
                } else {
                    product
                }
            },
        )
    }

    override fun postLoadedProducts(products: List<Product>) {
        loadedProducts.postValue(products)
    }

    override fun postLastPage(isLastPage: Boolean) {
        this.isLastPage.postValue(isLastPage)
    }

    override fun postProductsHistory(history: List<Product>) {
        productsHistory.postValue(history)
    }

    override fun postCartProductTotalCount(count: Int) {
        cartProductTotalCount.postValue(count)
    }

    companion object {
        private const val FIRST_PAGE = 1
        private const val PAGE_MOVE_COUNT = 1

        private const val INCREASE_AMOUNT = 1
        private const val DECREASE_AMOUNT = -1
    }
}
