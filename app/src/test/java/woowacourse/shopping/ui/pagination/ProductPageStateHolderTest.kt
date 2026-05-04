package woowacourse.shopping.ui.pagination

import io.kotest.matchers.shouldBe
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import woowacourse.shopping.model.Price
import woowacourse.shopping.model.Product
import woowacourse.shopping.model.ProductTitle

class ProductPageStateHolderTest {
    @ParameterizedTest
    @CsvSource("20, 20", "15, 15", "25, 20")
    fun `로드된 상품 개수에 따라 최대 20개까지 반환한다`(
        itemSize: Int,
        expectedItemSize: Int,
    ) {
        val productDataLoadStateHolder =
            ProductPageStateHolder(
                products =
                    List(itemSize) {
                        Product(
                            id = 1L,
                            price = Price(10_000),
                            title = ProductTitle("호날두"),
                            imageUrl = "",
                        )
                    },
            )
        productDataLoadStateHolder.getItems().size shouldBe expectedItemSize
    }

    @ParameterizedTest
    @CsvSource("20, 1, 20", "25, 2, 25", "50, 2, 40")
    fun `다음 페이지로 이동하면 기존 상품에 추가된 상품을 반환한다`(
        itemSize: Int,
        pageMoveCount: Int,
        expectedItemSize: Int,
    ) {
        val productDataLoadStateHolder =
            ProductPageStateHolder(
                products =
                    List(itemSize) {
                        Product(
                            id = 1L,
                            price = Price(10_000),
                            title = ProductTitle("호날두"),
                            imageUrl = "",
                        )
                    },
            )

        repeat(pageMoveCount - 1) {
            productDataLoadStateHolder.nextPage()
        }

        productDataLoadStateHolder.getItems().size shouldBe expectedItemSize
    }
}
