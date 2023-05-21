package woowacourse.shopping.data.repository

import woowacourse.shopping.data.datasource.cart.CartDataSource
import woowacourse.shopping.data.mapper.toData
import woowacourse.shopping.data.mapper.toDomain
import woowacourse.shopping.domain.model.Cart
import woowacourse.shopping.domain.model.CartProduct
import woowacourse.shopping.domain.model.page.Page
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.repository.CartRepository

class CartRepositoryImpl(private val localCartDataSource: CartDataSource.Local) :
    CartRepository {
    override fun getProductByPage(page: Page): Cart =
        localCartDataSource.getProductByPage(page.toData()).toDomain(page.sizePerPage)

    override fun getProductInCartByPage(page: Page): Cart =
        localCartDataSource.getProductInCartByPage(page.toData()).toDomain(page.sizePerPage)

    override fun getProductInRange(startPage: Page, endPage: Page): Cart {
        val start = startPage.toData()
        val end = endPage.toData()
        return localCartDataSource.getProductInRange(start, end).toDomain(startPage.sizePerPage)
    }

    override fun increaseCartCount(product: Product, count: Int) {
        localCartDataSource.increaseCartCount(product.toData(), count)
    }

    override fun update(cartProducts: List<CartProduct>) {
        localCartDataSource.update(cartProducts.toData())
    }

    override fun getTotalPrice(): Int =
        localCartDataSource.getTotalPrice()

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
