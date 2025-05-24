package woowacourse.shopping.presentation.goods.list

import android.content.Context
import android.content.Intent
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.swipeUp
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import org.awaitility.kotlin.await
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.not
import org.hamcrest.TypeSafeMatcher
import org.junit.After
import org.junit.Before
import org.junit.Test
import woowacourse.shopping.R
import woowacourse.shopping.ShoppingApplication
import woowacourse.shopping.data.dao.LatestGoodsDao
import woowacourse.shopping.data.database.ShoppingDatabase
import woowacourse.shopping.data.repository.LatestGoodsRepositoryImpl
import woowacourse.shopping.domain.repository.LatestGoodsRepository
import woowacourse.shopping.presentation.shoppingcart.ShoppingCartActivity
import java.util.concurrent.TimeUnit

class GoodsActivityTest {
    private lateinit var scenario: ActivityScenario<GoodsActivity>
    private lateinit var db: ShoppingDatabase
    private lateinit var dao: LatestGoodsDao
    private lateinit var latestGoodsRepository: LatestGoodsRepository

    @Before
    fun setUp() {
        Intents.init()
        val context = ApplicationProvider.getApplicationContext<Context>()
        val application = context as ShoppingApplication
        db =
            Room
                .inMemoryDatabaseBuilder(context, ShoppingDatabase::class.java)
                .allowMainThreadQueries()
                .build()

        dao = db.latestGoodsDao()
        latestGoodsRepository = LatestGoodsRepositoryImpl(dao)
        application.initLatestGoodsRepository(latestGoodsRepository as LatestGoodsRepositoryImpl)

        latestGoodsRepository.insertLatestGoods(1)

        val intent = Intent(ApplicationProvider.getApplicationContext(), GoodsActivity::class.java)
        scenario = ActivityScenario.launch(intent)
    }

    @After
    fun tearDown() {
        Intents.release()
        scenario.close()
    }

    @Test
    fun `상품의_이름이_보인다`() {
        // given
        onView(withId(R.id.rv_goods_list)).perform(
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(
                0,
            ),
        )

        // then
        onView(
            allOf(
                withId(R.id.tv_goods_name),
                withText("[병천아우내] 모듬순대"),
                isDescendantOfA(nthChildOf(withId(R.id.rv_goods_list), 0)),
            ),
        ).check(matches(isDisplayed()))
    }

    @Test
    fun `상품의_가격이_보인다`() {
        // given
        onView(withId(R.id.rv_goods_list)).perform(
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(
                0,
            ),
        )

        // then
        onView(
            allOf(
                withId(R.id.tv_goods_price),
                withText("11,900원"),
                isDescendantOfA(nthChildOf(withId(R.id.rv_goods_list), 0)),
            ),
        ).check(matches(isDisplayed()))
    }

    @Test
    fun `장바구니_버튼을_클릭하면_장바구니_목록_화면으로_이동한다`() {
        // when
        onView(withId(R.id.action_cart)).perform(click())

        // then
        intended(hasComponent(ShoppingCartActivity::class.java.name))
    }

    @Test
    fun `페이지_끝에_도달하면_더보기_버튼이_나타난다`() {
        // when
        onView(withId(R.id.scroll)).perform(swipeUp())

        // then
        await.atMost(1, TimeUnit.SECONDS).until {
            try {
                onView(withId(R.id.btn_load_more)).check(matches(isDisplayed()))
                true
            } catch (e: Throwable) {
                false
            }
        }
    }

    @Test
    fun `더보기_버튼을_클릭하면_사라진다`() {
        // when
        onView(withId(R.id.scroll)).perform(swipeUp())

        // then
        await.atMost(2, TimeUnit.SECONDS).until {
            try {
                onView(withId(R.id.btn_load_more)).perform(click())

                onView(withId(R.id.btn_load_more)).check(matches(not(isDisplayed())))
                true
            } catch (e: Throwable) {
                false
            }
        }
    }

    @Test
    fun `선택되지_않은_상품의_수량_조절_버튼은_보이지_않는다`() {
        onView(
            allOf(
                withId(R.id.cl_goods_quantity),
                isDescendantOfA(nthChildOf(withId(R.id.rv_goods_list), 0)),
            ),
        ).check(matches(not(isDisplayed())))
    }

    @Test
    fun `더하기_버튼을_누르면_수량_조절_버튼이_보인다`() {
        // when
        onView(
            allOf(
                withId(R.id.btn_plus),
                isDescendantOfA(nthChildOf(withId(R.id.rv_goods_list), 0)),
            ),
        ).perform(click())

        // then
        await.atMost(1, TimeUnit.SECONDS).until {
            try {
                onView(
                    allOf(
                        withId(R.id.cl_goods_quantity),
                        isDescendantOfA(nthChildOf(withId(R.id.rv_goods_list), 0)),
                    ),
                ).check(matches(isDisplayed()))
                true
            } catch (e: Throwable) {
                false
            }
        }
    }

    @Test
    fun `선택된_개수가_0이_되면_수량_조절_버튼이_사라진다`() {
        // given
        onView(
            allOf(
                withId(R.id.btn_plus),
                isDescendantOfA(nthChildOf(withId(R.id.rv_goods_list), 0)),
            ),
        ).perform(click())

        // when
        onView(
            allOf(
                withId(R.id.tv_minus),
                isDescendantOfA(
                    allOf(
                        withId(R.id.cl_goods_quantity),
                        isDescendantOfA(nthChildOf(withId(R.id.rv_goods_list), 0)),
                    ),
                ),
            ),
        ).perform(click())

        // then
        onView(
            allOf(
                withId(R.id.cl_goods_quantity),
                isDescendantOfA(nthChildOf(withId(R.id.rv_goods_list), 0)),
            ),
        ).check(matches(not(isDisplayed())))
    }

    @Test
    fun `최근_본_상품_목록이_보인다`() {
        // then
        onView(
            allOf(
                withId(R.id.tv_latest_goods_name),
                withText("[병천아우내] 모듬순대"),
                isDescendantOfA(nthChildOf(withId(R.id.rv_latest_goods_list), 0)),
            ),
        ).check(matches(isDisplayed()))
    }

    private fun nthChildOf(
        parentMatcher: Matcher<View>,
        childPosition: Int,
    ): Matcher<View> {
        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Nth child of parent matcher")
            }

            override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent) && parent.getChildAt(
                    childPosition,
                ) == view
            }
        }
    }
}
