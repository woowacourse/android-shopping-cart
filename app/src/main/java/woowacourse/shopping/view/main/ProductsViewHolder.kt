package woowacourse.shopping.view.main

import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemProductBinding
import woowacourse.shopping.domain.Product

class ProductsViewHolder(
    private val binding: ItemProductBinding,
    onProductSelected: (Product) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {
    init {
        binding.onProductSelected = onProductSelected
        binding.onBtnItemProductAddToCartSelected = { quantity ->
            quantity.value = quantity.value?.inc()
        }
        binding.onQuantityMinusSelected = { quantity ->
            quantity.value = quantity.value?.dec()
        }
        binding.onQuantityPlusSelected = { quantity ->
            quantity.value = quantity.value?.inc()
        }
    }

    fun bind(
        item: Product,
        quantityLiveData: MutableLiveData<Int>,
    ) {
        binding.product = item
        binding.quantity = quantityLiveData
    }
}
