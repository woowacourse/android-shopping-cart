package woowacourse.shopping.data

import okhttp3.mockwebserver.MockWebServer
import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import woowacourse.shopping.data.datasource.impl.MockServerProductDataSource
import woowacourse.shopping.data.entity.ProductEntity.Companion.STUB_LIST
import woowacourse.shopping.data.service.ProductDispatcher
import woowacourse.shopping.data.service.ProductMockServer

class ProductMockServerTest {
    private val productMockServer: MockWebServer = ProductMockServer.instance(ProductDispatcher())
    private val mockServerProductDataSource: MockServerProductDataSource = MockServerProductDataSource(productMockServer)

    @After
    fun tearDown() {
        ProductMockServer.shutDown()
    }

    @Test
    @DisplayName("product 전체를 꺼낼 수 있다")
    fun can_get_all_product_list() {
        // given
        val actual = mockServerProductDataSource.products()

        // then
        assertThat(actual).isEqualTo(STUB_LIST)
    }

    @Test
    @DisplayName("10 ~ 14의 위치에 해당하는 product list를 가져올 수 있다")
    fun can_get_product_list_of_position_that_10_to_14() {
        // when
        val startPosition = 10
        val lastPosition = 14

        // given
        val actual = mockServerProductDataSource.productsByOffset(startPosition, 5)

        // then
        assertThat(actual).isEqualTo(STUB_LIST.subList(startPosition, lastPosition + 1))
    }

    @Test
    @DisplayName("productId가 1일 때, productId 1에 해당하는 상품을 가져올 수 있다.")
    fun can_get_product_That_is_id_is_1() {
        // when
        val productId = 1L

        // given
        val actual = mockServerProductDataSource.productById(productId)

        // then
        assertThat(actual).isEqualTo(STUB_LIST.first { it.id == productId })
    }
}
