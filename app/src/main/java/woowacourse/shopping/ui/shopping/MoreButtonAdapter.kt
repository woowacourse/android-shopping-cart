package woowacourse.shopping.ui.shopping

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemButtonMoreBinding

class MoreButtonAdapter(private val onItemClick: () -> Unit) :
    RecyclerView.Adapter<MoreButtonViewHolder>() {

    private lateinit var itemButtonMoreBinding: ItemButtonMoreBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoreButtonViewHolder {
        itemButtonMoreBinding =
            ItemButtonMoreBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MoreButtonViewHolder(itemButtonMoreBinding, onItemClick)
    }

    fun updateVisibility(hasNext: Boolean) {
        itemButtonMoreBinding.hasNext = hasNext
    }

    override fun getItemViewType(position: Int): Int = ShoppingViewHolderType.MORE_BUTTON.value

    override fun getItemCount(): Int = 1

    override fun onBindViewHolder(holder: MoreButtonViewHolder, position: Int) {}
}
