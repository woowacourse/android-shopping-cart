package woowacourse.shopping.ui.basket

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import woowacourse.shopping.databinding.ItemBasketBinding
import woowacourse.shopping.domain.BasketProduct
import woowacourse.shopping.domain.Product
import woowacourse.shopping.ui.mapper.toDomain
import woowacourse.shopping.ui.model.UiBasketProduct

class BasketAdapter(
    private val onRemoveItemClick: (UiBasketProduct) -> Unit,
    private val minusClickListener: (Product) -> Unit,
    private val plusClickListener: (Product) -> Unit,
    private val onCheckedChangeListener: (BasketProduct) -> Unit
) :
    ListAdapter<UiBasketProduct, BasketViewHolder>(basketDiffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BasketViewHolder {
        val binding =
            ItemBasketBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BasketViewHolder(
            binding,
            { onRemoveItemClick(it) },
            { minusClickListener(it.toDomain()) },
            { plusClickListener(it.toDomain()) },
            { basketProduct, isChecked ->
                var processedBasketProduct = BasketProduct(
                    id = basketProduct.id,
                    count = basketProduct.count.toDomain(),
                    product = basketProduct.product.toDomain(),
                    checked = isChecked
                )
                onCheckedChangeListener(processedBasketProduct)
            }
        )
    }

    override fun onBindViewHolder(holder: BasketViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        private val basketDiffUtil = object : DiffUtil.ItemCallback<UiBasketProduct>() {
            override fun areItemsTheSame(oldItem: UiBasketProduct, newItem: UiBasketProduct):
                Boolean = oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: UiBasketProduct, newItem: UiBasketProduct):
                Boolean = oldItem == newItem
        }
    }
}
