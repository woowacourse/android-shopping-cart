package woowacourse.shopping.feature.model.mapper

import com.example.domain.CartProduct
import com.example.domain.Product
import woowacourse.shopping.feature.list.item.ProductView.CartProductItem

fun CartProduct.toUi(): CartProductItem {
    val cartProductItem =
        CartProductItem(productId, productImageUrl, productName, productPrice)
    cartProductItem.updateCount(count)
    return cartProductItem
}

fun CartProductItem.toDomain(): CartProduct {
    return CartProduct(id, imageUrl, name, price, count)
}

fun CartProductItem.toProductDomain(): Product {
    return Product(id, imageUrl, name, price)
}
