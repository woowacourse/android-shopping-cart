package woowacourse.shopping.presentation.productlist.product

import android.view.LayoutInflater
import android.view.ViewGroup
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ItemProductBinding
import woowacourse.shopping.presentation.model.ProductModel

class ProductItemViewHolder(
    parent: ViewGroup,
    inflater: LayoutInflater,
    onItemClick: (position: Int) -> Unit,
) : ProductBaseViewHolder(
    inflater.inflate(R.layout.item_product, parent, false),
    onItemClick,
) {
    // 사용하진 않지만 확장성을 위해 정의
    constructor(parent: ViewGroup, onItemClick: (Int) -> Unit) :
        this(parent, LayoutInflater.from(parent.context), onItemClick)

    private val binding = ItemProductBinding.bind(itemView)

    init {
        itemView.setOnClickListener { onItemClick(adapterPosition) }
    }

    override fun bind(product: ProductModel) {
        binding.productModel = product
    }
}
