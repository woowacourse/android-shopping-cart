package woowacourse.shopping.model.mapper

import com.example.domain.Product
import com.example.domain.RecentProduct
import woowacourse.shopping.list.item.ProductListItem
import woowacourse.shopping.model.ProductState

fun Product.toUi(): ProductState {
    return ProductState(id, imageUrl, name, price)
}

fun Product.toRecentProduct(): RecentProduct {
    return RecentProduct(id, imageUrl, name)
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
