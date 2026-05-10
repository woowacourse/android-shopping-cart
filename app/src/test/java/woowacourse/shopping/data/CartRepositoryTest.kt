package woowacourse.shopping.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import woowacourse.shopping.data.localdb.dao.CartItemDao
import woowacourse.shopping.data.localdb.entity.CartItemEntity
import woowacourse.shopping.data.repository.CartRepository
import woowacourse.shopping.model.Money
import woowacourse.shopping.model.Product
import woowacourse.shopping.model.ProductName

class CartRepositoryTest {
    private val product =
        Product(
            id = "1",
            name = ProductName("상품"),
            price = Money(2000),
            imageUrl = "image-url",
        )

    private val dao = TestCartItemDao()

    @Test
    fun `상품과 수량을 장바구니에 저장한다`() =
        runTest {
            val repository = CartRepository(dao)

            repository.addItem(product = product, quantity = 3)

            val savedItem = dao.findById(product.id)
            assertThat(savedItem?.id).isEqualTo(product.id)
            assertThat(savedItem?.name).isEqualTo(product.getName())
            assertThat(savedItem?.price).isEqualTo(product.getPrice())
            assertThat(savedItem?.imageUrl).isEqualTo(product.imageUrl)
            assertThat(savedItem?.quantity).isEqualTo(3)
        }

    @Test
    fun `장바구니에 담긴 상품 수량을 증가시킨다`() =
        runTest {
            val repository = CartRepository(dao)
            repository.addItem(product = product, quantity = 1)

            repository.increaseQuantity(product.id)

            assertThat(dao.findById(product.id)?.quantity).isEqualTo(2)
        }

    @Test
    fun `장바구니에 담긴 상품 수량을 감소시킨다`() =
        runTest {
            val repository = CartRepository(dao)
            repository.addItem(product = product, quantity = 2)

            repository.decreaseQuantity(product.id)

            assertThat(dao.findById(product.id)?.quantity).isEqualTo(1)
        }

    @Test
    fun `장바구니 상품 수량이 1이면 감소할 때 삭제한다`() =
        runTest {
            val repository = CartRepository(dao)
            repository.addItem(product = product, quantity = 1)

            repository.decreaseQuantity(product.id)

            assertThat(dao.findById(product.id)).isNull()
        }

    @Test
    fun `장바구니에 상품이 없으면 기본 수량 1을 반환한다`() =
        runTest {
            val repository = CartRepository(TestCartItemDao())

            val quantity = repository.getCartItemQuantity(product.id)

            assertThat(quantity).isEqualTo(1)
        }

    @Test
    fun `장바구니 총 가격을 반환한다`() =
        runTest {
            val repository = CartRepository(TestCartItemDao())
            repository.addItem(product = product, quantity = 3)

            val totalPrice = repository.getCartTotalPrice()

            assertThat(totalPrice).isEqualTo(6000)
        }

    private class TestCartItemDao : CartItemDao {
        private val items = MutableStateFlow<List<CartItemEntity>>(emptyList())

        override fun getAll(): Flow<List<CartItemEntity>> = items

        override suspend fun insert(item: CartItemEntity) {
            items.value = items.value.filterNot { it.id == item.id } + item
        }

        override suspend fun findById(id: String): CartItemEntity? =
            items.value.firstOrNull { it.id == id }

        override suspend fun deleteById(id: String) {
            items.value = items.value.filterNot { it.id == id }
        }

        override suspend fun getTotalCount(): Int = items.value.size

        override suspend fun getTotalPrice(): Int = items.value.sumOf { it.price * it.quantity }
    }
}
