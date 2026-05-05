package woowacourse.shopping.data.repository

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ProductRepositoryImplTest {
    private val repository = ProductRepositoryImpl

    @Test
    fun `100개의 리스트에서 0번째 페이지와 20 페이지 사이즈를 호출하면 20개의 응답이 온다`() {
        val result = repository.getProducts(0, 20)
        assertEquals(20, result.items.size)
    }

    @Test
    fun `1번째 페이지 요청 시 다음 20개 상품이 반환된다`() {
        val result = repository.getProducts(1, 20)
        assertEquals(20, result.items.size)
    }

    @Test
    fun `마지막 페이지 이후 요청 시 예외가 발생한다`() {
        org.junit.jupiter.api.assertThrows<IllegalArgumentException> {
            repository.getProducts(10, 20)
        }
    }

    @Test
    fun `잘못된 페이지 번호 요청 시 예외가 발생한다`() {
        org.junit.jupiter.api.assertThrows<IllegalArgumentException> {
            repository.getProducts(-1, 20)
        }
    }

    @Test
    fun `잘못된 페이지 사이즈 요청 시 예외가 발생한다`() {
        org.junit.jupiter.api.assertThrows<IllegalArgumentException> {
            repository.getProducts(0, 0)
        }
    }

    @Test
    fun `남은 상품이 페이지 사이즈보다 적을 때 남은 상품만 반환된다`() {
        val result = repository.getProducts(3, 30)
        assertEquals(10, result.items.size)
    }
}
