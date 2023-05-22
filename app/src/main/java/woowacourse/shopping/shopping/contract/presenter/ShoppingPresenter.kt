package woowacourse.shopping.shopping.contract.presenter

import com.domain.model.CartRepository
import com.domain.model.ProductRepository
import com.domain.model.RecentRepository
import woowacourse.shopping.mapper.toDomain
import woowacourse.shopping.mapper.toUIModel
import woowacourse.shopping.model.ProductUIModel
import woowacourse.shopping.shopping.ProductItem
import woowacourse.shopping.shopping.ProductReadMore
import woowacourse.shopping.shopping.ProductsItemType
import woowacourse.shopping.shopping.RecentProductsItem
import woowacourse.shopping.shopping.contract.ShoppingContract

class ShoppingPresenter(
    private val view: ShoppingContract.View,
    private val productOffset: Int,
    private val repository: ProductRepository,
    private val recentRepository: RecentRepository,
    private val cartRepository: CartRepository,
) : ShoppingContract.Presenter {
    private val productItemTypes = mutableListOf<ProductsItemType>()
    private val recentProductsData: RecentProductsItem
        get() = RecentProductsItem(
            recentRepository.getRecent(RECENT_PRODUCT_COUNT).map { it.toUIModel() },
        )

    override fun setUpCarts() {
        val count = cartRepository.getAll().size
        view.setUpCarts(count)
    }

    override fun setUpProducts() {
        productItemTypes.clear()
        productItemTypes.apply {
            recentProductsData.product.takeIf { it.isNotEmpty() }?.let { add(recentProductsData) }
            addAll(repository.getUntil(productOffset).map { ProductItem(it.toUIModel()) })
            add(ProductReadMore)
        }
        view.setProducts(productItemTypes)
    }

    override fun updateRecentProducts() {
        val recentProducts = recentProductsData
        when {
            productItemTypes[0] is RecentProductsItem -> productItemTypes[0] = recentProducts
            recentProducts.product.isNotEmpty() -> productItemTypes.add(0, recentProducts)
        }
        view.updateRecentProducts(productItemTypes)
    }

    override fun fetchMoreProducts() {
        val products = repository.getNext(PRODUCT_COUNT).map { ProductItem(it.toUIModel()) }
        productItemTypes.addAll(productItemTypes.size - 1, products)
        val start = if (productItemTypes[0] is RecentProductsItem) 1 else 0
        view.updateProducts(start, productItemTypes)
    }

    override fun navigateToItemDetail(data: ProductUIModel) {
        view.navigateToProductDetail(data)
    }

    override fun getOffset(): Int {
        return repository.getOffset()
    }

    override fun addCart(product: ProductUIModel) {
        cartRepository.insert(product.toDomain(), 1)
        setUpCarts()
    }

    override fun addOneInCart(product: ProductUIModel) {
        val targetProduct = cartRepository.findById(product.id)
        cartRepository.updateCount(product.id, targetProduct?.count?.plus(1) ?: 0)
    }

    override fun removeOneInCart(product: ProductUIModel) {
        val targetProduct = cartRepository.findById(product.id)
        cartRepository.updateCount(product.id, targetProduct?.count?.minus(1) ?: 0)
    }

    override fun loadCartCount(id: Int): Int {
        val cartProduct = cartRepository.findById(id)
        return cartProduct?.count ?: 0
    }

    companion object {
        private const val RECENT_PRODUCT_COUNT = 10
        private const val PRODUCT_COUNT = 20
    }
}
