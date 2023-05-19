package woowacourse.shopping.feature.main.product

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.common_ui.CounterView
import woowacourse.shopping.databinding.ItemMainProductBinding
import woowacourse.shopping.model.ProductUiModel

class MainProductViewHolder private constructor(
    private val binding: ItemMainProductBinding,
    listener: ProductClickListener
) : RecyclerView.ViewHolder(binding.root) {
    init {
        binding.listener = listener

        binding.counterView.countStateChangeListener =
            object : CounterView.OnCountStateChangeListener {
                override fun onCountChanged(counterNavigationView: CounterView?, count: Int) {
                    binding.product?.let { listener.onCartCountChanged(it.id, count) }
                }
            }
    }

    fun bind(product: ProductUiModel) {
        binding.product = product
        binding.counterView.setCountState(product.count, false)
    }

    companion object {
        fun create(parent: ViewGroup, listener: ProductClickListener): MainProductViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemMainProductBinding.inflate(layoutInflater, parent, false)
            return MainProductViewHolder(binding, listener)
        }
    }
}
