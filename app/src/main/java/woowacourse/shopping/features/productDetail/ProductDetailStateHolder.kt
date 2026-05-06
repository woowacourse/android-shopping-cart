package woowacourse.shopping.features.productDetail

import woowacourse.shopping.domain.cart.model.CartItem
import woowacourse.shopping.domain.cart.repository.CartRepository
import woowacourse.shopping.domain.product.model.ImageUrl
import woowacourse.shopping.domain.product.model.Price
import woowacourse.shopping.domain.product.model.Product
import woowacourse.shopping.domain.product.model.ProductName

class ProductDetailStateHolder(
    private val cartRepository: CartRepository,
) {
    fun addToCart(parcelProduct: ParcelProduct) {
        cartRepository.addCartItem(CartItem(product = toProduct(parcelProduct)))
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
