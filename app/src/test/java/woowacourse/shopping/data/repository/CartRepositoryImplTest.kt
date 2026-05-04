package woowacourse.shopping.data.repository

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import woowacourse.shopping.domain.model.product.Price
import woowacourse.shopping.domain.model.product.Product
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class CartRepositoryImplTest {
    @Test
    fun `page가 음수이면 빈 Cart를 반환한다`() {
        val cart = CartRepositoryImpl.getPagingItems(page = -1, pageSize = 10)
        assertThat(cart.cartItems).isEmpty()
    }

    @Test
    fun `pageSize가 0 이하이면 빈 Cart를 반환한다`() {
        val cart = CartRepositoryImpl.getPagingItems(page = 0, pageSize = 0)
        assertThat(cart.cartItems).isEmpty()
    }

    @Test
    fun `장바구니가 비어 있으면 빈 Cart를 반환한다`() {
        val cart = CartRepositoryImpl.getPagingItems(page = 0, pageSize = 10)
        assertThat(cart.cartItems).isEmpty()
    }

    @Test
    fun `범위를 벗어난 페이지이면 빈 Cart를 반환한다`() {
        val cart = CartRepositoryImpl.getPagingItems(page = 3, pageSize = 10)
        assertThat(cart.cartItems).isEmpty()
    }

    @OptIn(ExperimentalUuidApi::class)
    @Test
    fun `상품을 추가하면 장바구니에 저장된다`() {
        CartRepositoryImpl.addProduct(createProduct())
        val cart = CartRepositoryImpl.getPagingItems(page = 0, pageSize = 10)
        assertThat(cart.cartItems).hasSize(1)
    }

    @OptIn(ExperimentalUuidApi::class)
    @Test
    fun `상품을 삭제하면 장바구니에서 삭제된다`() {
        val product = createProduct()

        CartRepositoryImpl.addProduct(product)
        CartRepositoryImpl.deleteProduct(product.productId)

        val cart = CartRepositoryImpl.getPagingItems(page = 0, pageSize = 10)
        assertThat(cart.cartItems).isEmpty()
    }

    @OptIn(ExperimentalUuidApi::class)
    private fun createProduct(): Product =
        Product(
            productId = Uuid.random(),
            imageUrl = "",
            productName = "동원 스위트콘",
            price = Price(99800),
        )
}
