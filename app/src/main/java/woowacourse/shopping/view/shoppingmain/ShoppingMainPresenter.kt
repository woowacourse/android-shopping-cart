package woowacourse.shopping.view.shoppingmain

import com.shopping.repository.ProductRepository
import com.shopping.repository.RecentProductsRepository
import woowacourse.shopping.model.uimodel.ProductUIModel
import woowacourse.shopping.model.uimodel.RecentProductUIModel
import woowacourse.shopping.model.uimodel.mapper.toUIModel

class ShoppingMainPresenter(
    private val view: ShoppingMainContract.View,
    private val productsRepository: ProductRepository,
    private val recentProductsRepository: RecentProductsRepository
) : ShoppingMainContract.Presenter {
    private var index: Pair<Int, Int> = Pair(INIT_INDEX, PRODUCT_LOAD_UNIT)
    private var _isPossibleLoad = POSSIBLE_LOAD
    override val isPossibleLoad
        get() = _isPossibleLoad

    override fun loadProducts(): List<ProductUIModel> {
        val loadedProducts = productsRepository.loadProducts(index)
        index = Pair(index.first + PRODUCT_LOAD_UNIT, index.second + PRODUCT_LOAD_UNIT)

        if (loadedProducts.size < PRODUCT_LOAD_UNIT) {
            view.deactivateButton()
            _isPossibleLoad = IMPOSSIBLE_LOAD
        }

        return loadedProducts.toUIModel()
    }

    override fun getRecentProducts(): List<RecentProductUIModel> {
        return recentProductsRepository.getAll().map { it.toUIModel() }
    }

    override fun loadProductDetailPage() {
        view.showProductDetailPage()
    }

    override fun loadMoreScroll() {
        view.showMoreProducts()
        view.deactivateButton()
    }

    companion object {
        private const val INIT_INDEX = 0
        private const val PRODUCT_LOAD_UNIT = 8
        private const val POSSIBLE_LOAD = true
        private const val IMPOSSIBLE_LOAD = false
    }
}
