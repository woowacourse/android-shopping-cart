package woowacourse.shopping

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isClickable
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import woowacourse.shopping.shoppingcart.ShoppingCartActivity
import woowacourse.shopping.shoppingcart.ShoppingCartAdapter
import woowacourse.shopping.uimodel.CartItemUiModel

class ShoppingCartActivityTest {
    @get:Rule
    val activityRule = ActivityScenarioRule(ShoppingCartActivity::class.java)

    private lateinit var recyclerView: RecyclerView

    @Before
    fun setUp() {
        activityRule.scenario.onActivity { activity ->
            val items =
                listOf(
                    CartItemUiModel(
                        0,
                        "[든든] 동원 스위트콘",
                        1,
                        99800,
                        "https://media.istockphoto.com/" +
                            "id/1432690812/photo/old-wooden-doc" +
                            "k-at-the-lake-sunset-shot.webp?b=1&s=" +
                            "170667a&w=0&k=20&c=wu0hgDxT9wQ8fQMfR74gN_x" +
                            "b4AnFB795ceJ0QsyknH0=",
                    ),
                )

            recyclerView = activity.findViewById(R.id.rcv_shopping_cart)
            recyclerView.adapter = ShoppingCartAdapter(activity).apply { this.submitList(items) }
        }
    }

    @Test
    fun `장바구니의_아이템_개수가_5개_이하라면_버튼들이_비활성화된다`() {
        onView(withId(R.id.btn_shopping_cart_left)).check(matches(isClickable()))
        onView(withId(R.id.btn_shopping_cart_right)).check(matches(isClickable()))
    }
}
