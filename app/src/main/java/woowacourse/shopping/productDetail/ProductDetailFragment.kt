package woowacourse.shopping.productDetail

import android.os.Bundle
import android.util.Log
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
        ProductDetailViewModel.factory(requireActivity().application, productId = id)
    }

    private var _binding: FragmentProductDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentProductDetailBinding.inflate(inflater, container, false)
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

        viewModel.shouldShowLastViewedProduct.observe(viewLifecycleOwner, { shouldShow ->
            Log.d("ProductDetailFragment", "shouldShowLastViewedProduct: $shouldShow")
        })

        binding.lastViewedProductContainer.setOnClickListener {
            navigateToLastViewedProduct()
        }
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

    private fun navigateToLastViewedProduct() {
        val lastViewedProductId = viewModel.lastViewedProduct.value?.id ?: return
        val fragment = ProductDetailFragment().apply {
            arguments = Bundle().apply {
                putInt("productId", lastViewedProductId)
            }
        }
        parentFragmentManager.beginTransaction()
            .replace(R.id.container, fragment)
            .addToBackStack(null)
            .commit()
    }
}
