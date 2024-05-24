package woowacourse.shopping.data.datasource

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import woowacourse.shopping.data.datasourceimpl.DefaultProducts
import woowacourse.shopping.data.model.Product

class DefaultProductsTest {
    @Test
    fun `페이지당 상품의 수가 허용 범위 이내이다`() {
        // when
        val actualData = DefaultProducts.getProducts(0, 20).size

        // then
        assertTrue { actualData in 1..20 }
    }

    @Test
    fun `올바른 페이지 상품들을 가져온다`() {
        // when
        val actualData = DefaultProducts.getProducts(0, 5)

        // then
        assertThat(actualData).isEqualTo(
            listOf(
                Product(
                    1,
                    "사과",
                    "https://img.hankyung.com/photo/202403/AA.36104679.1.jpg",
                    1000,
                ),
                Product(
                    2,
                    "바나나",
                    "https://cdn.hankyung.com/photo/201803/AA.16218828.1.jpg",
                    2000,
                ),
                Product(
                    3,
                    "딸기",
                    "https://img.hankyung.com/photo/202403/AA.36104679.1.jpg",
                    3000,
                ),
                Product(
                    4,
                    "수박",
                    "https://cdn.hankyung.com/photo/201803/AA.16218828.1.jpg",
                    4000,
                ),
                Product(
                    5,
                    "포도",
                    "https://img.hankyung.com/photo/202403/AA.36104679.1.jpg",
                    5000,
                ),
            ),
        )
    }

    @Test
    fun `상품 아이디에 부합하는 상품을 가져올 수 있다`() {
        // when
        val actualData = DefaultProducts.getProductById(1)

        // then
        assertThat(actualData).isEqualTo(
            Product(
                1,
                "사과",
                "https://img.hankyung.com/photo/202403/AA.36104679.1.jpg",
                1000,
            ),
        )
    }

    @Test
    fun `유효하지 않은 상품 아이디로 상품 정보를 가져오면 예외를 발생시킨다`() {
        assertThrows<NoSuchElementException> { DefaultProducts.getProductById(0) }
    }
}
