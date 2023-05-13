package woowacourse.shopping.feature.model.mapper

import com.example.domain.Product
import woowacourse.shopping.feature.list.item.ProductItem
import woowacourse.shopping.feature.model.ProductState

fun ProductState.toItem(): ProductItem {
    return ProductItem(id, imageUrl, name, price)
}

fun ProductState.toDomain(): Product {
    return Product(id, imageUrl, name, price)
}

fun ProductItem.toUi(): ProductState {
    return ProductState(id, imageUrl, name, price)
}

fun Product.toUi(): ProductState {
    return ProductState(id, imageUrl, name, price)
}
