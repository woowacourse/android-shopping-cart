package woowacourse.shopping.view.productDetail

import woowacourse.shopping.domain.product.Product

sealed interface ProductDetailEvent {
    data object CartAdditionSucceeded : ProductDetailEvent

    data object CartAdditionFailed : ProductDetailEvent

    data object RecentProductAdditionFailed : ProductDetailEvent

    data object RecentProductFetchFailed : ProductDetailEvent

    data class UpdatedProductRequested(
        val product: Product,
        val previousUpdatedProduct: Product? = null,
    ) : ProductDetailEvent

    data class RecentProductRequested(
        val currentProduct: Product,
        val recentProduct: Product,
    ) : ProductDetailEvent
}
