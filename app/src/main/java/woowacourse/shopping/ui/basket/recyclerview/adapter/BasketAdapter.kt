package woowacourse.shopping.ui.basket.recyclerview.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import woowacourse.shopping.model.UiBasketProduct
import woowacourse.shopping.model.UiProduct
import woowacourse.shopping.util.diffutil.BasketDiffUtil

class BasketAdapter(
    private val onDeleteClick: (UiBasketProduct) -> Unit,
    private val onSelectProduct: (UiProduct) -> Unit,
    private val onUnselectProduct: (UiProduct) -> Unit,
    private val onIncreaseCount: (UiProduct) -> Unit,
    private val onDecreaseCount: (UiProduct) -> Unit,
) : ListAdapter<UiBasketProduct, BasketViewHolder>(BasketDiffUtil) {
    private val onDelete: (Int) -> Unit = { pos -> onDeleteClick(currentList[pos]) }
    private val onSelect: (Int) -> Unit = { pos -> onSelectProduct(currentList[pos].product) }
    private val onUnselect: (Int) -> Unit = { pos -> onUnselectProduct(currentList[pos].product) }
    private val onIncrease: (Int) -> Unit = { pos -> onIncreaseCount(currentList[pos].product) }
    private val onDecrease: (Int) -> Unit = { pos -> onDecreaseCount(currentList[pos].product) }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BasketViewHolder =
        BasketViewHolder(parent, onDelete, onSelect, onUnselect, onIncrease, onDecrease)

    override fun onBindViewHolder(holder: BasketViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}
