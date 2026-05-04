package woowacourse.shopping.model

import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.junit.jupiter.api.Test

class ProductTest {
    @Test
    fun `상품의 제목, 가격, 이미지, id가 같으면 같은 상품이다`() {
        val product =
            Product(
                id = 1,
                title = ProductTitle("동원 스위트콘"),
                price = Price(20_560),
                imageUrl = "https://www.coupang.com/vp/products/8402186124",
            )
        product shouldBe product
    }

    @Test
    fun `상품의 id가 다르면 제목, 가격, 이미지가 같아도 다른 상품이다`() {
        val product =
            Product(
                id = 1,
                title = ProductTitle("동원 스위트콘"),
                price = Price(20_560),
                imageUrl = "https://www.coupang.com/vp/products/8402186124",
            )
        val differentPriceProduct =
            Product(
                id = 2,
                title = ProductTitle("동원 스위트콘"),
                price = Price(20_560),
                imageUrl = "https://www.coupang.com/vp/products/8402186124",
            )
        product shouldNotBe differentPriceProduct
        product.hashCode() shouldNotBe differentPriceProduct.hashCode()
    }
}
