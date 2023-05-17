package woowacourse.shopping.model.mapper

import com.example.domain.CartProduct
import woowacourse.shopping.model.CartProductState

fun CartProduct.toUi(): CartProductState {
    return CartProductState(productId, productImageUrl, productName, productPrice, count, checked)
}

fun CartProductState.toDomain(): CartProduct {
    return CartProduct(productId, productImageUrl, productName, productPrice, count, checked)
}
