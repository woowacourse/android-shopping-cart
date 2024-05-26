package woowacourse.shopping.repository

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.assertThrows
import woowacourse.shopping.NumberPagingStrategy
import woowacourse.shopping.domain.repository.DummyShoppingCartItemRepository
import woowacourse.shopping.domain.repository.ShoppingCartItemRepository
import woowacourse.shopping.productTestFixture

class DummyShoppingCartItemRepositoryTest {
    private lateinit var dummyShoppingCartItemRepository: ShoppingCartItemRepository

    @BeforeEach
    fun setUp() {
        dummyShoppingCartItemRepository = DummyShoppingCartItemRepository(NumberPagingStrategy(countPerLoad = 5))
    }

    @Test
    fun `데이터를 추가한다`() {
        // when
        val addedCartItemId = dummyShoppingCartItemRepository.addCartItem(productTestFixture(id = 1))

        // then
        assertThat(addedCartItemId).isEqualTo(1)
    }

    @Test
    fun `데이터를 첫 페이지에 5개의 데이터를 로드한다`() {
        // given
        for (i in 1..5) {
            dummyShoppingCartItemRepository.addCartItem(productTestFixture(id = i.toLong()))
        }

        // when
        val loadedCartItems = dummyShoppingCartItemRepository.loadPagedCartItems(1)

        // then
        assertAll(
            { assertThat(loadedCartItems.size).isEqualTo(5) },
            { assertThat(loadedCartItems[0].id).isEqualTo(1) },
            { assertThat(loadedCartItems[1].id).isEqualTo(2) },
            { assertThat(loadedCartItems[2].id).isEqualTo(3) },
            { assertThat(loadedCartItems[3].id).isEqualTo(4) },
            { assertThat(loadedCartItems[4].id).isEqualTo(5) },
        )
    }

    @Test
    fun `데이터를 두 번째 페이지에서 5개의 데이터를 로드한다`() {
        // given
        for (i in 1..10) {
            dummyShoppingCartItemRepository.addCartItem(productTestFixture(id = i.toLong()))
        }

        // when
        val loadedCartItems = dummyShoppingCartItemRepository.loadPagedCartItems(2)

        // then
        assertAll(
            { assertThat(loadedCartItems.size).isEqualTo(5) },
            { assertThat(loadedCartItems[0].id).isEqualTo(6) },
            { assertThat(loadedCartItems[1].id).isEqualTo(7) },
            { assertThat(loadedCartItems[2].id).isEqualTo(8) },
            { assertThat(loadedCartItems[3].id).isEqualTo(9) },
            { assertThat(loadedCartItems[4].id).isEqualTo(10) },
        )
    }

    @Test
    fun `성품 id 에 맞는 상품을 찾는다`() {
        // given
        for (i in 1..10) {
            dummyShoppingCartItemRepository.addCartItem(productTestFixture(id = i.toLong()))
        }

        // when
        val foundCartItem = dummyShoppingCartItemRepository.findById(5)

        // then
        assertThat(foundCartItem.id).isEqualTo(5)
    }

    @Test
    fun `상품 id 에 맞는 상품을 제거한다`() {
        // given
        for (i in 1..10) {
            dummyShoppingCartItemRepository.addCartItem(productTestFixture(id = i.toLong()))
        }

        // when
        dummyShoppingCartItemRepository.removeCartItem(5)

        // then
        assertThrows<NoSuchElementException> {
            dummyShoppingCartItemRepository.findById(5)
        }
    }

    @Test
    fun `상품이 6개일 떄 1 페이지는 마지막 페이지가 아니다`() {
        // given
        for (i in 1..6) {
            dummyShoppingCartItemRepository.addCartItem(productTestFixture(id = i.toLong()))
        }

        // when
        val isFinalPage = dummyShoppingCartItemRepository.isFinalPage(1)

        // then
        assertThat(isFinalPage).isFalse
    }

    @Test
    fun `상품이 5개 일 때 1페이지는 마지막 페이지이다`() {
        // given
        for (i in 1..5) {
            dummyShoppingCartItemRepository.addCartItem(productTestFixture(id = i.toLong()))
        }

        // when
        val isFinalPage = dummyShoppingCartItemRepository.isFinalPage(1)

        // then
        assertThat(isFinalPage).isTrue
    }

    @AfterEach
    fun tearDown() {
        dummyShoppingCartItemRepository.clearAllCartItems()
    }
}
