package woowacourse.shopping.feature.mapper

import com.example.domain.Product
import woowacourse.shopping.feature.list.item.ProductListItem
import woowacourse.shopping.feature.model.ProductState

fun ProductState.toItem(): ProductListItem {
    return ProductListItem(id, imageUrl, name, price)
}

fun ProductListItem.toUi(): ProductState {
    return ProductState(id, imageUrl, name, price)
}

fun Product.toUi(): ProductState {
    return ProductState(id, imageUrl, name, price)
}
