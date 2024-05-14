package woowacourse.shopping.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.shopping.domain.model.CartItem
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.model.ShoppingCart

class ShoppingCartTest {
    private lateinit var shoppingCart: ShoppingCart

    @BeforeEach
    fun setUp(){
        shoppingCart = ShoppingCart()
    }

    @Test
    fun `장바구니에_아이템을_추가할_수_있다`(){
        //given
        val item = CartItem(0, Product(0,"상품",1000,""))
        //when
        shoppingCart.addProduct(item)

        //then
        assertThat(shoppingCart.cartItems.size).isEqualTo(1)
        assertThat(shoppingCart.cartItems.contains(item)).isEqualTo(true)
    }

    @Test
    fun `장바구니에서_선택한_아이템을_삭제할_수_있다`(){
        //given
        val item = CartItem(0, Product(0,"상품",1000,""))
        shoppingCart.addProduct(item)

        //when
        shoppingCart.deleteProduct(item.id)

        //then
        assertThat(shoppingCart.cartItems.size).isEqualTo(0)
        assertThat(shoppingCart.cartItems.contains(item)).isEqualTo(false)
    }
}
