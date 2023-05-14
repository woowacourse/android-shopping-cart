package woowacourse.shopping.ui.shopping

import woowacourse.shopping.mapper.toUIModel
import woowacourse.shopping.model.Product
import woowacourse.shopping.repository.ProductRepository
import woowacourse.shopping.repository.RecentRepository
import woowacourse.shopping.ui.shopping.productAdapter.ProductsItemType

class ShoppingPresenter(
    private val view: ShoppingContract.View,
    private val productRepository: ProductRepository,
    private val recentRepository: RecentRepository
) : ShoppingContract.Presenter {
    private var productsData: MutableList<ProductsItemType> = mutableListOf()

    override fun setUpProducts() {
        setRecentProduct()

        productsData += productRepository.getNext(PRODUCT_COUNT)
            .map { product: Product -> ProductsItemType.Product(product.toUIModel()) }
        view.setProducts(productsData.plus(ProductsItemType.ReadMore))
    }

    override fun updateProducts() {
        setRecentProduct()

        view.setProducts(productsData.plus(ProductsItemType.ReadMore))
    }

    override fun fetchMoreProducts() {
        setRecentProduct()

        productsData += productRepository.getNext(PRODUCT_COUNT)
            .map { ProductsItemType.Product(it.toUIModel()) }
        view.addProducts(productsData.plus(ProductsItemType.ReadMore))
    }

    override fun navigateToItemDetail(productId: Int) {
        productRepository.findById(productId).let {
            view.navigateToProductDetail(it.toUIModel())
        }
    }

    private fun setRecentProduct() {
        val recentProductsData = ProductsItemType.RecentProducts(
            recentRepository.getRecent(RECENT_PRODUCT_COUNT).map { it.toUIModel() }
        )

        when {
            productsData.isNotEmpty() && productsData[0] is ProductsItemType.RecentProducts -> productsData[0] = recentProductsData
            recentProductsData.product.isNotEmpty() -> productsData.add(0, recentProductsData)
        }
    }

    companion object {
        private const val RECENT_PRODUCT_COUNT = 10
        private const val PRODUCT_COUNT = 20
    }
}
