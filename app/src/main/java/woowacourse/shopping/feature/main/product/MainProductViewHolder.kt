package woowacourse.shopping.feature.main.product

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.common_ui.CounterView
import woowacourse.shopping.databinding.ItemMainProductBinding
import woowacourse.shopping.model.ProductUiModel

class MainProductViewHolder private constructor(
    private val binding: ItemMainProductBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(product: ProductUiModel, listener: ProductClickListener) {
        binding.product = product
        binding.listener = listener
        binding.counterView.countStateChangeListener =
            object : CounterView.OnCountStateChangeListener {
                override fun onCountMinusChanged(counterNavigationView: CounterView?, count: Int) {
                    listener.minusCart(product.id, count)
                }

                override fun onCountPlusChanged(counterNavigationView: CounterView?, count: Int) {
                    listener.plusCart(product.id, count)
                }
            }
    }

    companion object {
        fun create(parent: ViewGroup): MainProductViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemMainProductBinding.inflate(layoutInflater, parent, false)
            return MainProductViewHolder(binding)
        }
    }
}
