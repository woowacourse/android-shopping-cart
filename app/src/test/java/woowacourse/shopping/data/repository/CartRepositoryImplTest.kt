package woowacourse.shopping.data.repository

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertNotNull
import woowacourse.shopping.data.datasource.CartDataSource
import woowacourse.shopping.data.db.entity.CartEntity
import woowacourse.shopping.data.fake.FakeCartRepositoryImpl
import woowacourse.shopping.domain.Quantity
import woowacourse.shopping.domain.repository.CartRepository

class CartRepositoryImplTest {
    private lateinit var dataSource: CartDataSource
    private lateinit var repository: CartRepository

    @BeforeEach
    fun setUp() {
        dataSource = mockk()
        repository = FakeCartRepositoryImpl(dataSource)
    }

    @Test
    fun `주어진_productId로_장바구니_항목을_조회하면_해당_항목을_반환한다`() {
        // Given
        val productId = 1L
        val cartEntity = CartEntity(productId, 5)
        every { dataSource.getCartByProductId(productId) } returns cartEntity

        // When
        repository.getCart(productId) { cart ->
            // Then
            assertNotNull(cart)
            assertEquals(productId, cart.productId)
            assertEquals(5, cart.quantity.value)

            verify(exactly = 1) { dataSource.getCartByProductId(productId) }
        }
    }

    @Test
    fun `상품_id_리스트를_전달하면_해당하는_장바구니_항목들을_반환한다`() {
        // Given
        val productIds = listOf(1L, 2L)
        val entities = listOf(CartEntity(1L, 3), CartEntity(2L, 7))
        every { dataSource.getCartsByProductIds(productIds) } returns entities

        // When
        repository.getCarts(productIds) { carts ->
            // Then
            assertEquals(2, carts.size)
            assertEquals(3, carts[0]?.quantity?.value)
            assertEquals(7, carts[1]?.quantity?.value)

            verify(exactly = 1) { dataSource.getCartsByProductIds(productIds) }
        }
    }

    @Test
    fun `upsert_호출시_datasource의_upsert가_호출된다`() {
        // Given
        val productId = 1L
        val quantity = Quantity(10)
        every { dataSource.upsert(any()) } returns Unit

        // When
        repository.upsert(productId, quantity)

        // Then
        verify(exactly = 1) { dataSource.upsert(CartEntity(productId, quantity.value)) }
    }

    @Test
    fun `modify_호출시_datasource의_modify가_호출된다`() {
        // Given
        val productId = 1L
        val quantity = Quantity(15)
        every { dataSource.modify(any()) } returns Unit

        // When
        repository.modify(productId, quantity)

        // Then
        verify(exactly = 1) { dataSource.modify(CartEntity(productId, quantity.value)) }
    }

    @Test
    fun `delete_호출시_datasource의_delete가_호출된다`() {
        // Given
        val productId = 1L
        every { dataSource.deleteCartByProductId(productId) } returns Unit

        // When
        repository.delete(productId) {
            // Then
            verify(exactly = 1) { dataSource.deleteCartByProductId(productId) }
        }
    }

    @Test
    fun `singlePage_호출시_정확한_CartSinglePage_데이터를_반환한다`() {
        // Given
        val page = 0
        val pageSize = 2
        val entities = listOf(CartEntity(1L, 3), CartEntity(2L, 5))
        every { dataSource.singlePage(page, pageSize) } returns entities
        every { dataSource.pageCount(pageSize) } returns 2

        // When
        repository.singlePage(page, pageSize) { pageResult ->
            // Then
            assertNotNull(pageResult)
            assertEquals(2, pageResult.carts.size)
            assertEquals(3, pageResult.carts.first().quantity.value)
            assertTrue(pageResult.hasNextPage)

            verify(exactly = 1) { dataSource.singlePage(page, pageSize) }
            verify(exactly = 1) { dataSource.pageCount(pageSize) }
        }
    }
}
