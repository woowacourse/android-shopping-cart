package woowacourse.shopping.view.viewmodel

//import androidx.arch.core.executor.testing.InstantTaskExecutorRule
//import org.assertj.core.api.Assertions.assertThat
//import org.junit.Rule
//import org.junit.jupiter.api.BeforeEach
//import org.junit.jupiter.api.Test
//import woowacourse.shopping.TestFixture.getOrAwaitValue
//import woowacourse.shopping.domain.model.CartItem
//import woowacourse.shopping.domain.model.Product
//import kotlin.concurrent.thread
//
//class ShoppingCartTest {
//    private lateinit var shoppingCart: ShoppingCart
//
//    @get:Rule
//    val instantExecutorRule = InstantTaskExecutorRule()
//
//    @BeforeEach
//    fun setUp() {
//        shoppingCart = ShoppingCart()
//    }
//
//    @Test
//    fun `장바구니에_아이템을_추가할_수_있다`() {
//
//        // given
//        val item = CartItem(0, Product(0, "상품", 1000, ""))
//        // when
//        thread {
//            shoppingCart.addProducts(listOf(item))
//        }
//
//        val cartItems = shoppingCart.cartItems.getOrAwaitValue()
//        assertThat(cartItems.size).isEqualTo(1)
//        assertThat(cartItems.contains(item)).isEqualTo(true)
//    }
//
////    @Test
////    fun `장바구니에서_선택한_아이템을_삭제할_수_있다`() {
////        // given
////        val item = CartItem(0, Product(0, "상품", 1000, ""))
////        thread {
////            shoppingCart.addProducts(listOf(item))
////        }.join()
////
////        // when
////        thread {
////            shoppingCart.deleteProduct(item.id)
////        }.join()
////
////        // then
//////        assertThat(shoppingCart.cartItems.value?.size).isEqualTo(0)
////        assertThat(shoppingCart.cartItems).isEqualTo(0)
////        assertThat(shoppingCart.cartItems.value?.contains(item)).isEqualTo(false)
////    }
//}
