package woowacourse.shopping

import android.content.res.Resources
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher

class RecyclerViewMatcher(
    private val recyclerViewId: Int,
) {
    fun atPosition(position: Int): Matcher<View> = atPositionOnView(position, -1)

    fun atPositionOnView(
        position: Int,
        targetViewId: Int,
    ): Matcher<View> =
        object : TypeSafeMatcher<View>() {
            var resources: Resources? = null
            var childView: View? = null

            override fun describeTo(description: Description?) {
                var idDescription = recyclerViewId.toString()
                if (this.resources != null) {
                    idDescription =
                        try {
                            this.resources!!.getResourceName(recyclerViewId)
                        } catch (e: Resources.NotFoundException) {
                            recyclerViewId.toString()
                        }
                }
                description?.appendText("$idDescription $position")
            }

            override fun matchesSafely(view: View): Boolean {
                this.resources = view.resources
                if (childView == null) {
                    val recyclerView =
                        view.rootView.findViewById<RecyclerView>(recyclerViewId) ?: return false
                    val viewHolder =
                        recyclerView.findViewHolderForAdapterPosition(position) ?: return false
                    childView = viewHolder.itemView
                }

                return if (targetViewId == -1) {
                    view == childView
                } else {
                    val targetView = childView!!.findViewById<View>(targetViewId)
                    view == targetView
                }
            }
        }

    companion object {
        fun withRecyclerView(recyclerViewId: Int): RecyclerViewMatcher = RecyclerViewMatcher((recyclerViewId))

        fun hasDrawable(): Matcher<View> =
            object : TypeSafeMatcher<View>() {
                override fun describeTo(description: Description) {
                    description.appendText("사진 있음")
                }

                override fun matchesSafely(view: View): Boolean = view is ImageView && view.drawable != null
            }
    }
}
