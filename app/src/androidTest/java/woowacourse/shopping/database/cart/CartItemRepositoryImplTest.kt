package woowacourse.shopping.database.cart

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import androidx.test.core.app.ApplicationProvider
import org.junit.After
import org.junit.Before
import org.junit.Test
import woowacourse.shopping.database.DbHelper
import woowacourse.shopping.database.product.ProductRepositoryImpl
import woowacourse.shopping.domain.CartItem
import woowacourse.shopping.domain.Product
import java.time.LocalDateTime

/*
 * 상품의 아이디가 81에서 85인 장바구니 아이템이 데이터베이스에 저장되어 있지 않음을 전제로 합니다.
 * 상품의 아이디가 1인 장바구니 아이템이 데이터베이스에 저장되어 있음을 전제로 합니다.
 */
internal class CartItemRepositoryImplTest {
    private lateinit var context: Context
    private lateinit var sut: CartItemRepositoryImpl
    private lateinit var database: SQLiteDatabase

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
        database = DbHelper.getDbInstance(context)
        sut = CartItemRepositoryImpl(database, ProductRepositoryImpl)
        database.beginTransaction()
    }

    @After
    fun finish() {
        database.endTransaction()
    }

    @Test
    fun 이미_같은_상품의_장바구니_아이템이_저장되어_있다면_장바구니_아이템이_저장되지_않는다() {
        val cartItem = CartItem(Product(81L, "", "", 1), LocalDateTime.now(), 1)
        sut.save(cartItem)
        val otherCartItem = CartItem(Product(81L, "", "", 1), LocalDateTime.now(), 1)

        sut.save(otherCartItem)

        assert(otherCartItem.id == null)
    }

    @Test
    fun 장바구니_아이템을_저장하면_새로운_아이디를_부여한다() {
        val cartItem = CartItem(Product(81L, "", "", 1), LocalDateTime.now(), 1)

        sut.save(cartItem)

        assert(cartItem.id != null)
    }

    @Test
    fun 아이디_모음으로_장바구니_아이템들을_찾을_수_있다() {
        val ids = mutableListOf<Long>()
        val cartItems = mutableListOf<CartItem>()
        (81..85).forEach {
            val cartItem = CartItem(Product(it.toLong(), "", "", 1), LocalDateTime.now(), 1)
            sut.save(cartItem)
            cartItems.add(cartItem)
            ids.add(cartItem.id!!)
        }

        val findCartItems = sut.findAllByIds(ids)

        assert(findCartItems == cartItems)
    }

    @Test
    fun limit과_offset을_이용해_추가된_순서로_정렬된_장바구니_아이템들을_찾을_수_있다() {
        val cartItems = mutableListOf<CartItem>()
        (81..85).forEach {
            val cartItem = CartItem(
                Product(it.toLong(), "", "", 1),
                LocalDateTime.now().plusYears(2L).minusDays(it.toLong()),
                1
            )
            sut.save(cartItem)
            cartItems.add(cartItem)
        }

        val count = sut.countAll()
        val findCartItems = sut.findAllOrderByAddedTime(5, count - 5)

        assert(findCartItems.reversed() == cartItems)
    }

    @Test
    fun 아이디로_장바구니_아이템을_찾을_수_있다() {
        val cartItem = CartItem(Product(81L, "", "", 1), LocalDateTime.now(), 1)
        sut.save(cartItem)

        val findCartItem = sut.findById(cartItem.id!!)

        assert(findCartItem == cartItem)
    }

    @Test
    fun 상품의_아이디로_장바구니_아이템을_찾을_수_있다() {
        val cartItem = sut.findByProductId(1L)

        assert(cartItem != null)
    }

    @Test
    fun 상품의_아이디를_가진_장바구니_아이템이_존재하는지_알_수_있다() {
        val actual = sut.existByProductId(81)

        assert(actual.not())
    }

    @Test
    fun 장바구니_아이템의_수량을_업데이트하면_변한다() {
        val cartItem = CartItem(Product(81L, "", "", 1), LocalDateTime.now(), 1)
        sut.save(cartItem)

        sut.updateCountById(cartItem.id!!, 2)

        val findCartItem = sut.findById(cartItem.id!!)
        assert(findCartItem!!.count == 2)
    }

    @Test
    fun 아이디로_장바구니_아이템을_삭제할_수_있다() {
        val cartItem = CartItem(Product(81L, "", "", 1), LocalDateTime.now(), 1)
        sut.save(cartItem)

        sut.deleteById(cartItem.id!!)

        assert(sut.findById(cartItem.id!!) == null)
    }
}