package woowacourse.shopping.presentation.view.productlist.adpater

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.presentation.view.productlist.viewholder.MoreProductListViewHolder

class MoreProductListAdapter(
    private val adapter: ProductListAdapter,
    private val onButtonClick: () -> Unit
) : RecyclerView.Adapter<MoreProductListViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoreProductListViewHolder {
        return MoreProductListViewHolder(parent, adapter) { onButtonClick() }
    }

    override fun onBindViewHolder(holder: MoreProductListViewHolder, position: Int) = Unit

    override fun getItemCount(): Int = 1

    companion object {
        internal const val VIEW_TYPE = 300
    }
}
