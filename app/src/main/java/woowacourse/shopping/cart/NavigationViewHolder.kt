package woowacourse.shopping.cart

import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemCartNavigatorBinding

class NavigationViewHolder(
    private val binding: ItemCartNavigatorBinding,
    onPreviousButtonClick: () -> Unit,
    onNextButtonClick: () -> Unit
) : RecyclerView.ViewHolder(binding.root) {
    init {
        binding.cartNavigatorPreviousButton.setOnClickListener {
            onPreviousButtonClick()
        }

        binding.cartNavigatorNextButton.setOnClickListener {
            onNextButtonClick()
        }
    }

    fun bind(currentPage: Int, isLastPage: Boolean) {
        binding.cartNavigatorPageText.text = currentPage.toString()
        binding.cartNavigatorPreviousButton.isEnabled = currentPage != 1
        binding.cartNavigatorNextButton.isEnabled = !isLastPage
    }
}
