package woowacourse.shopping.productcatalogue

import com.shopping.domain.CartProduct
import com.shopping.domain.CartRepository
import com.shopping.domain.Product
import com.shopping.domain.ProductRepository
import com.shopping.domain.RecentRepository
import woowacourse.shopping.mapper.toDomain
import woowacourse.shopping.mapper.toUIModel
import woowacourse.shopping.uimodel.CartProductUIModel
import woowacourse.shopping.uimodel.ProductUIModel

class ProductCataloguePresenter(
    private val view: ProductCatalogueContract.View,
    private val productRepository: ProductRepository,
    private val recentProductRepository: RecentRepository,
    private val cartRepository: CartRepository,
) : ProductCatalogueContract.Presenter {
    private var productsSize = MAIN_PRODUCT_UNIT_SIZE

    override fun fetchRecentProduct() {
        val recentProducts =
            recentProductRepository.getAll()
                .sortedBy { it.time }
                .takeLast(RECENT_PRODUCT_UNIT_SIZE)
                .map { it.toUIModel() }
        view.setRecentProductList(recentProducts)
    }

    override fun fetchMoreProducts(unitSize: Int, page: Int) {
        Thread {
            productRepository.getUnitData(
                unitSize,
                page,
                onFailure = {},
                onSuccess = {
                    view.attachNewProducts(it.map { product: Product -> product.toUIModel() })
                    productsSize += MAIN_PRODUCT_UNIT_SIZE
                }
            )
        }.start()
        fetchProductsSizeForUpdateLayoutManager()
    }

    override fun fetchCartCount() {
        view.setCartCountCircle(cartRepository.getSize())
    }

    override fun decreaseCartProductCount(cartProduct: CartProductUIModel, count: Int) {
        val decreasedCount = count - 1
        if (decreasedCount == 0) {
            cartRepository.remove(cartProduct.product.id)
            return
        }
        if (decreasedCount < 0) return
        cartRepository.updateProductCount(cartProduct.product.id, decreasedCount)
    }

    override fun increaseCartProductCount(cartProduct: CartProductUIModel, count: Int) {
        val increasedCount = count + 1
        if (increasedCount == 1) {
            cartRepository.insert(CartProduct(true, 1, cartProduct.product.toDomain()))
            return
        }
        cartRepository.updateProductCount(cartProduct.product.id, increasedCount)
    }

    override fun fetchProductsSizeForUpdateLayoutManager() {
        view.setGridLayoutManager(productsSize)
    }

    override fun getProductCount(product: ProductUIModel): Int {
        val count = cartRepository.getProductCount(product.id)
        if (count == 1) cartRepository.insert(CartProduct(true, 1, product.toDomain()))
        return count
    }

    companion object {
        private const val MAIN_PRODUCT_UNIT_SIZE = 20
        private const val RECENT_PRODUCT_UNIT_SIZE = 10
    }
}
