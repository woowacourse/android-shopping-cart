package woowacourse.shopping.presentation.productlist.recentproduct

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ItemRecentProductBinding
import woowacourse.shopping.presentation.model.ProductModel

class RecentProductItemViewHolder(
    parent: ViewGroup,
    inflater: LayoutInflater,
    onItemClick: (position: Int) -> Unit,
) : RecyclerView.ViewHolder(
    inflater.inflate(R.layout.item_recent_product, parent, false),
) {
    // 사용하진 않지만 확장성을 위해 정의
    constructor(parent: ViewGroup, onItemClick: (Int) -> Unit) :
        this(parent, LayoutInflater.from(parent.context), onItemClick)

    private val binding = ItemRecentProductBinding.bind(itemView)

    init {
        itemView.setOnClickListener { onItemClick(adapterPosition) }
    }

    fun bind(product: ProductModel) {
        binding.productModel = product
    }
}
