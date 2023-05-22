package woowacourse.shopping.presentation.ui.home

import woowacourse.shopping.domain.model.Operator
import woowacourse.shopping.domain.model.ProductInCart
import woowacourse.shopping.domain.model.RecentlyViewedProduct
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.domain.repository.RecentlyViewedRepository
import woowacourse.shopping.domain.repository.ShoppingCartRepository
import woowacourse.shopping.domain.util.WoowaResult
import woowacourse.shopping.presentation.model.HomeData
import woowacourse.shopping.presentation.model.HomeMapper.toProductItem
import woowacourse.shopping.presentation.model.ProductItem
import woowacourse.shopping.presentation.model.RecentlyViewedItem
import woowacourse.shopping.presentation.model.ShowMoreItem
import woowacourse.shopping.presentation.ui.home.adapter.HomeViewType.PRODUCT
import woowacourse.shopping.presentation.ui.home.adapter.HomeViewType.SHOW_MORE

class HomePresenter(
    private val view: HomeContract.View,
    private val productRepository: ProductRepository,
    private val recentlyViewedRepository: RecentlyViewedRepository,
    private val shoppingCartRepository: ShoppingCartRepository,
) : HomeContract.Presenter {
    private var lastProductId: Long = 0
    private val homeData = mutableListOf<HomeData>()
    private var recentlyViewedItem = listOf<RecentlyViewedProduct>()

    override fun setHome() {
        view.setHomeData(homeData)
        fetchTotalQuantity()
    }

    override fun fetchRecentlyViewed() {
        recentlyViewedItem = recentlyViewedRepository
            .getRecentlyViewedProducts(UNIT)
        if (recentlyViewedItem.isEmpty()) return
        checkRecentlyViewedInit()
        view.updateRecentlyViewedProducts(recentlyViewedItem.toList())
    }

    override fun fetchProducts() {
        deleteShowMoreItem()
        val startPosition = homeData.size
        val products = productRepository.getProducts(UNIT, lastProductId).map { it.toProductItem() }
        lastProductId = products.lastOrNull()?.id ?: lastProductId
        homeData.addAll(products)
        view.appendProductItems(startPosition, products.size)
        checkIsLastProduct()
    }

    private fun checkIsLastProduct() {
        val isLast = productRepository.isLastProduct(lastProductId)
        if (!isLast) addShowMoreItem()
    }

    private fun checkRecentlyViewedInit() {
        if (homeData.isEmpty() || (homeData[0] !is RecentlyViewedItem)) {
            homeData.add(0, RecentlyViewedItem(recentlyViewedItem))
            view.initRecentlyViewed()
        }
    }

    private fun addShowMoreItem() {
        homeData.add(ShowMoreItem())
        view.appendShowMoreItem(homeData.size - 1)
    }

    private fun deleteShowMoreItem() {
        if (homeData.isNotEmpty() && homeData.last().viewType == SHOW_MORE) {
            homeData.removeLast()
            view.removeShowMoreItem(homeData.size)
        }
    }

    override fun updateProductQuantity(position: Int, operator: Operator) {
        val productInCart = updatedQuantity(position, operator) ?: return
        if (productInCart.quantity == 0) {
            deleteProductInCart(position, productInCart)
            return
        }
        updateProductItem(position, productInCart)
    }

    private fun updatedQuantity(position: Int, operator: Operator): ProductInCart? {
        if (homeData[position].viewType != PRODUCT) {
            view.showUnexpectedError()
            return null
        }
        return operator.operate((homeData[position] as ProductItem).productInCart)
    }

    private fun updateProductItem(position: Int, product: ProductInCart) {
        val result =
            shoppingCartRepository.updateProductQuantity(product.product.id, product.quantity)
        when (result) {
            is WoowaResult.SUCCESS -> update(position, product)
            is WoowaResult.FAIL -> {
                view.showUnexpectedError()
                println("[ERROR] " + "error message : ${result.error.errorMessage}")
            }
        }
    }

    private fun update(position: Int, productInCart: ProductInCart) {
        homeData[position] = ProductItem(productInCart)
        view.updateProductQuantity(position)
    }

    private fun deleteProductInCart(position: Int, productInCart: ProductInCart) {
        val result =
            shoppingCartRepository.deleteProductInCart((homeData[position] as ProductItem).id)
        if (result) {
            homeData[position] = ProductItem(productInCart)
            view.updateProductQuantity(position)
        }
    }

    override fun fetchTotalQuantity() {
        val size = shoppingCartRepository.getTotalQuantity()
        view.updateTotalQuantity(size)
    }

    companion object {
        private const val UNIT = 10
    }
}
