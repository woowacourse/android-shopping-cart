package woowacourse.shopping.ui.productList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.MutableSingleLiveData
import woowacourse.shopping.SingleLiveData
import woowacourse.shopping.currentPageIsNullException
import woowacourse.shopping.data.model.ProductData
import woowacourse.shopping.data.source.DummyProductIdsCountDataSource
import woowacourse.shopping.domain.model.ProductCountEvent
import woowacourse.shopping.domain.model.ProductIdsCount
import woowacourse.shopping.domain.repository.DefaultProductIdsCountRepository
import woowacourse.shopping.domain.repository.ProductIdsCountRepository
import woowacourse.shopping.domain.repository.ShoppingProductsRepository

// TODO: 생성자 파라미터 기본값 삭제
class ProductListViewModel(
    private val productsRepository: ShoppingProductsRepository,
    private val productIdsCountRepository: ProductIdsCountRepository =
        DefaultProductIdsCountRepository(
            DummyProductIdsCountDataSource(),
        ),
    private var _currentPage: MutableLiveData<Int> = MutableLiveData(FIRST_PAGE),
) : ViewModel(),
    ProductRecyclerViewAdapter.OnProductItemClickListener,
    ProductRecyclerViewAdapter.OnItemQuantityChangeListener {
    val currentPage: LiveData<Int> get() = _currentPage

    private val _loadedProducts: MutableLiveData<List<ProductData>> =
        MutableLiveData(
            productsRepository.loadPagedItems(currentPage.value ?: currentPageIsNullException()),
        )
    val loadedProducts: LiveData<List<ProductData>>
        get() = _loadedProducts

    private val _productIdsCountInCart: MutableLiveData<List<ProductIdsCount>> =
        MutableLiveData(productIdsCountRepository.loadAllProductIdsCounts())

    val productIdsCount: LiveData<List<ProductIdsCount>> get() = _productIdsCountInCart

    private val _productsEvent: MutableLiveData<ProductCountEvent> =
        MutableLiveData(ProductCountEvent.ProductCountAllCleared)
    val productsEvent: LiveData<ProductCountEvent> = _productsEvent

    private var _isLastPage: MutableLiveData<Boolean> =
        MutableLiveData(
            productsRepository.isFinalPage(currentPage.value ?: currentPageIsNullException()),
        )
    val isLastPage: LiveData<Boolean> get() = _isLastPage

    private var _detailProductDestinationId: MutableSingleLiveData<Int> = MutableSingleLiveData()
    val detailProductDestinationId: SingleLiveData<Int> get() = _detailProductDestinationId

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

    fun loadProductIdsCount() {
        _productIdsCountInCart.value = productIdsCountRepository.loadAllProductIdsCounts()
    }

    override fun onClick(productId: Int) {
        _detailProductDestinationId.setValue(productId)
    }

    override fun onIncrease(productId: Int) {
        // TODO: 이거 레포지토리에서 nullable 값을 리턴해야 겠는데
        try {
            productIdsCountRepository.plusProductsIdCount(productId)
            val product = productIdsCountRepository.findByProductId(productId)
            _productsEvent.value = ProductCountEvent.ProductCountCountChanged(product.productId, product.quantity)
        } catch (e: NoSuchElementException) {
            productIdsCountRepository.addedProductsId(ProductIdsCount(productId, 1))
            _productsEvent.value = ProductCountEvent.ProductCountCountChanged(productId, 1)
        }
        loadProductIdsCount()
    }

    override fun onDecrease(productId: Int) {
        // TODO: 이거 레포지토리에서 nullable 값을 리턴해야 겠는데
        try {
            productIdsCountRepository.minusProductsIdCount(productId)
        } catch (e: NoSuchElementException) {
            return
        }

        try {
            val product = productIdsCountRepository.findByProductId(productId)
            _productsEvent.value = ProductCountEvent.ProductCountCountChanged(product.productId, product.quantity)
        } catch (e: NoSuchElementException) {
            _productsEvent.value = ProductCountEvent.ProductCountCleared(productId)
        }
    }

    companion object {
        private val TAG = ProductListViewModel::class.java.simpleName
        private const val FIRST_PAGE = 1
        private const val PAGE_MOVE_COUNT = 1
    }
}
