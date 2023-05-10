package woowacourse.shopping.feature.model.mapper

import com.example.domain.CartProduct
import woowacourse.shopping.feature.list.item.CartProductListItem

fun CartProduct.toItem(): CartProductListItem {
    return CartProductListItem(productId, productImageUrl, productName, productPrice)
}
