package woowacourse.shopping.data

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.shopping.data.repository.LocalCartRepository
import woowacourse.shopping.data.source.local.cart.CartDao
import woowacourse.shopping.data.source.local.cart.CartEntity
import woowacourse.shopping.domain.model.AddItemResult
import woowacourse.shopping.domain.model.Money
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.model.ProductName
import woowacourse.shopping.domain.model.RemoveItemResult
import woowacourse.shopping.domain.repository.ProductRepository

class LocalCartRepositoryTest {
    private val cartDao: CartDao = mockk()
    private val productRepository: ProductRepository = mockk()
    private lateinit var repository: LocalCartRepository

    private val productId = 1L
    private val product = Product(productId, ProductName("상품"), Money(1400), "")

    @BeforeEach
    fun setUp() {
        repository = LocalCartRepository(cartDao, productRepository)
    }

    @Test
    fun `등록되지 않은 상품을 등록하면 NewAdded를 반환한다`() =
        runTest {
            coEvery { cartDao.findById(productId) } returns null
            coEvery { cartDao.addOrIncrement(productId) } returns Unit
            coEvery { cartDao.getAll() } returns listOf(CartEntity(productId, 1))
            coEvery { productRepository.getProductById(productId) } returns product

            val result = repository.addItem(productId)

            assertThat(result).isInstanceOf(AddItemResult.NewAdded::class.java)
        }

    @Test
    fun `이미 등록된 상품을 등록하면 Increment를 반환한다`() =
        runTest {
            val existingEntity = CartEntity(productId, 1)
            coEvery { cartDao.findById(productId) } returns existingEntity
            coEvery { cartDao.addOrIncrement(productId) } returns Unit
            coEvery { cartDao.getAll() } returns listOf(CartEntity(productId, 2))
            coEvery { productRepository.getProductById(productId) } returns product

            // When
            val result = repository.addItem(productId)

            assertThat(result).isInstanceOf(AddItemResult.Incremented::class.java)
        }

    @Test
    fun `수량 감소 시 장바구니에 등록된 상품의 수량을 1 감소시킨다`() =
        runTest {
            val existingEntity = CartEntity(productId, 2)
            coEvery { cartDao.findById(productId) } returns existingEntity
            coEvery { cartDao.deleteOrDecrement(productId) } returns Unit
            coEvery { cartDao.getAll() } returns listOf(CartEntity(productId, 1))
            coEvery { productRepository.getProductsByIds(listOf(productId)) } returns listOf(product)

            val result = repository.decrease(productId)

            assertThat(result).isInstanceOf(RemoveItemResult.Success::class.java)
            coVerify { cartDao.deleteOrDecrement(productId) }

            val cart = (result as RemoveItemResult.Success).cart
            assertThat(cart.items).hasSize(1)
            assertThat(cart.items.first().quantity).isEqualTo(1)
        }

    @Test
    fun `장바구니에 등록된 상품의 수량이 1일 때 수량을 감소시키면 장바구니에서 삭제된다`() =
        runTest {
            val existingEntity = CartEntity(productId, 1)
            coEvery { cartDao.findById(productId) } returns existingEntity
            coEvery { cartDao.deleteOrDecrement(productId) } returns Unit
            coEvery { cartDao.getAll() } returns emptyList()
            coEvery { productRepository.getProductById(productId) } returns product

            val result = repository.decrease(productId)

            assertThat(result).isInstanceOf(RemoveItemResult.Success::class.java)
            coVerify { cartDao.deleteOrDecrement(productId) }

            val cart = (result as RemoveItemResult.Success).cart
            assertThat(cart.items).hasSize(0)
        }
}
