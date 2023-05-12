package woowacourse.shopping.presentation.view.productlist.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ItemProductListMoreItemBinding
import woowacourse.shopping.presentation.view.productlist.adpater.ProductListAdapter

class MoreProductListViewHolder(
    parent: ViewGroup,
    adapter: ProductListAdapter,
    onButtonClick: () -> Unit
) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context)
        .inflate(R.layout.item_product_list_more_item, parent, false)
) {
    private val binding = ItemProductListMoreItemBinding.bind(itemView)

    init {
        binding.btItemProductListMore.setOnClickListener {
            onButtonClick()
        }
    }
}
