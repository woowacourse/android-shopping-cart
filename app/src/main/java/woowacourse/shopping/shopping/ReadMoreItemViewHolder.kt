package woowacourse.shopping.shopping

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ItemReadMoreBinding

class ReadMoreItemViewHolder private constructor(
    private val binding: ItemReadMoreBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(
        readMoreDescription: String = binding.root.context.getString(R.string.read_more),
        onClicked: () -> Unit
    ) {
        binding.buttonShowMore.text = readMoreDescription
        binding.buttonShowMore.setOnClickListener {
            onClicked()
        }
    }

    companion object {
        fun from(parent: ViewGroup): ReadMoreItemViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemReadMoreBinding.inflate(layoutInflater, parent, false)

            return ReadMoreItemViewHolder(binding)
        }
    }
}
