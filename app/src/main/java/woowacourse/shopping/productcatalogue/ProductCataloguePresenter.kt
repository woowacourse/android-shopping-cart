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
    private var productsSize = 20

    override fun getRecentProduct() {
        val recentProducts =
            recentProductRepository.getAll()
                .sortedBy { it.time }
                .takeLast(RECENT_PRODUCT_UNIT_SIZE)
                .map { it.toUIModel() }
        view.setRecentProductList(recentProducts)
    }

    override fun getNewProducts(unitSize: Int, page: Int) {
        Thread {
            productRepository.getUnitData(
                unitSize,
                page,
                onFailure = {},
                onSuccess = {
                    view.attachNewProducts(it.map { product: Product -> product.toUIModel() })
                    productsSize += 20
                }
            )
        }.start()
        getSpanSize()
    }

    override fun updateCartCount() {
        view.setCartCountCircle(cartRepository.getSize())
    }

    override fun decreaseCartProductCount(cartProduct: CartProductUIModel, count: Int) {
        if (count == 0) {
            deleteCartProduct(cartProduct)
            return
        }
        if (count < 0) return
        updateCartProductCount(cartProduct, count)
    }

    override fun increaseCartProductCount(cartProduct: CartProductUIModel, count: Int) {
        if (count == 1) {
            cartRepository.insert(CartProduct(true, 1, cartProduct.product.toDomain()))
            return
        }
        updateCartProductCount(cartProduct, count)
    }

    override fun getSpanSize() {
        view.setGridLayoutManager(productsSize)
    }

    override fun getProductCount(product: ProductUIModel): Int {
        val count = cartRepository.getProductCount(product.id) // 조회 했을 떄 없다면 0을 리턴
        if (count == 1) cartRepository.insert(CartProduct(true, 1, product.toDomain()))
        return count
    }

    override fun deleteCartProduct(cartProduct: CartProductUIModel) {
        cartRepository.remove(cartProduct.product.id)
    }

    private fun updateCartProductCount(cartProduct: CartProductUIModel, count: Int) {
        cartRepository.updateProductCount(cartProduct.product.id, count)
    }

    companion object {
        private const val RECENT_PRODUCT_UNIT_SIZE = 10
    }
}
