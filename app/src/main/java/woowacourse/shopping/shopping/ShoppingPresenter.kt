package woowacourse.shopping.shopping

import woowacourse.shopping.common.data.dao.ProductDao
import woowacourse.shopping.common.data.dao.RecentProductDao
import woowacourse.shopping.common.data.database.state.ProductsState
import woowacourse.shopping.common.data.database.state.RecentProductsState
import woowacourse.shopping.common.data.database.state.State
import woowacourse.shopping.common.model.ProductModel
import woowacourse.shopping.common.model.mapper.ProductMapper.toDomainModel
import woowacourse.shopping.common.model.mapper.ProductMapper.toViewModel
import woowacourse.shopping.common.model.mapper.RecentProductMapper.toViewModel
import woowacourse.shopping.domain.Products
import woowacourse.shopping.domain.RecentProduct
import woowacourse.shopping.domain.RecentProducts

class ShoppingPresenter(
    private val view: ShoppingContract.View,
    private var productsState: State<Products> = ProductsState,
    private val productDao: ProductDao,
    private var recentProductsState: State<RecentProducts> = RecentProductsState,
    private val recentProductDao: RecentProductDao,
    private val recentProductSize: Int,
    private val productLoadSize: Int,
) : ShoppingContract.Presenter {

    init {
        productDao.initMockData()
        loadMoreProduct()
        val recentProducts = recentProductDao.selectAll()
        recentProductsState.save(recentProducts)
    }

    override fun resumeView() {
        updateRecentProducts()
    }

    override fun openProduct(productModel: ProductModel) {
        val recentProducts = recentProductsState.load()
        val recentProduct = recentProducts.makeRecentProduct(productModel.toDomainModel())

        recentProductsState.save(recentProducts.add(recentProduct))
        recentProductDao.insertRecentProduct(recentProduct.toViewModel())

        view.showProductDetail(productModel)
    }

    override fun openCart() {
        view.showCart()
    }

    override fun loadMoreProduct() {
        val products = productsState.load()
        val loadedProducts = productDao.selectByRange(products.value.size, productLoadSize)
        productsState.save(products + loadedProducts)
        view.addProducts(loadedProducts.value.map { it.toViewModel() })
    }

    private fun updateRecentProducts() {
        val recentProducts = recentProductsState.load()
        val recentProductsDesc =
            recentProducts.getRecentProducts(recentProductSize).value.sortedByDescending(
                RecentProduct::ordinal
            )
        view.updateRecentProducts(recentProductsDesc.map { it.toViewModel() })
    }
}
