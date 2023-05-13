package woowacourse.shopping.feature.model.mapper

import com.example.domain.CartProduct
import woowacourse.shopping.feature.list.item.CartProductItem

fun CartProduct.toItem(): CartProductItem {
    return CartProductItem(productId, productImageUrl, productName, productPrice)
}

fun CartProductItem.toDomain(): CartProduct {
    return CartProduct(productId, productImageUrl, productName, productPrice)
}
