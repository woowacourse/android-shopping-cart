package woowacourse.shopping.repository

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.shopping.TwentyItemsPagingStrategy

class DummyShoppingProductsRepositoryTest {
    private lateinit var dummyShoppingProductsRepository: ShoppingProductsRepository

    @BeforeEach
    fun setUp() {
        dummyShoppingProductsRepository = DummyShoppingProductsRepository(TwentyItemsPagingStrategy())
    }

    @Test
    fun `데이터를 첫 페이지에 20개 로드`() {
        // giveo
        val loadedCartItems = dummyShoppingProductsRepository.loadPagedItems(1)

        // then
        assertThat(loadedCartItems.size).isEqualTo(20)
    }

    @Test
    fun `데이터를 두 번째 페이지에서 20개 로드`() {
        // given
        val loadedCartItems = dummyShoppingProductsRepository.loadPagedItems(2)

        // when
        assertThat(loadedCartItems.size).isEqualTo(20)
    }

    // findById test
    @Test
    fun `id로 상품을 찾는다`() {
        // given
        val findId = 1

        // when
        val product = dummyShoppingProductsRepository.findById(findId)

        // then
        assertThat(product.id).isEqualTo(findId)
    }
}
