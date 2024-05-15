package woowacourse.shopping.view.cart

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import woowacourse.shopping.R
import woowacourse.shopping.databinding.FragmentShoppingCartBinding
import woowacourse.shopping.view.MainActivity
import woowacourse.shopping.view.MainViewModel
import woowacourse.shopping.view.cart.adapter.ShoppingCartAdapter
import woowacourse.shopping.view.detail.ProductDetailFragment

class ShoppingCartFragment : Fragment(), OnClickShoppingCart {
    private var _binding: FragmentShoppingCartBinding? = null
    val binding: FragmentShoppingCartBinding get() = _binding!!
    private lateinit var mainViewModel: MainViewModel
    private lateinit var adapter: ShoppingCartAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentShoppingCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainViewModel = (requireActivity() as MainActivity).viewModel
        initView()
    }

    private fun initView() {
        mainViewModel.loadPagingCartItem()
        binding.onClickShoppingCart = this
        adapter = ShoppingCartAdapter(onClickShoppingCart = this){
            mainViewModel.loadPagingCartItem()
        }
        binding.rvShoppingCart.adapter = adapter
        mainViewModel.shoppingCart.cartItems.observe(viewLifecycleOwner) { shoppingCart ->
            view?.post{
                adapter.updateCartItems(addedCartItems = shoppingCart)
            }
        }
    }

    override fun clickBack() {
        parentFragmentManager.popBackStack()
    }

    override fun clickCartItem(productId: Long) {
        val productFragment = ProductDetailFragment().apply {
            arguments = ProductDetailFragment.createBundle(productId)
        }
        changeFragment(productFragment)
    }

    override fun clickRemoveCartItem(cartItemId: Long) {
        mainViewModel.deleteShoppingCartItem(cartItemId)
    }

    private fun changeFragment(nextFragment: Fragment) {
        parentFragmentManager
            .beginTransaction()
            .add(R.id.fragment_container, nextFragment)
            .addToBackStack(null)
            .commit()
    }
}
