package woowacourse.shopping.cart

import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemCartNavigatorBinding

class NavigationViewHolder(
    private val binding: ItemCartNavigatorBinding,
    cartNavigationListener: CartNavigationListener
) : RecyclerView.ViewHolder(binding.root) {
    init {
        binding.cartNavigatorPreviousButton.setOnClickListener {
            cartNavigationListener.onPreviousButtonClick()
        }

        binding.cartNavigatorNextButton.setOnClickListener {
            cartNavigationListener.onNextButtonClick()
        }
    }

    fun bind(currentPage: Int, isLastPage: Boolean) {
        binding.cartNavigatorPageText.text = currentPage.toString()
        binding.cartNavigatorPreviousButton.isEnabled = currentPage != 1
        binding.cartNavigatorNextButton.isEnabled = !isLastPage
    }
}
