package woowacourse.shopping.model.mapper

import com.example.domain.CartProduct
import woowacourse.shopping.list.item.CartProductListItem
import woowacourse.shopping.model.CartProductState

fun CartProduct.toUi(): CartProductState {
    return CartProductState(productId, productImageUrl, productName, productPrice)
}
fun CartProduct.toItem(): CartProductListItem {
    return CartProductListItem(productId, productImageUrl, productName, productPrice)
}

fun CartProductState.toDomain(): CartProduct {
    return CartProduct(productId, productImageUrl, productName, productPrice)
}

fun CartProductState.toItem(): CartProductListItem {
    return CartProductListItem(productId, productImageUrl, productName, productPrice)
}

fun CartProductListItem.toDomain(): CartProduct {
    return CartProduct(productId, productImageUrl, productName, productPrice)
}

fun CartProductListItem.toUi(): CartProductState {
    return CartProductState(productId, productImageUrl, productName, productPrice)
}
