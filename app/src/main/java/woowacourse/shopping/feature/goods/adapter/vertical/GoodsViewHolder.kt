package woowacourse.shopping.feature.goods.adapter.vertical

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemGoodsBinding
import woowacourse.shopping.domain.model.CartItem
import woowacourse.shopping.feature.QuantityChangeListener

class GoodsViewHolder(
    val binding: ItemGoodsBinding,
    goodsClickListener: GoodsClickListener,
    quantityChangeListener: QuantityChangeListener,
) : RecyclerView.ViewHolder(binding.root) {
    init {
        binding.clickListener = goodsClickListener
        binding.quantityChangeListener = quantityChangeListener
    }

    fun bind(cartItem: CartItem) {
        binding.cartItem = cartItem
    }

    companion object {
        fun from(
            parent: ViewGroup,
            goodsClickListener: GoodsClickListener,
            quantityChangeListener: QuantityChangeListener,
        ): GoodsViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ItemGoodsBinding.inflate(inflater, parent, false)
            return GoodsViewHolder(binding, goodsClickListener, quantityChangeListener)
        }
    }
}
