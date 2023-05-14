package woowacourse.shopping.view.productlist

import android.content.Context
import androidx.recyclerview.widget.GridLayoutManager

class GridLayoutManagerWrapper : GridLayoutManager {
    constructor(context: Context, spanCount: Int) : super(context, spanCount)

    override fun supportsPredictiveItemAnimations(): Boolean {
        return false
    }
}
