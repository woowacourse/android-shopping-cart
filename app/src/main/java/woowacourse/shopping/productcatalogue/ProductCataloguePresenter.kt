package woowacourse.shopping.productcatalogue

import com.shopping.domain.CartProduct
import woowacourse.shopping.datas.CartRepository
import woowacourse.shopping.datas.ProductRepository
import woowacourse.shopping.datas.RecentRepository
import woowacourse.shopping.mapper.toDomain
import woowacourse.shopping.mapper.toUIModel
import woowacourse.shopping.uimodel.ProductUIModel

class ProductCataloguePresenter(
    private val view: ProductCatalogueContract.View,
    private val productRepository: ProductRepository,
    private val recentProductRepository: RecentRepository,
    private val cartRepository: CartRepository,
) : ProductCatalogueContract.Presenter {

    override fun getRecentProduct() {
        val recentProducts =
            recentProductRepository.getAll()
                .sortedBy { it.time }
                .takeLast(RECENT_PRODUCT_UNIT_SIZE)
                .map { it.toUIModel() }
        view.setRecentProductList(recentProducts)
    }

    override fun readMoreOnClick(unitSize: Int, page: Int) {
        productRepository.getUnitData(unitSize, page).map { it.toUIModel() }
        view.notifyDataChanged()
    }

    override fun updateCartCount() {
        view.setCartCountCircle(cartRepository.getSize())
    }

    override fun updateCartProductCount(product: ProductUIModel, count: Int) {
        if (count <= 0) return
        if (count == 1) cartRepository.insert(CartProduct(true, 1, product.toDomain()))
        cartRepository.updateProductCount(product.id, count)
    }

    override fun getProductCount(product: ProductUIModel): Int {
        val count = cartRepository.getProductCount(product.id)
        if (count == 1) cartRepository.insert(CartProduct(true, 1, product.toDomain()))
        return count
    }

    companion object {
        private const val RECENT_PRODUCT_UNIT_SIZE = 10
    }
}
