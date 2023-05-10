package woowacourse.shopping.shopping

import woowacourse.shopping.common.data.database.dao.ProductDao
import woowacourse.shopping.common.data.database.dao.RecentProductDao
import woowacourse.shopping.common.data.database.state.ProductsState
import woowacourse.shopping.common.data.database.state.RecentProductsState
import woowacourse.shopping.common.data.database.state.State
import woowacourse.shopping.common.model.ProductModel
import woowacourse.shopping.common.model.mapper.ProductMapper.toDomain
import woowacourse.shopping.common.model.mapper.ProductMapper.toView
import woowacourse.shopping.common.model.mapper.RecentProductMapper.toView
import woowacourse.shopping.domain.Products
import woowacourse.shopping.domain.RecentProducts

class ShoppingPresenter(
    private val view: ShoppingContract.View,
    private var products: Products = Products(emptyList()),
    private var productsState: State<Products> = ProductsState,
    productDao: ProductDao,
    private var recentProducts: RecentProducts = RecentProducts(emptyList()),
    private var recentProductsState: State<RecentProducts> = RecentProductsState,
    private val recentProductDao: RecentProductDao,
    recentProductSize: Int
) : ShoppingContract.Presenter {

    init {
        products = productDao.selectAll()
        recentProducts = recentProductDao.selectAll()
        view.updateProductList(products.value.map { it.toView() })
        view.updateRecentProductList(recentProducts.getRecentProducts(recentProductSize).value.map { it.toView() })
    }

    override fun resumeView() {
        updateProducts()
        updateRecentProducts()
    }

    private fun updateProducts() {
        products = productsState.load()
        view.updateProductList(products.value.map { it.toView() })
    }

    private fun updateRecentProducts() {
        recentProducts = recentProductsState.load()
        view.updateRecentProductList(recentProducts.value.map { it.toView() })
    }

    override fun openProduct(productModel: ProductModel) {
        val recentProduct = recentProducts.makeRecentProduct(productModel.toDomain())

        recentProducts = recentProducts.add(recentProduct)
        recentProductsState.save(recentProducts)
        recentProductDao.insertRecentProduct(recentProduct.toView())

        view.showProductDetail()
    }

    override fun openCart() {
        view.showCart()
    }
}
