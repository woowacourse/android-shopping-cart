package woowacourse.shopping.model.mapper

import com.example.domain.RecentProduct
import woowacourse.shopping.model.RecentProductState

fun RecentProduct.toUi(): RecentProductState {
    return RecentProductState(productId, productImageUrl, productName)
}
