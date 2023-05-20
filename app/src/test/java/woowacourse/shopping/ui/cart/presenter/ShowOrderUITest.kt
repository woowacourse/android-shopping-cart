package woowacourse.shopping.ui.cart.presenter

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.shopping.domain.CartItem
import woowacourse.shopping.domain.Product
import woowacourse.shopping.ui.cart.OrderView
import java.time.LocalDateTime

internal class ShowOrderUITest {

    private lateinit var view: FakeOrderView
    private val dummyProducts = List(20) { Product(it.toLong(), "url", "name", 10_000) }
    private val dummyCartItems =
        dummyProducts.map { CartItem(it, LocalDateTime.MAX, 1).apply { id = it.id } }

    @BeforeEach
    fun setUp() {
        view = FakeOrderView()
    }

    @Test
    fun `주문 UI 보여주기가 실행되면 선택된 장바구니 아이템의 주문 금액의 합을 뷰에 설정한다`() {
        val sut = ShowOrderUI(view)
        val selectedCartItems = dummyCartItems.slice(0 until 5)

        sut.invoke(selectedCartItems.toSet())

        assertThat(view.price).isEqualTo(50_000)
    }

    @Test
    fun `주문 UI 보여주기가 실행되면 선택된 장바구니 아이템의 개수를 뷰에 설정한다`() {
        val sut = ShowOrderUI(view)
        val selectedCartItems = dummyCartItems.slice(0 until 5)

        sut.invoke(selectedCartItems.toSet())

        assertThat(view.count).isEqualTo(selectedCartItems.size)
    }

    class FakeOrderView : OrderView {
        var price: Int? = null
        var count: Int? = null

        override fun setOrderPrice(price: Int) {
            this.price = price
        }

        override fun setOrderCount(count: Int) {
            this.count = count
        }

    }
}