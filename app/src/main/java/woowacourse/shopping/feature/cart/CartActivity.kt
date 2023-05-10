package woowacourse.shopping.feature.cart

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import woowacourse.shopping.R
import woowacourse.shopping.data.CartRepositoryImpl
import woowacourse.shopping.data.sql.CartDao
import woowacourse.shopping.databinding.ActivityCartBinding

class CartActivity : AppCompatActivity(), CartContract.View {

    private lateinit var binding: ActivityCartBinding
    private lateinit var presenter: CartContract.Presenter
    private lateinit var cartProductAdapter: CartProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_cart)

        cartProductAdapter = CartProductAdapter(listOf())
        binding.cartItemRecyclerview.adapter = cartProductAdapter
        presenter = CartPresenter(this, CartRepositoryImpl(CartDao(this)))
        presenter.loadCartProduct()
    }

    override fun changeCartProducts(newItems: List<CartProductItemModel>) {
        cartProductAdapter.setItems(newItems)
    }

    override fun deleteCartProductFromScreen(position: Int) {
        presenter.deleteCartProduct(cartProductAdapter.items[position].cartProduct)
    }

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, CartActivity::class.java)
        }
    }
}
