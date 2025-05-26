package woowacourse.shopping.view.product.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ComponentDividerBinding

class DividerHolder(
    val binding: ComponentDividerBinding,
) : RecyclerView.ViewHolder(binding.root) {
    companion object {
        fun from(parent: ViewGroup): DividerHolder {
            val binding =
                ComponentDividerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return DividerHolder(binding)
        }
    }
}
