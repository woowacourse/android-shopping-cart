package woowacourse.shopping.model.mapper

import com.example.domain.RecentProduct
import woowacourse.shopping.list.item.RecentProductListItem
import woowacourse.shopping.model.RecentProductState

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
