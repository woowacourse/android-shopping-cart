package woowacourse.shopping.presentation.productlist.product

import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemMoreBinding
import woowacourse.shopping.presentation.productlist.ProductListContract

class MoreItemViewHolder(
    binding: ItemMoreBinding,
    presenter: ProductListContract.Presenter,
) : RecyclerView.ViewHolder(binding.root) {
    init {
        itemView.setOnClickListener { presenter.loadMoreProductItems() }
    }
}
