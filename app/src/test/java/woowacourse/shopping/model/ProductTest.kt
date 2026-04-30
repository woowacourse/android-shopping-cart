package woowacourse.shopping.model

import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

class ProductTest {
    @ParameterizedTest
    @MethodSource("provideSameProducts")
    fun `상품의 제목, 가격, 이미지는 달라도 id가 같으면 같은 상품이다`(
        product: Product,
        sameIdProduct: Product,
    ) {
        product shouldBe sameIdProduct
        product.hashCode() shouldBe sameIdProduct.hashCode()
    }

    @Test
    fun `상품의 id가 다르면 제목, 가격, 이미지가 같아도 다른 상품이다`() {
        val product =
            Product(
                id = 1,
                title = "동원 스위트콘",
                price = Price(20_560),
                imageUrl = "https://www.coupang.com/vp/products/8402186124",
            )
        val differentPriceProduct =
            Product(
                id = 2,
                title = "동원 스위트콘",
                price = Price(20_560),
                imageUrl = "https://www.coupang.com/vp/products/8402186124",
            )
        product shouldNotBe differentPriceProduct
        product.hashCode() shouldNotBe differentPriceProduct.hashCode()
    }

    companion object {
        @JvmStatic
        fun provideSameProducts() =
            listOf(
                Arguments.of(
                    Product(
                        id = 1,
                        title = "동원 스위트콘",
                        price = Price(20_560),
                        imageUrl = "https://www.coupang.com/vp/products/8402186124",
                    ),
                    Product(
                        id = 1,
                        title = "동원 스위트콘",
                        price = Price(20_560),
                        imageUrl = "https://www.coupang.com/vp/products/8402186124",
                    ),
                ),
                Arguments.of(
                    Product(
                        id = 1,
                        title = "동원 스위트콘 2.95kg",
                        price = Price(20_560),
                        imageUrl = "https://www.coupang.com/vp/products/8402186124",
                    ),
                    Product(
                        id = 1,
                        title = "동원 스위트콘 340g",
                        price = Price(20_560),
                        imageUrl = "https://www.coupang.com/vp/products/8402186124",
                    ),
                ),
                Arguments.of(
                    Product(
                        id = 1,
                        title = "동원 스위트콘 340g",
                        price = Price(39_920),
                        imageUrl = "https://www.coupang.com/vp/products/8402186124",
                    ),
                    Product(
                        id = 1,
                        title = "동원 스위트콘 340g",
                        price = Price(20_560),
                        imageUrl = "https://www.coupang.com/vp/products/8402186124",
                    ),
                ),
            )
    }
}
