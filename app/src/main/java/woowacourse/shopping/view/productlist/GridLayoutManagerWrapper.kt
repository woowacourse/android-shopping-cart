package woowacourse.shopping.view.productlist

import android.content.Context
import androidx.recyclerview.widget.GridLayoutManager

class GridLayoutManagerWrapper(context: Context, spanCount: Int) :
    GridLayoutManager(context, spanCount) {

    override fun supportsPredictiveItemAnimations(): Boolean {
        return false
    }
}
