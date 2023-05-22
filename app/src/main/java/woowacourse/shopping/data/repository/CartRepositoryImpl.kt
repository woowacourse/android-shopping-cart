package woowacourse.shopping.data.repository

import woowacourse.shopping.data.datasource.cart.CartDataSource
import woowacourse.shopping.data.mapper.toData
import woowacourse.shopping.data.mapper.toDomain
import woowacourse.shopping.domain.model.Cart
import woowacourse.shopping.domain.model.CartEntity
import woowacourse.shopping.domain.model.CartProduct
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.model.page.Page
import woowacourse.shopping.domain.repository.CartRepository

class CartRepositoryImpl(private val localCartDataSource: CartDataSource.Local) :
    CartRepository {
    override fun getAllCartEntities(): List<CartEntity> =
        localCartDataSource.getAllCartEntity().map { it.toDomain() }

    override fun getCartEntity(productId: Int): CartEntity =
        localCartDataSource.getCartEntity(productId).toDomain()

    override fun increaseCartCount(product: Product, count: Int) {
        localCartDataSource.increaseCartCount(product.toData(), count)
    }

    override fun update(cartProducts: List<CartProduct>) {
        localCartDataSource.update(cartProducts.toData())
    }

    override fun getCheckedProductCount(): Int =
        localCartDataSource.getCheckedProductCount()

    override fun removeCheckedProducts() {
        localCartDataSource.removeCheckedProducts()
    }

    override fun decreaseCartCount(product: Product, count: Int) {
        localCartDataSource.decreaseCartCount(product.toData(), count)
    }

    override fun deleteByProductId(productId: Int) {
        localCartDataSource.deleteByProductId(productId)
    }

    override fun getProductInCartSize(): Int =
        localCartDataSource.getProductInCartSize()
}
