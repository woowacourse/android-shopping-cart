package woowacourse.shopping.feature.main.load

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class LoadAdapter(
    val onClick: () -> Unit
) : RecyclerView.Adapter<LoadViewHolder>() {
    private lateinit var loadViewHolder: LoadViewHolder

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LoadViewHolder {
        loadViewHolder = LoadViewHolder.create(parent)
        return loadViewHolder
    }

    override fun getItemCount(): Int = 1

    override fun onBindViewHolder(holder: LoadViewHolder, position: Int) {
        holder.bind(onClick)
    }

    override fun getItemViewType(position: Int): Int {
        return VIEW_TYPE
    }

    fun hide() = loadViewHolder.hide()

    companion object {
        const val VIEW_TYPE = 333
    }
}
