package woowacourse.shopping.presentation.cart.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ItemCartBinding
import woowacourse.shopping.presentation.model.ProductModel

class CartItemViewHolder(
    parent: ViewGroup,
    inflater: LayoutInflater,
    onCloseClick: (position: Int) -> Unit,
) : RecyclerView.ViewHolder(
    inflater.inflate(R.layout.item_cart, parent, false),
) {
    // 사용하진 않지만 확장성을 위해 정의
    constructor(parent: ViewGroup, onCloseClick: (position: Int) -> Unit) :
        this(parent, LayoutInflater.from(parent.context), onCloseClick)

    private val binding = ItemCartBinding.bind(itemView)

    init {
        binding.imageCartDelete.setOnClickListener { onCloseClick(adapterPosition) }
    }

    fun bind(product: ProductModel) {
        binding.productModel = product
    }
}
