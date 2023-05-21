package woowacourse.shopping.feature.model.mapper

import com.example.domain.Product
import woowacourse.shopping.feature.list.item.ProductView
import woowacourse.shopping.feature.model.ProductState

fun ProductState.toItem(): ProductView.ProductItem {
    return ProductView.ProductItem(id, imageUrl, name, price)
}

fun ProductState.toDomain(): Product {
    return Product(id, imageUrl, name, price)
}

fun ProductView.ProductItem.toUi(): ProductState {
    return ProductState(id, imageUrl, name, price)
}

fun Product.toUi(): ProductState {
    return ProductState(id, imageUrl, name, price)
}
