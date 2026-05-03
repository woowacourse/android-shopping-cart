package woowacourse.shopping.data.repository

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class ProductRepositoryImplTest {
    @Test
    fun `page가 음수이면 빈 Products를 반환한다`() {
        val products = ProductRepositoryImpl.getPagingProducts(page = -1, pageSize = 10)
        assertThat(products.productItems).isEmpty()
    }

    @Test
    fun `pageSize가 0 이하이면 빈 Products를 반환한다`() {
        val products = ProductRepositoryImpl.getPagingProducts(page = 0, pageSize = 0)
        assertThat(products.productItems).isEmpty()
    }

    @Test
    fun `범위를 벗어난 페이지이면 빈 Products를 반환한다`() {
        val products = ProductRepositoryImpl.getPagingProducts(page = 3, pageSize = 10)
        assertThat(products.productItems).isEmpty()
    }

    @Test
    fun `page가 0이고 pageSize가 10이면 10개의 Product를 반환한다`() {
        val products = ProductRepositoryImpl.getPagingProducts(page = 0, pageSize = 10)
        assertThat(products.productItems).hasSize(10)
    }

    @Test
    fun `다음 페이지가 존재하면 true를 반환한다`() {
        val hasNextPage = ProductRepositoryImpl.hasNextPage(currentPage = 0, pageSize = 10)
        assertThat(hasNextPage).isTrue()
    }

    @Test
    fun `다음 페이지가 존재하지 않으면 false를 반환한다`() {
        val hasNextPage = ProductRepositoryImpl.hasNextPage(currentPage = 1, pageSize = 30)
        assertThat(hasNextPage).isFalse()
    }
}
