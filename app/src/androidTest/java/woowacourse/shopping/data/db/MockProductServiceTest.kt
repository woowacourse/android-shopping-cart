package woowacourse.shopping.data.db

import androidx.test.ext.junit.runners.AndroidJUnit4
import io.kotest.matchers.shouldBe
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import woowacourse.shopping.data.db.product.MockProductService
import woowacourse.shopping.data.db.product.ProductDatabase
import woowacourse.shopping.domain.model.Product

@RunWith(AndroidJUnit4::class)
class MockProductServiceTest {
    private val dao = MockProductService()
    private val products = ProductDatabase.products

    @Test
    fun `상품_목록을_불러올_수_있다`() {
        val actual = dao.findAll().size
        val expected = products.size
        actual shouldBe expected
    }

    @Test
    fun `상품_데이터베이스의_특정_ID_값의_상품_데이터를_가져온다`() {
        val actual: Product? = dao.findProductById(0L)
        val expected: Product? = products.find { it.id == 0L }

        assertThat(actual).isNotNull()
        assertThat(expected).isNotNull()
        assertThat(actual?.id).isEqualTo(expected?.id)
    }
}
