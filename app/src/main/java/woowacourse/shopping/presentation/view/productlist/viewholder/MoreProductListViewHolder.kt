package woowacourse.shopping.presentation.view.productlist.viewholder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ItemProductListMoreItemBinding
import woowacourse.shopping.presentation.view.productlist.adpater.ProductListAdapter

class MoreProductListViewHolder private constructor(view: View) : RecyclerView.ViewHolder(view) {
    private val binding = ItemProductListMoreItemBinding.bind(view)

    constructor(
        parent: ViewGroup,
        adapter: ProductListAdapter,
        onButtonClick: () -> Unit
    ) : this(
        LayoutInflater.from(parent.context).inflate(
            R.layout.item_product_list_more_item, parent, false
        )
    ) {
        binding.btItemProductListMore.setOnClickListener {
            onButtonClick()
        }
    }
}
