package woowacourse.shopping.view.shoppingmain

import com.shopping.domain.CartProduct
import com.shopping.domain.Count
import com.shopping.repository.CartProductRepository
import com.shopping.repository.ProductRepository
import com.shopping.repository.RecentProductsRepository
import woowacourse.shopping.model.uimodel.ProductUIModel
import woowacourse.shopping.model.uimodel.RecentProductUIModel
import woowacourse.shopping.model.uimodel.mapper.toDomain
import woowacourse.shopping.model.uimodel.mapper.toUIModel

class ShoppingMainPresenter(
    private val view: ShoppingMainContract.View,
    private val productsRepository: ProductRepository,
    private val cartProductRepository: CartProductRepository,
    private val recentProductsRepository: RecentProductsRepository
) : ShoppingMainContract.Presenter {
    private var index: Pair<Int, Int> = Pair(INIT_INDEX, PRODUCT_LOAD_UNIT)
    private var _isPossibleLoad = POSSIBLE_LOAD
    override val isPossibleLoad
        get() = _isPossibleLoad

    override fun loadProducts() {
        productsRepository.loadProducts(
            index,
            onSuccess = { products ->
                index = Pair(index.first + PRODUCT_LOAD_UNIT, index.second + PRODUCT_LOAD_UNIT)

                if (products.size < PRODUCT_LOAD_UNIT) {
                    view.deactivateButton()
                    _isPossibleLoad = IMPOSSIBLE_LOAD
                }

                view.showMoreProducts(products.toUIModel())
            },
            onFailure = { }
        )
    }

    override fun getRecentProducts(): List<RecentProductUIModel> {
        return recentProductsRepository.getAll().map { it.toUIModel() }
    }

    override fun loadProductDetailPage() {
        view.showProductDetailPage()
    }

    override fun loadMoreScroll() {
        loadProducts()
        view.deactivateButton()
    }

    override fun updateCartBadge() {
        view.updateCartBadgeCount(cartProductRepository.getAllProductsCount())
    }

    override fun updateProductCartCount(productUIModel: ProductUIModel): Int {
        return cartProductRepository.findCountById(productUIModel.id)
    }

    override fun addToCart(productUIModel: ProductUIModel) {
        cartProductRepository.add(CartProduct(productUIModel.toDomain(), Count(1), false))
        updateCartBadge()
    }

    override fun updateCart(productUIModel: ProductUIModel, count: Int) {
        cartProductRepository.updateCount(productUIModel.toDomain(), count)
        updateCartBadge()
    }

    companion object {
        private const val INIT_INDEX = 0
        private const val PRODUCT_LOAD_UNIT = 8
        private const val POSSIBLE_LOAD = true
        private const val IMPOSSIBLE_LOAD = false
    }
}
