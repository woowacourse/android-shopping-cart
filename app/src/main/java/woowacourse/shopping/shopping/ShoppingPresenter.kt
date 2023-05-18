package woowacourse.shopping.shopping

import woowacourse.shopping.common.model.ProductModel
import woowacourse.shopping.common.model.mapper.ProductMapper.toDomain
import woowacourse.shopping.common.model.mapper.RecentProductMapper.toView
import woowacourse.shopping.common.model.mapper.ShoppingProductMapper.toView
import woowacourse.shopping.domain.RecentProduct
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.domain.repository.RecentProductRepository

class ShoppingPresenter(
    private val view: ShoppingContract.View,
    private val productRepository: ProductRepository,
    private val recentProductRepository: RecentProductRepository,
    private val cartRepository: CartRepository,
    private val recentProductSize: Int,
    private val productLoadSize: Int,
) : ShoppingContract.Presenter {
    private var productSize: Int = 0

    init {
        loadMoreProduct()
    }

    override fun updateRecentProducts() {
        val recentProducts = recentProductRepository.getAll()
        view.updateRecentProducts(recentProducts.getRecentProducts(recentProductSize).value.map { it.toView() })
    }

    override fun setUpCartAmount() {
        updateCartAmount()
    }

    override fun openProduct(productModel: ProductModel) {
        updateRecentProducts(productModel)
        view.showProductDetail(productModel)
    }

    private fun updateRecentProducts(productModel: ProductModel) {
        val recentProducts = recentProductRepository.getAll()
        var recentProduct = recentProductRepository.getByProduct(productModel.toDomain())

        if (recentProduct == null) {
            recentProduct = recentProducts.makeRecentProduct(productModel.toDomain())
            addRecentProduct(recentProduct)
        } else {
            recentProduct = recentProduct.updateTime()
            updateRecentProduct(recentProduct)
        }
    }

    private fun addRecentProduct(recentProduct: RecentProduct) {
        recentProductRepository.addRecentProduct(recentProduct)
    }

    private fun updateRecentProduct(recentProduct: RecentProduct) {
        recentProductRepository.modifyRecentProduct(recentProduct)
    }

    override fun openCart() {
        view.showCart()
    }

    override fun loadMoreProduct() {
        val loadedProducts = productRepository.getProducts(productSize, productLoadSize)
        productSize += loadedProducts.value.size
        view.addProducts(loadedProducts.value.map { it.toView() })
    }

    private fun updateCartAmount() {
        val totalAmount = cartRepository.getTotalAmount()
        view.updateCartAmount(totalAmount)
    }
}
