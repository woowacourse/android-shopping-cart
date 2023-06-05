package woowacourse.shopping.feature.cart.model

import com.example.domain.CartProduct

fun CartProduct.toUi(): CartProductState {
    return CartProductState(productId, productImageUrl, productName, productPrice, count, checked)
}

fun CartProductState.toDomain(): CartProduct {
    return CartProduct(productId, productImageUrl, productName, productPrice, count, checked)
}
