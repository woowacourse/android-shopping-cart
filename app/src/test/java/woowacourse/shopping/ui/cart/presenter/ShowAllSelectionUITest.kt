package woowacourse.shopping.ui.cart.presenter

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.shopping.domain.CartItem
import woowacourse.shopping.domain.Product
import woowacourse.shopping.repository.CartItemRepository
import woowacourse.shopping.ui.cart.AllSelectableItemView
import java.time.LocalDateTime

internal class ShowAllSelectionUITest {

    private lateinit var view: FakeAllSelectableItemView
    private lateinit var cartItemRepository: CartItemRepository
    private val dummyProducts = List(20) { Product(it.toLong(), "url", "name", 10_000) }
    private val dummyCartItems =
        dummyProducts.map { CartItem(it, LocalDateTime.MAX, 1).apply { id = it.id } }

    @BeforeEach
    fun setUp() {
        view = FakeAllSelectableItemView()
        cartItemRepository = mockk()
    }

    @Test
    fun `모두 선택 UI를 보여주기가 실행되면 장바구니 아이템 저장소에서 알맞은 페이지의 아이템들을 가져온다`() {
        val pageSize = 5
        val sut = ShowAllSelectionUI(view, cartItemRepository, pageSize)
        val currentPage = 2
        every { cartItemRepository.findAllOrderByAddedTime(any(), any()) } returns emptyList()

        sut.invoke(currentPage, setOf())

        verify { cartItemRepository.findAllOrderByAddedTime(pageSize, 5) }
    }

    @Test
    fun `현재 페이지의 아이템이 모두 선택되었다면 모두 선택된 상태라고 뷰를 설정한다`() {
        val pageSize = 5
        val sut = ShowAllSelectionUI(view, cartItemRepository, pageSize)
        val currentPage = 2
        every {
            cartItemRepository.findAllOrderByAddedTime(pageSize, (2 - 1) * pageSize)
        } returns dummyCartItems.slice(5 until 10)
        val selectedCartItems = dummyCartItems.slice(5 until 10).toSet()

        sut.invoke(currentPage, selectedCartItems)

        assertThat(view.isAllSelected).isTrue
    }

    @Test
    fun `현재 페이지의 아이템 중 선택되지 않은 아이템이 있다면 모두 선택되지 않은 상태라고 뷰를 설정한다`() {
        val pageSize = 5
        val sut = ShowAllSelectionUI(view, cartItemRepository, pageSize)
        val currentPage = 2
        every {
            cartItemRepository.findAllOrderByAddedTime(pageSize, (2 - 1) * pageSize)
        } returns dummyCartItems.slice(5 until 10)
        val selectedCartItems = dummyCartItems.slice(5 until 9).toSet()

        sut.invoke(currentPage, selectedCartItems)

        assertThat(view.isAllSelected).isFalse
    }

    @Test
    fun `현재 페이지에 아이템이 하나도 없다면 모두 선택되지 않은 상태라고 뷰를 설정한다`() {
        val pageSize = 5
        val sut = ShowAllSelectionUI(view, cartItemRepository, pageSize)
        val currentPage = 2
        every {
            cartItemRepository.findAllOrderByAddedTime(pageSize, (2 - 1) * pageSize)
        } returns listOf()
        val selectedCartItems = dummyCartItems.slice(5 until 9).toSet()

        sut.invoke(currentPage, selectedCartItems)

        assertThat(view.isAllSelected).isFalse
    }

    class FakeAllSelectableItemView : AllSelectableItemView {
        var isAllSelected: Boolean? = null

        override fun setStateOfAllSelection(isAllSelected: Boolean) {
            this.isAllSelected = isAllSelected
        }

    }
}