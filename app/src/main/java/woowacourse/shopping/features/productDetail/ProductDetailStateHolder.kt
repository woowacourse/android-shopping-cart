package woowacourse.shopping.features.productDetail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import woowacourse.shopping.domain.cart.model.CartItem
import woowacourse.shopping.domain.cart.model.CartItemQuantity
import woowacourse.shopping.domain.cart.repository.CartRepository
import woowacourse.shopping.domain.product.model.ImageUrl
import woowacourse.shopping.domain.product.model.Price
import woowacourse.shopping.domain.product.model.Product
import woowacourse.shopping.domain.product.model.ProductName

class ProductDetailStateHolder(
    parcelProduct: ParcelProduct,
    private val cartRepository: CartRepository,
) {
    val product: Product = toProduct(parcelProduct)
    var productPrice: Int by mutableStateOf(product.price.value)
    var quantity by mutableStateOf(1)
    var minusEnabled by mutableStateOf(false)

    fun addToCart() {
        val cartItem = CartItem(product = product, quantity = CartItemQuantity(0))
        cartRepository.addCartItem(cartItem, quantity)
    }

    fun increaseCartItem() {
        quantity += 1
        productPrice += product.price.value
        minusEnabled = quantity > 1
    }

    fun decreaseCartItem() {
        quantity -= 1
        minusEnabled = quantity > 1
        productPrice -= product.price.value
    }

    companion object {
        fun from(product: Product): ParcelProduct =
            ParcelProduct(
                id = product.id,
                name = product.name.value,
                price = product.price.value,
                imageUrl = product.imageUrl.value,
            )

        fun toProduct(parcelProduct: ParcelProduct): Product =
            Product(
                id = parcelProduct.id,
                name = ProductName(parcelProduct.name),
                price = Price(parcelProduct.price),
                imageUrl = ImageUrl(parcelProduct.imageUrl),
            )
    }
}
