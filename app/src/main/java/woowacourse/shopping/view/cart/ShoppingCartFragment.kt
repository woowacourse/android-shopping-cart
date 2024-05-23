package woowacourse.shopping.view.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import woowacourse.shopping.R
import woowacourse.shopping.data.repository.CartRepositoryImpl
import woowacourse.shopping.data.repository.ProductRepositoryImpl
import woowacourse.shopping.databinding.FragmentShoppingCartBinding
import woowacourse.shopping.view.cart.adapter.ShoppingCartAdapter
import woowacourse.shopping.view.detail.ProductDetailFragment

class ShoppingCartFragment : Fragment() {
    private var _binding: FragmentShoppingCartBinding? = null
    val binding: FragmentShoppingCartBinding get() = _binding!!
    private lateinit var adapter: ShoppingCartAdapter

    private val shoppingCartViewModel: ShoppingCartViewModel by lazy {
        val viewModelFactory =
            ShoppingCartViewModelFactory(
                ProductRepositoryImpl(requireContext()),
                CartRepositoryImpl(requireContext()),
            )
        viewModelFactory.create(ShoppingCartViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentShoppingCartBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        setupDataBinding()
        observeData()
    }

    private fun setupDataBinding() {
        binding.viewModel = shoppingCartViewModel
        binding.shoppingCartActionHandler = shoppingCartViewModel
        adapter =
            ShoppingCartAdapter(
                shoppingCartActionHandler = shoppingCartViewModel,
                countActionHandler = shoppingCartViewModel,
            )
        binding.rvShoppingCart.adapter = adapter
        binding.rvShoppingCart.itemAnimator?.changeDuration = 0
    }

    private fun observeData() {
        shoppingCartViewModel.pagedData.observe(viewLifecycleOwner) {
            adapter.updateCartItems(it)
        }

        shoppingCartViewModel.navigateToBack.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let { parentFragmentManager.popBackStack() }
        }

        shoppingCartViewModel.navigateToDetail.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let { navigateToDetail(it) }
        }

        shoppingCartViewModel.updatedCountInfo.observe(viewLifecycleOwner) {
            adapter.updateCartItem(it)
        }
    }

    private fun navigateToDetail(productId: Long) {
        val productFragment =
            ProductDetailFragment().apply {
                arguments = ProductDetailFragment.createBundle(productId)
            }
        changeFragment(productFragment)
    }

    private fun changeFragment(nextFragment: Fragment) {
        parentFragmentManager
            .beginTransaction()
            .add(R.id.fragment_container, nextFragment)
            .addToBackStack(null)
            .commit()
    }
}
