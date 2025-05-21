package woowacourse.shopping.view.main

import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemProductBinding
import woowacourse.shopping.domain.Product

class ProductsViewHolder(
    private val binding: ItemProductBinding,
    private val handler: ProductEventHandler,
) : RecyclerView.ViewHolder(binding.root) {
    init {
        binding.handler = handler
    }

    fun bind(
        item: Product,
        quantityLiveData: MutableLiveData<Int>,
    ) {
        binding.product = item
        binding.quantity = quantityLiveData
    }
}

interface ProductEventHandler {
    fun onProductSelected(product: Product)

    fun onBtnItemProductAddToCartSelected(quantity: MutableLiveData<Int>)

    fun onQuantityMinusSelected(quantity: MutableLiveData<Int>)

    fun onQuantityPlusSelected(quantity: MutableLiveData<Int>)

    fun whenQuantityChangedSelectView(quantity: MutableLiveData<Int>)
}
