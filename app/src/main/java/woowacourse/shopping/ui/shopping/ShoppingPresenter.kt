package woowacourse.shopping.ui.shopping

import woowacourse.shopping.mapper.toUIModel
import woowacourse.shopping.model.CartProductUIModel
import woowacourse.shopping.repository.CartRepository
import woowacourse.shopping.repository.ProductRepository
import woowacourse.shopping.repository.RecentRepository
import woowacourse.shopping.ui.shopping.productAdapter.ProductsItemType

class ShoppingPresenter(
    private val view: ShoppingContract.View,
    private val productRepository: ProductRepository,
    private val recentRepository: RecentRepository,
    private val cartRepository: CartRepository
) : ShoppingContract.Presenter {
    private var productsData: MutableList<ProductsItemType> = mutableListOf()
    private var cartProductsData: List<CartProductUIModel> = cartRepository.getAll().toUIModel()

    override fun setUpProducts() {
        setRecentProduct()

        productsData += productRepository.getNext(PRODUCT_COUNT)
            .map { ProductsItemType.Product(it.toUIModel(), findCountById(it.id)) }
        view.setProducts(productsData.plus(ProductsItemType.ReadMore))
    }

    override fun updateProducts() {
        setRecentProduct()
        updateCartProducts()

        productsData = productsData.map {
            when (it) {
                is ProductsItemType.Product -> {
                    ProductsItemType.Product(it.product, findCountById(it.product.id))
                }
                else -> it
            }
        }.toMutableList()
        view.updateProducts(productsData.plus(ProductsItemType.ReadMore))
    }

    private fun findCountById(productId: Int): Int {
        return cartProductsData.firstOrNull { it.id == productId }?.count ?: 0
    }

    override fun fetchMoreProducts() {
        productsData += productRepository.getNext(PRODUCT_COUNT)
            .map { ProductsItemType.Product(it.toUIModel(), findCountById(it.id)) }
        view.updateProducts(productsData.plus(ProductsItemType.ReadMore))
    }

    override fun navigateToItemDetail(productId: Int) {
        productRepository.findById(productId).let {
            view.navigateToProductDetail(it.toUIModel())
        }
    }

    override fun updateItem(productId: Int, count: Int): Int {
        cartRepository.insert(productId)
        val updatedCount = cartRepository.updateCount(productId, count)
        updateCartProducts()
        return updatedCount
    }

    override fun updateCartProducts() {
        cartProductsData = cartRepository.getAll().toUIModel()
        view.updateToolbar(cartProductsData.size)
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
