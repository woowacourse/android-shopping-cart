package woowacourse.shopping.feature.goods

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.swipeUp
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.idling.CountingIdlingResource
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import woowacourse.shopping.R
import woowacourse.shopping.feature.cart.CartActivity
import woowacourse.shopping.feature.goodsdetails.GoodsDetailsActivity

@Suppress("ktlint:standard:function-naming")
class GoodsActivityTest {
    private lateinit var countingIdlingResource: CountingIdlingResource

    @Before
    fun registerIdlingResource() {
        countingIdlingResource = CountingIdlingResource("GoodsActivity")
        IdlingRegistry.getInstance().register(countingIdlingResource)
    }

    @After
    fun unregisterIdlingResource() {
        IdlingRegistry.getInstance().unregister(countingIdlingResource)
    }

    @Before
    fun setup() {
        ActivityScenario.launch(GoodsActivity::class.java)
        Intents.init()
    }

    @Test
    fun 상품_목록이_표시된다() {
        onView(withId(R.id.rv_goods_items))
            .check(matches(isDisplayed()))
    }

    @Test
    fun 스크롤_했을_때_더보기_버튼이_표시된다() {
        repeat(5) {
            onView(withId(R.id.rv_goods_items)).perform(swipeUp())
        }

        onView(withId(R.id.btn_more))
            .check(matches(isDisplayed()))
    }

    @Test
    fun 더보기_버튼을_클릭하면_다음_상품들이_로드된다() {
        var beforeItemCount = 0
        onView(withId(R.id.rv_goods_items)).check { view, _ ->
            val recyclerView = view as RecyclerView
            beforeItemCount = recyclerView.adapter?.itemCount ?: 0
        }

        repeat(5) {
            onView(withId(R.id.rv_goods_items)).perform(swipeUp())
        }

        Thread.sleep(1000)
        onView(withId(R.id.btn_more)).perform(click())

        var afterItemCount = 0
        onView(withId(R.id.rv_goods_items)).check { view, _ ->
            val recyclerView = view as RecyclerView
            afterItemCount = recyclerView.adapter?.itemCount ?: 0
        }

        assertThat(afterItemCount > beforeItemCount).isTrue
    }

    @Test
    fun 상품_아이템을_클릭하면_상품_상세_화면으로_이동한다() {
        Thread.sleep(200)

        onView(withId(R.id.rv_goods_items))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    1,
                    click(),
                ),
            )

        Thread.sleep(200)
        intended(hasComponent(GoodsDetailsActivity::class.java.name))
    }

    @Test
    fun 장바구니_아이콘을_클릭하면_장바구니_화면으로_이동한다() {
        onView(withId(R.id.nav_cart)).perform(click())

        intended(hasComponent(CartActivity::class.java.name))
    }

    @Test
    fun 상품의_플러스_버튼을_클릭하면_수량이_증가한다() {
        Thread.sleep(200)

        // When 첫 번째 상품 (position 1)의 + 버튼 클릭 (수량 0 → 1)
        onView(withId(R.id.rv_goods_items))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    1,
                    clickOnChildView(R.id.btn_plus_quantity_round),
                ),
            )

        Thread.sleep(200)

        // Then quantitySelector가 나타났는지 확인
        onView(withId(R.id.rv_goods_items))
            .check { view, _ ->
                val recyclerView = view as RecyclerView
                val viewHolder = recyclerView.findViewHolderForAdapterPosition(1)
                val quantitySelector =
                    viewHolder?.itemView?.findViewById<View>(R.id.quantitySelector)
                assertThat(quantitySelector?.visibility).isEqualTo(View.VISIBLE)

                // 수량이 1로 표시되는지 확인
                val quantityText = quantitySelector?.findViewById<TextView>(R.id.tv_quantity)
                assertThat(quantityText?.text?.toString()).isEqualTo("1")
            }
    }

    @Test
    fun 수량_선택기의_플러스_버튼으로_수량을_더_증가시킬_수_있다() {
        // Given 수량을 1로 만들기 (동그란 + 버튼 클릭)
        Thread.sleep(500)
        onView(withId(R.id.rv_goods_items))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    1,
                    clickOnChildView(R.id.btn_plus_quantity_round),
                ),
            )

        Thread.sleep(500)

        // When quantitySelector 내부 + 버튼 클릭
        onView(withId(R.id.rv_goods_items))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    1,
                    clickOnChildView(R.id.btn_plus_quantity),
                ),
            )

        Thread.sleep(500)

        // Then 수량이 2로 증가했는지 확인
        onView(withId(R.id.rv_goods_items))
            .check { view, _ ->
                val recyclerView = view as RecyclerView
                val viewHolder = recyclerView.findViewHolderForAdapterPosition(1)
                val quantitySelector =
                    viewHolder?.itemView?.findViewById<View>(R.id.quantitySelector)
                val quantityText = quantitySelector?.findViewById<TextView>(R.id.tv_quantity)
                assertThat(quantityText?.text?.toString()).isEqualTo("2")
            }
    }

    @Test
    fun 수량_선택기의_마이너스_버튼으로_수량을_감소시킬_수_있다() {
        Thread.sleep(500)

        // Given (수량을 2로)
        onView(withId(R.id.rv_goods_items))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    1,
                    clickOnChildView(R.id.btn_plus_quantity_round),
                ),
            )
        Thread.sleep(500)

        onView(withId(R.id.rv_goods_items))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    1,
                    clickOnChildView(R.id.btn_plus_quantity),
                ),
            )
        Thread.sleep(500)

        // When (- 버튼 클릭 수량 2 → 1)
        onView(withId(R.id.rv_goods_items))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    1,
                    clickOnChildView(R.id.btn_minus_quantity),
                ),
            )

        Thread.sleep(500)

        // Then 수량이 1로 감소했는지 확인
        onView(withId(R.id.rv_goods_items))
            .check { view, _ ->
                val recyclerView = view as RecyclerView
                val viewHolder = recyclerView.findViewHolderForAdapterPosition(1)
                val quantitySelector =
                    viewHolder?.itemView?.findViewById<View>(R.id.quantitySelector)
                val quantityText = quantitySelector?.findViewById<TextView>(R.id.tv_quantity)
                assertThat(quantityText?.text?.toString()).isEqualTo("1")

                // quantitySelector는 여전히 표시되어야 함
                assertThat(quantitySelector?.visibility).isEqualTo(View.VISIBLE)
            }
    }

    private fun clickOnChildView(viewId: Int): ViewAction =
        object : ViewAction {
            override fun getConstraints() = isDisplayed()

            override fun getDescription() = "Click on child view with id $viewId"

            override fun perform(
                uiController: UiController,
                view: View,
            ) {
                val childView = view.findViewById<View>(viewId)
                if (childView != null && childView.visibility == View.VISIBLE) {
                    childView.performClick()
                } else {
                    throw RuntimeException("View with id $viewId not found or not visible")
                }
            }
        }

    private fun findQuantityTextView(view: View?): TextView? {
        if (view == null) return null

        if (view is TextView && view.id == R.id.tv_quantity) {
            return view
        }

        if (view is ViewGroup) {
            for (i in 0 until view.childCount) {
                val found = findQuantityTextView(view.getChildAt(i))
                if (found != null) return found
            }
        }

        return null
    }

    @After
    fun tearDown() {
        Intents.release()
    }
}
