package woowacourse.shopping.shopping

import woowacourse.shopping.common.model.ProductModel
import woowacourse.shopping.common.model.mapper.ProductMapper.toDomain
import woowacourse.shopping.common.model.mapper.ProductMapper.toView
import woowacourse.shopping.common.model.mapper.RecentProductMapper.toView
import woowacourse.shopping.data.database.dao.RecentProductDao
import woowacourse.shopping.data.state.RecentProductsState
import woowacourse.shopping.data.state.State
import woowacourse.shopping.domain.RecentProduct
import woowacourse.shopping.domain.RecentProducts
import woowacourse.shopping.domain.repository.ProductRepository

class ShoppingPresenter(
    private val view: ShoppingContract.View,
    private val productRepository: ProductRepository,
    private var recentProductsState: State<RecentProducts> = RecentProductsState,
    private val recentProductDao: RecentProductDao,
    private val recentProductSize: Int,
    private val productLoadSize: Int,
) : ShoppingContract.Presenter {
    private var productSize: Int = 0

    init {
        loadMoreProduct()
        val recentProducts = recentProductDao.selectAll()
        recentProductsState.save(recentProducts)
    }

    override fun updateRecentProducts() {
        val recentProducts = recentProductsState.load()
        view.updateRecentProducts(recentProducts.getRecentProducts(recentProductSize).value.map { it.toView() })
    }

    override fun openProduct(productModel: ProductModel) {
        updateRecentProducts(productModel)
        view.showProductDetail(productModel)
    }

    private fun updateRecentProducts(productModel: ProductModel) {
        val recentProducts = recentProductsState.load()
        var recentProduct = recentProductDao.selectByProduct(productModel)

        if (recentProduct == null) {
            recentProduct = recentProducts.makeRecentProduct(productModel.toDomain())
            addRecentProduct(recentProducts, recentProduct)
        } else {
            recentProduct = recentProduct.updateTime()
            updateRecentProduct(recentProducts, recentProduct)
        }
    }

    private fun addRecentProduct(
        recentProducts: RecentProducts,
        recentProduct: RecentProduct
    ) {
        recentProductsState.save(recentProducts.add(recentProduct))
        recentProductDao.insertRecentProduct(recentProduct.toView())
    }

    private fun updateRecentProduct(
        recentProducts: RecentProducts,
        recentProduct: RecentProduct
    ) {
        recentProductsState.save(recentProducts.update(recentProduct))
        recentProductDao.updateRecentProduct(recentProduct.toView())
    }

    override fun openCart() {
        view.showCart()
    }

    override fun loadMoreProduct() {
        val loadedProducts = productRepository.getProducts(productSize, productLoadSize)
        productSize += loadedProducts.value.size
        view.addProducts(loadedProducts.value.map { it.toView() })
    }
}
