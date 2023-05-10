package woowacourse.shopping.presentation.view.productlist

import woowacourse.shopping.data.respository.product.ProductRepository
import woowacourse.shopping.data.respository.product.ProductRepositoryImp
import woowacourse.shopping.data.respository.recentproduct.RecentProductRepository

class ProductListPresenter(
    private val view: ProductContract.View,
    private val productRepository: ProductRepository = ProductRepositoryImp(),
    private val recentProductRepository: RecentProductRepository
) : ProductContract.Presenter {
    override fun loadProductItems() {
        val products = productRepository.getData()
        view.setProductItemsView(products)
    }

    override fun loadRecentProductItems() {
        val recentProducts = recentProductRepository.getRecentProducts()
        view.setRecentProductItemsView(recentProducts)
    }

    override fun saveRecentProduct(productId: Long) {
        recentProductRepository.addCart(productId)
    }
}
