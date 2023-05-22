package woowacourse.shopping.domain.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import woowacourse.shopping.domain.fixture.ALL_CHECKED_CARTS
import woowacourse.shopping.domain.fixture.ALL_UNCHECKED_CARTS
import woowacourse.shopping.domain.fixture.getCart
import woowacourse.shopping.domain.model.page.Pagination
import woowacourse.shopping.domain.util.safeSubList

internal class PaginationTest {
    @Test
    internal fun `첫 번째 페이지를 반환한다`() {
        // given
        val page = Pagination(3)

        // when
        val actual = page.getStartPage()

        // then
        assertThat(actual).usingRecursiveComparison().isEqualTo(Pagination(1))
    }

    @ParameterizedTest
    @ValueSource(ints = [0, -1, -2, -3, -4, -5])
    internal fun `페이지 번호가 1보다 작으면 예외가 발생한다`(number: Int) {
        assertThrows<IllegalArgumentException> { Pagination(number) }
    }

    @ParameterizedTest
    @ValueSource(ints = [2, 3, 4, 5, 6, 100])
    internal fun `페이지 번호가 1보다 크면 이전 페이지가 존재한다`(number: Int) {
        // given
        val page = Pagination(number)

        // when
        val actual = page.hasPrevious()

        // then
        assertThat(actual).isTrue
    }

    @Test
    internal fun `페이지 번호가 1이면 이전 페이지가 존재하지 않는다`() {
        // given
        val page = Pagination(1)

        // when
        val actual = page.hasPrevious()

        // then
        assertThat(actual).isFalse
    }

    @Test
    internal fun `총 카트 아이템 개수가 페이지가 수용 가능한 크기보다 크면, 다음 페이지가 존재한다`() {
        // given
        val page = Pagination(1, 5)
        val cart = ALL_UNCHECKED_CARTS

        // when
        val actual = page.hasNext(cart)

        // then
        assertThat(actual).isTrue
    }

    @Test
    internal fun `총 카트 아이템 개수가 페이지가 수용 가능한 크기보다 적으면, 다음 페이지가 존재하지 않는다`() {
        // given
        val page = Pagination(1, 5)
        val cart = getCart(3)

        // when
        val actual = page.hasNext(cart)

        // then
        assertThat(actual).isFalse()
    }

    @ParameterizedTest
    @ValueSource(ints = [2, 3, 4, 5, 6, 100])
    internal fun `페이지 번호를 증가시켰을 때, 1만큼 증가한다`(currentNumber: Int) {
        // given
        val page = Pagination(currentNumber)
        val expected = Pagination(currentNumber + 1)

        // when
        val actual = page.next()

        // then
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected)
    }

    @Test
    internal fun `페이지에 해당하는 상품 목록을 가져온다`() {
        // given
        val page = Pagination(2, 5)

        // when
        val actual = page.takeItems(ALL_UNCHECKED_CARTS)

        // then
        assertThat(actual).isEqualTo(ALL_UNCHECKED_CARTS.items.safeSubList(5, 10))
    }

    @Test
    internal fun `현재 페이지에 체크된 모든 상품 목록 개수를 반환한다`() {
        // given
        val page = Pagination(2, 30)
        val carts = ALL_CHECKED_CARTS

        // when
        val actual = page.getCheckedProductSize(carts)

        // then
        assertThat(actual).isEqualTo(30)
    }
}
