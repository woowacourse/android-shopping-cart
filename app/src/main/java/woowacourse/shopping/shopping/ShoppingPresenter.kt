package woowacourse.shopping.shopping

import woowacourse.shopping.common.model.CartProductModel
import woowacourse.shopping.common.model.ProductModel
import woowacourse.shopping.common.model.mapper.CartProductMapper.toViewModel
import woowacourse.shopping.common.model.mapper.ProductMapper.toDomainModel
import woowacourse.shopping.common.model.mapper.ProductMapper.toViewModel
import woowacourse.shopping.common.model.mapper.RecentProductMapper.toViewModel
import woowacourse.shopping.data.repository.CartRepository
import woowacourse.shopping.data.repository.ProductRepository
import woowacourse.shopping.data.repository.RecentProductRepository
import woowacourse.shopping.data.repository.ShopRepository
import woowacourse.shopping.domain.RecentProduct
import woowacourse.shopping.domain.RecentProducts

class ShoppingPresenter(
    private val view: ShoppingContract.View,
    private val productRepository: ProductRepository,
    private val shopRepository: ShopRepository,
    private val cartRepository: CartRepository,
    private val recentProductRepository: RecentProductRepository,
    private val recentProductSize: Int,
    private val productLoadSize: Int,
) : ShoppingContract.Presenter {
    private var productLoadedCount: Int = 0

    init {
        productRepository.initMockData()
        productLoadedCount += productLoadSize
    }

    override fun reloadProducts() {
        updateProducts()
        updateRecentProducts()
    }

    override fun showProductDetail(cartProduct: CartProductModel) {
        val recentProducts = recentProductRepository.selectAll()
        val recentProduct = recentProducts.makeRecentProduct(cartProduct.product.toDomainModel())

        recentProductRepository.insertRecentProduct(recentProduct)

        val currentRecentProduct = getMostRecentProduct(recentProducts)
        view.showProductDetail(cartProduct, currentRecentProduct)
    }

    override fun openCart() {
        view.showCart()
    }

    override fun loadMoreProduct() {
        val loadedProducts = productRepository.selectByRange(productLoadedCount, productLoadSize)
        val loadedShop = shopRepository.selectByProducts(loadedProducts)
        productLoadedCount += productLoadSize
        view.addProducts(loadedShop.products.map { it.toViewModel() })
    }

    override fun minusCartProduct(cartProduct: CartProductModel) {
        cartRepository.minusCartProduct(cartProduct.product.toDomainModel())
        updateProducts()
    }

    override fun plusCartProduct(cartProduct: CartProductModel) {
        cartRepository.plusCartProduct(cartProduct.product.toDomainModel())
        updateProducts()
    }

    private fun getMostRecentProduct(recentProducts: RecentProducts): ProductModel? {
        return if (recentProducts.value.isNotEmpty()) recentProducts.value.last().product.toViewModel()
        else null
    }

    private fun updateProducts() {
        val loadedProducts = productRepository.selectByRange(0, productLoadSize)
        val loadedShop = shopRepository.selectByProducts(loadedProducts)
        view.updateProducts(loadedShop.products.map { it.toViewModel() })
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
