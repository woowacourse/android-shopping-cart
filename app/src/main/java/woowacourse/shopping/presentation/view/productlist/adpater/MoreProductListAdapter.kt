package woowacourse.shopping.presentation.view.productlist.adpater

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.presentation.view.productlist.viewholder.MoreProductListViewHolder

class MoreProductListAdapter(
    private val onButtonClick: () -> Unit
) : RecyclerView.Adapter<MoreProductListViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoreProductListViewHolder {
        return MoreProductListViewHolder(parent) { onButtonClick() }
    }

    override fun onBindViewHolder(holder: MoreProductListViewHolder, position: Int) = Unit

    override fun getItemCount(): Int = 1
    override fun getItemViewType(position: Int): Int = ViewType.MORE_PRODUCT_LIST.ordinal
}
