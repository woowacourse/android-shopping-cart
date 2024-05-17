package woowacourse.shopping.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemReadMoreBinding

class ReadMoreViewHolder private constructor(
    binding: ItemReadMoreBinding,
    onClickReadMore: () -> Unit,
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.tvReadMore.setOnClickListener {
            onClickReadMore()
        }
    }

    companion object {
        fun from(parent: ViewGroup, onClickReadMore: () -> Unit): ReadMoreViewHolder {
            val binding =
                ItemReadMoreBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return ReadMoreViewHolder(binding, onClickReadMore)
        }
    }
}
