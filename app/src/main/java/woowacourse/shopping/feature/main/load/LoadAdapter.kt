package woowacourse.shopping.feature.main.load

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemLoadMoreProductBinding

class LoadAdapter(
    private val onClick: () -> Unit
) : RecyclerView.Adapter<LoadViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LoadViewHolder {
        val binding =
            ItemLoadMoreProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LoadViewHolder(binding)
    }

    override fun getItemCount(): Int = 1

    override fun onBindViewHolder(holder: LoadViewHolder, position: Int) {
        holder.bind(onClick)
    }

    override fun getItemViewType(position: Int): Int {
        return VIEW_TYPE
    }

    companion object {
        const val VIEW_TYPE = 333
    }
}
