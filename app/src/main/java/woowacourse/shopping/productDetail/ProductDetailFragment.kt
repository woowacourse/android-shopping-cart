package woowacourse.shopping.productDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import woowacourse.shopping.R
import woowacourse.shopping.databinding.FragmentProductDetailBinding
import woowacourse.shopping.listener.OnClickCartItemCounter
import woowacourse.shopping.model.CartItem

class ProductDetailFragment : Fragment(), OnClickCartItemCounter {
    private val viewModel: ProductDetailViewModel by viewModels {
        val id = arguments?.getInt("productId") ?: 1
        ProductDetailViewModel.factory(productId = id)
    }

    private var _binding: FragmentProductDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentProductDetailBinding.inflate(inflater)

        binding.vm = viewModel
        binding.lifecycleOwner = this
        binding.listener = this
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        binding.productDetailQuantityButton.productDetailPlus.visibility = View.VISIBLE
        binding.productDetailQuantityButton.productDetailMinus.visibility = View.VISIBLE
        binding.productDetailQuantityButton.productDetailProductCount.visibility = View.VISIBLE

        binding.productDetailToolbar.setOnMenuItemClickListener { clickXButton(it) }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun clickXButton(it: MenuItem) =
        when (it.itemId) {
            R.id.action_x -> {
                parentFragmentManager.popBackStack()
                true
            }

            else -> false
        }

    override fun increaseQuantity(cartItem: CartItem) {
        viewModel.addProductToCart()
    }

    override fun decreaseQuantity(cartItem: CartItem) {
        viewModel.subtractProductCount()
    }
}
