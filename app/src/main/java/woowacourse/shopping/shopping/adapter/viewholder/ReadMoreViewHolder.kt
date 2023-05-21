package woowacourse.shopping.shopping.adapter.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import woowacourse.shopping.databinding.ItemReadMoreBinding

class ReadMoreViewHolder private constructor(
    binding: ItemReadMoreBinding,
    private val onReadMoreButtonClicked: () -> Unit,
) : ShoppingRecyclerItemViewHolder<ShoppingRecyclerItem.ReadMoreDescription, ItemReadMoreBinding>(
    binding
) {

    init {
        setOnClicked()
    }

    private fun setOnClicked() {
        binding.buttonReadMore.setOnClickListener { onReadMoreButtonClicked() }
    }

    override fun bind(itemData: ShoppingRecyclerItem.ReadMoreDescription) {
        binding.buttonReadMore.text = itemData.value
    }

    companion object {
        fun from(
            parent: ViewGroup,
            onReadMoreButtonClicked: () -> Unit,
        ): ReadMoreViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemReadMoreBinding.inflate(layoutInflater, parent, false)

            return ReadMoreViewHolder(
                binding = binding,
                onReadMoreButtonClicked = onReadMoreButtonClicked
            )
        }
    }
}
