package woowacourse.shopping.view.main.adapter

import androidx.recyclerview.widget.GridLayoutManager

object GridLayoutSpanSizeRule : GridLayoutManager.SpanSizeLookup() {
    override fun getSpanSize(position: Int): Int {
        return if (position == 0) 2 else 1
    }
}
