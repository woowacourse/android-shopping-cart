package woowacourse.shopping.shopping

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemReadMoreBinding

class ReadMoreViewHolder(
    val binding: ItemReadMoreBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(onClicked: () -> Unit) {
        binding.buttonShowMore.setOnClickListener {
            onClicked()
        }
    }

    companion object {
        const val READ_MORE_ITEM_TYPE = 2

        fun from(parent: ViewGroup): ReadMoreViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemReadMoreBinding.inflate(layoutInflater, parent, false)

            return ReadMoreViewHolder(binding)
        }
    }
}
