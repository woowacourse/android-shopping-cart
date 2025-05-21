package woowacourse.shopping.mathcer

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import org.hamcrest.Description
import org.hamcrest.TypeSafeMatcher
import woowacourse.shopping.R

object ProductRecyclerViewMatchers {
    fun atPositionOnView(
        position: Int,
        targetViewId: Int,
    ) = object : TypeSafeMatcher<View>() {
        override fun describeTo(description: Description) = Unit

        override fun matchesSafely(view: View): Boolean {
            val recyclerView =
                view.rootView.findViewById<RecyclerView>(R.id.recycler_view_product)
                    ?: return false
            val viewHolder =
                recyclerView.findViewHolderForAdapterPosition(position)
                    ?: return false
            val targetView = viewHolder.itemView.findViewById<View>(targetViewId)
            return view === targetView
        }
    }
}
