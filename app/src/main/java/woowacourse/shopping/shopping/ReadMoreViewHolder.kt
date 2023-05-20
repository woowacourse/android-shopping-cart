package woowacourse.shopping.shopping

import android.view.LayoutInflater
import android.view.ViewGroup
import woowacourse.shopping.databinding.ItemReadMoreBinding

class ReadMoreViewHolder private constructor(
    binding: ItemReadMoreBinding,
) : ShoppingRecyclerItemViewHolder<ShoppingRecyclerItem.ReadMoreDescription, ItemReadMoreBinding>(binding) {

    fun setOnClicked(onClicked: () -> Unit) {
        binding.buttonReadMore.setOnClickListener { onClicked() }
    }

    override fun bind(itemData: ShoppingRecyclerItem.ReadMoreDescription) {
        binding.buttonReadMore.text = itemData.value
    }

    companion object {
        fun from(parent: ViewGroup): ReadMoreViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemReadMoreBinding.inflate(layoutInflater, parent, false)

            return ReadMoreViewHolder(binding)
        }
    }
}
