package woowacourse.shopping.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.shopping.data.local.CartDao
import woowacourse.shopping.data.local.CartEntity
import woowacourse.shopping.domain.model.Price
import woowacourse.shopping.domain.model.Quantity
import woowacourse.shopping.domain.model.cart.CartItem
import woowacourse.shopping.domain.model.product.Product
import woowacourse.shopping.domain.model.product.ProductTitle

class CartRepositoryImplTest {
    private lateinit var cartDao: FakeCartDao
    private lateinit var repository: CartRepositoryImpl

    private val product =
        Product(
            id = "product-1",
            imageUrl = "url",
            productTitle = ProductTitle("상품"),
            price = Price(1000),
        )

    @BeforeEach
    fun setUp() {
        cartDao = FakeCartDao()
        repository = CartRepositoryImpl(cartDao)
    }

    @Test
    fun `장바구니 항목을 추가하면 상품과 수량이 저장된다`() =
        runTest {
            repository.updateCart(CartItem(product, Quantity(1)))

            val savedItem = repository.getCartItem(product.id).first()

            assertThat(savedItem).isNotNull
            assertThat(savedItem?.quantity?.value).isEqualTo(1)
            assertThat(cartDao.items.value.single().productId).isEqualTo(product.id)
            assertThat(cartDao.items.value.single().quantity).isEqualTo(1)
        }

    @Test
    fun `장바구니 항목을 삭제하면 목록에서 제거된다`() =
        runTest {
            repository.updateCart(CartItem(product, Quantity(1)))

            repository.deleteCartItem(product.id)

            assertThat(cartDao.items.value).isEmpty()
        }

    @Test
    fun `수량 증가 시 기존 수량에서 1 증가된 값으로 저장한다`() =
        runTest {
            repository.updateCart(CartItem(product, Quantity(1)))

            repository.increaseCartItemQuantity(product.id)

            assertThat(cartDao.items.value.single().quantity).isEqualTo(2)
        }

    @Test
    fun `수량 감소 시 기존 수량이 1보다 크면 1 감소된 값으로 저장한다`() =
        runTest {
            repository.updateCart(CartItem(product, Quantity(2)))

            repository.decreaseCartItemQuantity(product.id)

            assertThat(cartDao.items.value.single().quantity).isEqualTo(1)
        }

    @Test
    fun `수량 감소 시 기존 수량이 1이면 수량을 유지한다`() =
        runTest {
            repository.updateCart(CartItem(product, Quantity(1)))

            repository.decreaseCartItemQuantity(product.id)

            assertThat(cartDao.items.value.single().quantity).isEqualTo(1)
        }

    @Test
    fun `페이지네이션 요청 시 지정한 페이지와 크기에 해당하는 장바구니 항목을 반환한다`() =
        runTest {
            repeat(7) { index ->
                val itemProduct =
                    product.copy(
                        id = "product-${index + 1}",
                        productTitle = ProductTitle("상품 ${index + 1}"),
                    )
                repository.updateCart(CartItem(itemProduct, Quantity(1)))
            }

            val result = repository.getPagingCartItems(page = 1, pageSize = 5)

            assertThat(result.map { it.product.id }).containsExactly("product-6", "product-7")
        }

    private class FakeCartDao : CartDao {
        val items = MutableStateFlow<List<CartEntity>>(emptyList())

        override fun getAllCartItems(): Flow<List<CartEntity>> = items

        override fun getCartItem(productId: String): Flow<CartEntity?> =
            items.map { cartItems -> cartItems.firstOrNull { it.productId == productId } }

        override suspend fun upsert(cartEntity: CartEntity) {
            val currentItems = items.value.filterNot { it.productId == cartEntity.productId }
            items.value = currentItems + cartEntity
        }

        override suspend fun deleteCartItem(productId: String) {
            items.value = items.value.filterNot { it.productId == productId }
        }

        override suspend fun getCartItemCount(): Int = items.value.size

        override suspend fun getPagingCartItems(
            limit: Int,
            offset: Int,
        ): List<CartEntity> = items.value.drop(offset).take(limit)
    }
}
