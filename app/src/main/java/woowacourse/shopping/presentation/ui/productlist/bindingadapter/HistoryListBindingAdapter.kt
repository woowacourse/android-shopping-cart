package woowacourse.shopping.presentation.ui.productlist.bindingadapter

import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import woowacourse.shopping.presentation.ui.productlist.uistates.ProductBrowsingHistoryUiState

@BindingAdapter("showHistoryLoadingOrFailure")
fun TextView.showHistoryLoadingOrFailure(state: ProductBrowsingHistoryUiState) {
    when (state) {
        is ProductBrowsingHistoryUiState.Success -> {
            visibility = View.GONE
        }
        is ProductBrowsingHistoryUiState.Loading -> {
            visibility = View.VISIBLE
            text = "로딩중"
        }
        is ProductBrowsingHistoryUiState.Failure -> {
            visibility = View.VISIBLE
            text = "실패"
        }
    }
}
