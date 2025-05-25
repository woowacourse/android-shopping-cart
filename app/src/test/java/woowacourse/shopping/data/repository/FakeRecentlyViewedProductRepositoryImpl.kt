package woowacourse.shopping.data.repository

import woowacourse.shopping.data.entity.CartProductEntity
import woowacourse.shopping.data.mapper.toEntity
import woowacourse.shopping.product.catalog.ProductUiModel

class FakeRecentlyViewedProductRepositoryImpl(
    val catalogProductRepository: CatalogProductRepository,
) : RecentlyViewedProductRepository {
    val productUids: LinkedHashSet<Int> = linkedSetOf()

    override fun insertRecentlyViewedProductUid(uid: Int) {
        productUids.add(uid)
    }

    override fun getRecentlyViewedProducts(callback: (List<CartProductEntity>) -> Unit) {
        val products: List<ProductUiModel> =
            catalogProductRepository.getCartProductsByUids(productUids.toList())
        callback(products.map { it.toEntity() })
    }

    override fun getLatestViewedProduct(callback: (ProductUiModel) -> Unit) {
        val lastIndex = listOf(productUids.last)
        val product: ProductUiModel? =
            catalogProductRepository.getCartProductsByUids(lastIndex).firstOrNull()
        product?.let { callback(it) }
    }
}
