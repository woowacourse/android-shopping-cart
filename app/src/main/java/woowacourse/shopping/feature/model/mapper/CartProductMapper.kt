package woowacourse.shopping.feature.model.mapper

import com.example.domain.CartProduct
import com.example.domain.Product
import woowacourse.shopping.feature.list.item.ProductView

fun CartProduct.toUi(): ProductView.CartProductItem {
    val cartProductItem =
        ProductView.CartProductItem(productId, productImageUrl, productName, productPrice)
    cartProductItem.updateCount(count)
    return cartProductItem
}

fun ProductView.CartProductItem.toDomain(): CartProduct {
    return CartProduct(id, imageUrl, name, price, count)
}

fun ProductView.CartProductItem.toProductDomain(): Product {
    return Product(id, imageUrl, name, price)
}
