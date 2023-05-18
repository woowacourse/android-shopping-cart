package woowacourse.shopping.shopping

import woowacourse.shopping.common.model.CartProductModel
import woowacourse.shopping.common.model.mapper.CartProductMapper.toViewModel
import woowacourse.shopping.common.model.mapper.ProductMapper.toDomainModel
import woowacourse.shopping.common.model.mapper.RecentProductMapper.toViewModel
import woowacourse.shopping.data.repository.CartRepository
import woowacourse.shopping.data.repository.RecentProductRepository
import woowacourse.shopping.data.repository.ShoppingRepository
import woowacourse.shopping.domain.RecentProduct

class ShoppingPresenter(
    private val view: ShoppingContract.View,
    private val shoppingRepository: ShoppingRepository,
    private val cartRepository: CartRepository,
    private val recentProductRepository: RecentProductRepository,
    private val recentProductSize: Int,
    private val productLoadSize: Int,
) : ShoppingContract.Presenter {
    private var productLoadedCount: Int = 0

    init {
        shoppingRepository.initMockData()
        productLoadedCount += productLoadSize
    }

    override fun reloadProducts() {
        updateProducts()
        updateRecentProducts()
    }

    override fun openProduct(cartProduct: CartProductModel) {
        val recentProducts = recentProductRepository.selectAll()
        val recentProduct = recentProducts.makeRecentProduct(cartProduct.product.toDomainModel())

        recentProductRepository.insertRecentProduct(recentProduct)

        view.showProductDetail(cartProduct)
    }

    override fun openCart() {
        view.showCart()
    }

    override fun loadMoreProduct() {
        val loadedProducts = shoppingRepository.selectByRange(productLoadedCount, productLoadSize)
        productLoadedCount += productLoadSize
        view.addProducts(loadedProducts.products.map { it.toViewModel() })
    }

    override fun minusCartProduct(cartProduct: CartProductModel) {
        cartRepository.minusCartProduct(cartProduct.product.toDomainModel())
        updateProducts()
    }

    override fun plusCartProduct(cartProduct: CartProductModel) {
        cartRepository.plusCartProduct(cartProduct.product.toDomainModel())
        updateProducts()
    }

    private fun updateProducts() {
        val loadedProducts = shoppingRepository.selectByRange(0, productLoadSize)
        view.updateProducts(loadedProducts.products.map { it.toViewModel() })
        view.updateCartProductsCount(cartRepository.selectAllCount())
    }

    private fun updateRecentProducts() {
        val recentProducts = recentProductRepository.selectAll()
        val sortedRecentProducts =
            recentProducts.getRecentProducts(recentProductSize).value.sortedByDescending(
                RecentProduct::ordinal
            )
        view.updateRecentProducts(sortedRecentProducts.map { it.toViewModel() })
    }
}
