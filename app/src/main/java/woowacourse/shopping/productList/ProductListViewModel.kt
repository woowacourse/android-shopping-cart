package woowacourse.shopping.productList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.MutableSingleLiveData
import woowacourse.shopping.SingleLiveData
import woowacourse.shopping.currentPageIsNullException
import woowacourse.shopping.data.Product
import woowacourse.shopping.domain.model.ProductIdsCount
import woowacourse.shopping.repository.DefaultProductIdsCountRepository
import woowacourse.shopping.repository.ProductIdsCountRepository
import woowacourse.shopping.repository.ShoppingProductsRepository
import woowacourse.shopping.source.DummyProductIdsCountDataSource

// TODO: 생성자 파라미터 기본값 삭제
class ProductListViewModel(
    private val productsRepository: ShoppingProductsRepository,
    private val productIdsCountRepository: ProductIdsCountRepository =
        DefaultProductIdsCountRepository(
            DummyProductIdsCountDataSource(),
        ),
    private var _currentPage: MutableLiveData<Int> = MutableLiveData(FIRST_PAGE),
) : ViewModel(), ProductRecyclerViewAdapter.OnProductItemClickListener {
    val currentPage: LiveData<Int> get() = _currentPage

    private val _loadedProducts: MutableLiveData<List<Product>> =
        MutableLiveData(
            productsRepository.loadPagedItems(currentPage.value ?: currentPageIsNullException()),
        )

    val loadedProducts: LiveData<List<Product>>
        get() = _loadedProducts

    private val _productIdsCountInCart: MutableLiveData<List<ProductIdsCount>> =
        MutableLiveData(productIdsCountRepository.loadAllProductIdsCounts())
    val productIdsCount: LiveData<List<ProductIdsCount>> get() = _productIdsCountInCart

    private var _isLastPage: MutableLiveData<Boolean> =
        MutableLiveData(
            productsRepository.isFinalPage(currentPage.value ?: currentPageIsNullException()),
        )
    val isLastPage: LiveData<Boolean> get() = _isLastPage

    private var _detailProductDestinationId: MutableSingleLiveData<Int> = MutableSingleLiveData()
    val detailProductDestinationId: SingleLiveData<Int> get() = _detailProductDestinationId

    fun loadProductIdsCount() {
        _productIdsCountInCart.value = productIdsCountRepository.loadAllProductIdsCounts()
    }

    // TODO: productIdsCount 를 추가하거나 삭제할 수 있음

    fun loadNextPageProducts() {
        if (isLastPage.value == true) return
        _currentPage.value = _currentPage.value?.plus(PAGE_MOVE_COUNT)
        _isLastPage.value = productsRepository.isFinalPage(currentPage.value ?: currentPageIsNullException())

        val result = productsRepository.loadPagedItems(currentPage.value ?: currentPageIsNullException())

        _loadedProducts.value =
            _loadedProducts.value?.toMutableList()?.apply {
                addAll(result)
            }
    }

    override fun onClick(productId: Int) {
        _detailProductDestinationId.setValue(productId)
    }

    companion object {
        private val TAG = ProductListViewModel::class.java.simpleName
        private const val FIRST_PAGE = 1
        private const val PAGE_MOVE_COUNT = 1
    }
}
