package woowacourse.shopping.ui.cart

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import woowacourse.shopping.database.cart.CartRepositoryImpl
import woowacourse.shopping.database.product.ProductRepositoryImpl
import woowacourse.shopping.databinding.ActivityCartBinding
import woowacourse.shopping.ui.cart.adapter.CartListAdapter

class CartActivity : AppCompatActivity(), CartContract.View {
    private lateinit var binding: ActivityCartBinding
    private val presenter: CartPresenter by lazy {
        CartPresenter(this, CartRepositoryImpl(this, ProductRepositoryImpl))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initCartList()
    }

    private fun initCartList() {
        presenter.loadCartItems()
    }

    override fun setCartItems(cartItems: List<CartUIState>) {
        binding.recyclerViewCart.adapter = CartListAdapter(cartItems) {
            presenter.deleteCartItem(cartItems[it].id)
        }
    }

    companion object {
        fun startActivity(context: Context) {
            Intent(context, CartActivity::class.java).also {
                context.startActivity(it)
            }
        }
    }
}
