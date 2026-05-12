package woowacourse.shopping.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import woowacourse.shopping.data.localdb.dao.CartItemDao
import woowacourse.shopping.data.localdb.entity.CartItemEntity
import woowacourse.shopping.data.repository.CartRepository

class CartRepositoryTest {
    private val productId = "1"

    @Test
    fun `상품 id와 수량을 장바구니에 저장한다`() =
        runTest {
            val dao = TestCartItemDao()
            val repository = CartRepository(dao)

            repository.setQuantity(id = productId, quantity = 3)

            val savedItem = dao.findById(productId)
            assertThat(savedItem?.id).isEqualTo(productId)
            assertThat(savedItem?.quantity).isEqualTo(3)
        }

    @Test
    fun `장바구니 항목 목록을 반환한다`() =
        runTest {
            val dao = TestCartItemDao()
            val repository = CartRepository(dao)
            dao.insert(CartItemEntity(id = productId, quantity = 2, timestamp = 100L))

            val items = repository.observeCartItems().first()

            assertThat(items).containsExactly(CartItemEntity(id = productId, quantity = 2, timestamp = 100L))
        }

    @Test
    fun `장바구니에 담긴 상품 수량을 변경한다`() =
        runTest {
            val dao = TestCartItemDao()
            val repository = CartRepository(dao)
            repository.setQuantity(id = productId, quantity = 1)

            repository.updateQuantity(productId, quantity = 2)

            assertThat(dao.findById(productId)?.quantity).isEqualTo(2)
        }

    @Test
    fun `장바구니 상품 수량을 0으로 변경하면 삭제한다`() =
        runTest {
            val dao = TestCartItemDao()
            val repository = CartRepository(dao)
            repository.setQuantity(id = productId, quantity = 1)

            repository.updateQuantity(productId, quantity = 0)

            assertThat(dao.findById(productId)).isNull()
        }

    @Test
    fun `장바구니 상품을 삭제한다`() =
        runTest {
            val dao = TestCartItemDao()
            val repository = CartRepository(dao)
            repository.setQuantity(id = productId, quantity = 1)

            repository.deleteItem(productId)

            assertThat(dao.findById(productId)).isNull()
        }

    @Test
    fun `장바구니에 상품이 없으면 null을 반환한다`() =
        runTest {
            val repository = CartRepository(TestCartItemDao())

            val quantity = repository.getCartItemQuantity(productId)

            assertThat(quantity).isNull()
        }

    @Test
    fun `장바구니에 담긴 상품 수량을 반환한다`() =
        runTest {
            val repository = CartRepository(TestCartItemDao())
            repository.setQuantity(id = productId, quantity = 4)

            val quantity = repository.getCartItemQuantity(productId)

            assertThat(quantity).isEqualTo(4)
        }

    @Test
    fun `장바구니 상품 개수를 반환한다`() =
        runTest {
            val repository = CartRepository(TestCartItemDao())
            repository.setQuantity(id = productId, quantity = 3)

            val cartSize = repository.getCartSize()

            assertThat(cartSize).isEqualTo(1)
        }

    private class TestCartItemDao : CartItemDao {
        private val items = MutableStateFlow<List<CartItemEntity>>(emptyList())

        override fun getAll(): Flow<List<CartItemEntity>> = items

        override suspend fun insert(item: CartItemEntity) {
            items.value = items.value.filterNot { it.id == item.id } + item
        }

        override suspend fun findById(id: String): CartItemEntity? = items.value.firstOrNull { it.id == id }

        override suspend fun deleteById(id: String) {
            items.value = items.value.filterNot { it.id == id }
        }

        override suspend fun getTotalCount(): Int = items.value.size
    }
}
