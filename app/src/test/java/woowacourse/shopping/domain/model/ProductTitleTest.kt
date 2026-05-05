package woowacourse.shopping.domain.model

import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import woowacourse.shopping.domain.model.product.ProductTitle

class ProductTitleTest {
    @Test
    fun `상품명이 빈 값인 경우 생성이 불가능하다`() {
        assertThrows(IllegalArgumentException::class.java) { ProductTitle("") }
    }

    @Test
    fun `상품명이 공백인 경우 생성이 불가능하다`() {
        assertThrows(IllegalArgumentException::class.java) { ProductTitle("   ") }
    }
}
