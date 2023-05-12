package woowacourse.shopping.feature.model.mapper

import com.example.domain.RecentProduct
import woowacourse.shopping.feature.list.item.RecentProductListItem
import woowacourse.shopping.feature.model.RecentProductState

fun RecentProduct.toUi(): RecentProductState {
    return RecentProductState(productId, productImageUrl, productName)
}

fun RecentProductListItem.toUi(): RecentProductState {
    return RecentProductState(productId, productImageUrl, productName)
}

fun RecentProductState.toDomain(): RecentProduct {
    return RecentProduct(productId, productImageUrl, productName)
}

fun RecentProductState.toItem(): RecentProductListItem =
    RecentProductListItem(productId, productImageUrl, productName)
