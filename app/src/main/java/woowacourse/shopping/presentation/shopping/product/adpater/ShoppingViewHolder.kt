package woowacourse.shopping.presentation.shopping.product.adpater

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemPlusProductBinding
import woowacourse.shopping.databinding.ItemProductBinding
import woowacourse.shopping.presentation.shopping.product.ShoppingUiModel

sealed class ShoppingViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    class ProductViewHolder(
        private val binding: ItemProductBinding,
        private val onClickItem: (id: Long) -> Unit,
        private val onClickAddBtn: (id: Long) -> Unit,
    ) : ShoppingViewHolder(binding.root) {
        fun bind(product: ShoppingUiModel.Product) {
            binding.product = product
            binding.root.setOnClickListener { onClickItem(product.id) }
            binding.itemProductCount.btnProductAdd.setOnClickListener {
                if (product.isVisible) {
                    binding.itemProductCount.btnMinus.visibility = View.VISIBLE
                    binding.itemProductCount.btnPlus.visibility = View.VISIBLE
                    binding.itemProductCount.tvCount.visibility = View.VISIBLE
                    binding.itemProductCount.btnProductAdd.visibility = View.INVISIBLE
                } else {
                    binding.itemProductCount.btnMinus.visibility = View.INVISIBLE
                    binding.itemProductCount.btnPlus.visibility = View.INVISIBLE
                    binding.itemProductCount.tvCount.visibility = View.INVISIBLE
                    binding.itemProductCount.btnProductAdd.visibility = View.VISIBLE
                }
                onClickAddBtn(product.id)

            }
        }
    }

    class PlusViewHolder(
        private val binding: ItemPlusProductBinding,
        private val onPlusItem: () -> Unit,
    ) : ShoppingViewHolder(binding.root) {
        fun bind() {
            binding.root.setOnClickListener { onPlusItem() }
        }
    }
}
