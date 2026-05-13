package woowacourse.shopping.ui.pagination

import io.kotest.matchers.shouldBe
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import woowacourse.shopping.model.Price
import woowacourse.shopping.model.Product
import woowacourse.shopping.model.ProductTitle
import woowacourse.shopping.model.ShoppingItem

class ProductPageStateHolderTest {
    @ParameterizedTest
    @CsvSource("20, 20", "15, 15", "25, 20")
    fun `로드된 상품 개수에 따라 최대 20개까지 반환한다`(
        itemSize: Int,
        expectedItemSize: Int,
    ) {
        val productDataLoadStateHolder =
            ProductPageStateHolder(
                shoppingItems =
                    List(itemSize) {
                        ShoppingItem(
                            product =
                                Product(
                                    id = it.toLong(),
                                    price = Price(10_000),
                                    title = ProductTitle("호날두"),
                                    imageUrl = "",
                                ),
                            quantity = 0,
                        )
                    },
            )
        productDataLoadStateHolder.getItems().size shouldBe expectedItemSize
    }
}
