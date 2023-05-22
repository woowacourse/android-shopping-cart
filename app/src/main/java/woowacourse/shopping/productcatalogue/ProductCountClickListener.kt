package woowacourse.shopping.productcatalogue

import android.widget.TextView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import woowacourse.shopping.uimodel.ProductUIModel

interface ProductCountClickListener {
    fun onDownClicked(product: ProductUIModel, countView: TextView)
    fun onUpClicked(product: ProductUIModel, countView: TextView)
    fun onShowClicked(down: TextView, up: TextView, count: TextView, show: FloatingActionButton)
}
