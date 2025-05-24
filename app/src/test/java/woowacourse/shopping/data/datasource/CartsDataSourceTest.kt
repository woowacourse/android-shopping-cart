package woowacourse.shopping.data.datasource

import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.shopping.data.db.dao.CartDao
import woowacourse.shopping.data.db.entity.CartEntity

class CartsDataSourceTest {
    private lateinit var dao: CartDao
    private lateinit var dataSource: CartDataSource

    @BeforeEach
    fun setUp() {
        dao = mockk(relaxed = true)
        dataSource = CartDataSource(dao)
    }

    @Test
    fun `주어진_productId로_장바구니_항목을_조회하면_해당_항목을_반환한다`() {
        val productId = 1L
        val entity = CartEntity(productId, 3)

        every { dao.cartByProductId(productId) } returns entity

        val result = dataSource.getCartByProductId(productId)

        assertEquals(entity, result)
        verify { dao.cartByProductId(productId) }
    }

    @Test
    fun `기존_항목이_있을_때_accumulate을_호출하면_수량을_누적하여_업데이트한다`() {
        val productId = 1L
        val existing = CartEntity(productId, 2)
        val incoming = CartEntity(productId, 3)
        val updated = existing.copy(quantity = 5)

        every { dao.cartByProductId(productId) } returns existing
        every { dao.update(updated) } just Runs

        dataSource.modify(incoming)

        verify { dao.update(updated) }
    }

    @Test
    fun `기존_항목이_없을_때_accumulate을_호출하면_새로운_항목을_추가한다`() {
        val productId = 2L
        val incoming = CartEntity(productId, 1)

        every { dao.cartByProductId(productId) } returns null
        every { dao.insert(incoming) } just Runs

        dataSource.modify(incoming)

        verify { dao.insert(incoming) }
    }

    @Test
    fun `delete를_호출하면_해당_productId의_항목을_삭제한다`() {
        val productId = 6L

        every { dao.deleteByProductId(productId) } just Runs

        dataSource.deleteCartByProductId(productId)

        verify { dao.deleteByProductId(productId) }
    }

    @Test
    fun `singlePage를_호출하면_지정된_페이지의_장바구니_목록을_반환한다`() {
        val page = 2
        val size = 10
        val expected = listOf(CartEntity(1L, 2), CartEntity(2L, 1))

        every { dao.cartSinglePage(page, size) } returns expected

        val result = dataSource.singlePage(page, size)

        Assertions.assertEquals(expected, result)
        verify { dao.cartSinglePage(page, size) }
    }

    @Test
    fun `pageCount를_호출하면_전체_페이지_수를_반환한다`() {
        val size = 10
        every { dao.pageCount(size) } returns 3

        val result = dataSource.pageCount(size)

        Assertions.assertEquals(3, result)
        verify { dao.pageCount(size) }
    }
}
