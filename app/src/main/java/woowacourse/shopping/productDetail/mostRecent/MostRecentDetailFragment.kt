package woowacourse.shopping.productDetail.mostRecent

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import woowacourse.shopping.R
import woowacourse.shopping.databinding.FragmentProductMostResentDetailBinding
import woowacourse.shopping.listener.OnClickCartItemCounter
import woowacourse.shopping.model.CartItem

class MostRecentDetailFragment : Fragment(), OnClickCartItemCounter {
    private val viewModel: MostRecentDetailViewModel by viewModels {
        val id = arguments?.getInt("productId") ?: 1
        MostRecentDetailViewModel.factory(requireActivity().application, productId = id)
    }

    private var _binding: FragmentProductMostResentDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentProductMostResentDetailBinding.inflate(inflater, container, false)
        binding.vm = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        binding.listener = this
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
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
