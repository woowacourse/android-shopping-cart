package woowacourse.shopping.feature.product.recent.model

import com.example.domain.Product
import com.example.domain.RecentProduct

fun RecentProduct.toUi(): RecentProductState {
    return RecentProductState(productId, productImageUrl, productPrice, productName)
}

fun RecentProductState.toProduct(): Product {
    return Product(productId, productImageUrl, productName, productPrice)
}
