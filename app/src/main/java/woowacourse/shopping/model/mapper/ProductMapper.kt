package woowacourse.shopping.model.mapper

import com.example.domain.Product
import com.example.domain.RecentProduct
import woowacourse.shopping.list.item.ProductListItem
import woowacourse.shopping.model.ProductState
import java.time.LocalDateTime

fun Product.toUi(): ProductState {
    return ProductState(id, imageUrl, name, price)
}

fun Product.toRecentProduct(nowDateTime: LocalDateTime): RecentProduct {
    return RecentProduct(id, imageUrl, name, nowDateTime)
}

fun ProductState.toItem(): ProductListItem {
    return ProductListItem(id, imageUrl, name, price)
}

fun ProductState.toDomain(): Product {
    return Product(id, imageUrl, name, price)
}

fun ProductListItem.toUi(): ProductState {
    return ProductState(id, imageUrl, name, price)
}
