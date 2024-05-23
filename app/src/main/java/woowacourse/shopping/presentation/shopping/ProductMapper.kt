package woowacourse.shopping.presentation.shopping

import woowacourse.shopping.domain.entity.CartProduct
import woowacourse.shopping.domain.entity.Product
import woowacourse.shopping.presentation.cart.CartProductUi
import woowacourse.shopping.presentation.shopping.detail.ProductUi
import woowacourse.shopping.presentation.shopping.product.ShoppingUiModel

fun CartProduct.toShoppingUiModel(): ShoppingUiModel.Product {
    return product.toShoppingUiModel().copy(count = count)
}

fun Product.toShoppingUiModel(): ShoppingUiModel.Product {
    return ShoppingUiModel.Product(id, name, price, imageUrl)
}

fun Product.toUiModel(): ProductUi {
    return ProductUi(id, name, price, imageUrl)
}

fun Product.toCartUiModel(): CartProductUi {
    return CartProductUi(toUiModel(), 1)
}
