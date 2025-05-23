package woowacourse.shopping.matcher

import android.content.res.Resources
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.ViewAssertion
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers
import org.hamcrest.TypeSafeMatcher
import org.junit.Assert.fail

class RecyclerViewMatcher(private val recyclerViewId: Int) {
    fun atPositionOnView(
        position: Int,
        targetViewId: Int,
    ): Matcher<View> {
        return object : TypeSafeMatcher<View>() {
            var resources: Resources? = null

            override fun describeTo(description: Description) {
                var idDescription = recyclerViewId.toString()
                this.resources?.let {
                    idDescription =
                        runCatching {
                            it.getResourceName(recyclerViewId)
                        }.onFailure { e ->
                            String.format("%s (resource name not found)", recyclerViewId)
                        }.getOrThrow()
                }
                description.appendText("with id: $idDescription")
            }

            override fun matchesSafely(view: View): Boolean {
                this.resources = view.resources
                val recyclerView = view.rootView.findViewById<RecyclerView>(recyclerViewId)
                val childView = recyclerView?.findViewHolderForAdapterPosition(position)?.itemView

                return if (targetViewId == -1) {
                    view === childView
                } else {
                    val targetView = childView?.findViewById<View>(targetViewId)
                    view === targetView
                }
            }
        }
    }

    companion object {
        fun withRecyclerView(recyclerViewId: Int): RecyclerViewMatcher {
            return RecyclerViewMatcher(recyclerViewId)
        }
    }
}

fun scrollToPosition(position: Int): ViewAction {
    return object : ViewAction {
        override fun getDescription(): String {
            return "Scroll RecyclerView to position $position"
        }

        override fun getConstraints(): Matcher<View> {
            return Matchers.allOf(
                isAssignableFrom(RecyclerView::class.java),
                ViewMatchers.isDisplayed(),
            )
        }

        override fun perform(
            uiController: UiController?,
            view: View?,
        ) {
            if (view is RecyclerView) {
                val layoutManager = view.layoutManager
                if (layoutManager is LinearLayoutManager) {
                    layoutManager.scrollToPositionWithOffset(position, 0)
                    uiController?.loopMainThreadUntilIdle()
                } else {
                    throw IllegalStateException(
                        "RecyclerView's LayoutManager is not LinearLayoutManager. Cannot use scrollToPositionWithOffset.",
                    )
                }
            } else {
                throw IllegalStateException("View is not a RecyclerView")
            }
        }
    }
}

fun matchSize(size: Int): ViewAssertion {
    return ViewAssertion { view, noViewFoundException ->
        if (noViewFoundException != null) {
            throw noViewFoundException
        }
        if (view !is RecyclerView) {
            fail("Assertion failed: View is not a RecyclerView. Found: ${view?.javaClass?.name}")
        }

        val recyclerView = view as RecyclerView
        val itemCount = recyclerView.adapter?.itemCount ?: 0
        if (itemCount != size) {
            fail("Assertion failed: RecyclerView item count is not $size. Found: $itemCount")
        }
    }
}

fun matchSizeWithViewType(
    size: Int,
    viewType: Int,
): ViewAssertion {
    return ViewAssertion { view, noViewFoundException ->
        if (noViewFoundException != null) {
            throw noViewFoundException
        }
        if (view !is RecyclerView) {
            fail("Assertion failed: View is not a RecyclerView. Found: ${view?.javaClass?.name}")
        }

        val recyclerView = view as RecyclerView
        val itemCount = recyclerView.adapter?.itemCount ?: 0
        val matchingViewCount =
            (0..<itemCount).filter { i ->
                recyclerView.adapter?.getItemViewType(i) == viewType
            }.size
        if (matchingViewCount != size) {
            fail("Assertion failed: RecyclerView item count with matching view type is not $size. Found: $matchingViewCount")
        }
    }
}

fun sizeGreaterThan(size: Int): ViewAssertion {
    return ViewAssertion { view, noViewFoundException ->
        if (noViewFoundException != null) {
            throw noViewFoundException
        }
        if (view !is RecyclerView) {
            fail("Assertion failed: View is not a RecyclerView. Found: ${view?.javaClass?.name}")
        }

        val recyclerView = view as RecyclerView
        val itemCount = recyclerView.adapter?.itemCount ?: 0
        if (itemCount <= size) {
            fail("Assertion failed: RecyclerView item count is not greater than $size. Found: $itemCount")
        }
    }
}
