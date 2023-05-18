package woowacourse.shopping.feature.cart

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.domain.repository.CartRepository
import woowacourse.shopping.R
import woowacourse.shopping.data.cart.CartDao
import woowacourse.shopping.data.cart.CartRepositoryImpl
import woowacourse.shopping.databinding.ActivityCartBinding
import woowacourse.shopping.model.CartProductState

class CartActivity : AppCompatActivity(), CartContract.View {
    private var _binding: ActivityCartBinding? = null
    private val binding: ActivityCartBinding
        get() = _binding!!

    private val presenter: CartContract.Presenter by lazy {
        val cartRepo: CartRepository = CartRepositoryImpl(CartDao(this))
        CartPresenter(this, cartRepo)
    }
    private val adapter: CartProductListAdapter by lazy {
        CartProductListAdapter(
            onCartProductDeleteClick = { presenter.deleteCartProduct(it) },
            onCountMinusClick = { presenter.minusCountNumber(it, it.count) },
            onCountPlusClick = { presenter.plusCountNumber(it, it.count) }
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.cartProductRv.adapter = adapter
        binding.pageNumberPlusTv.setOnClickListener { presenter.plusPageNumber() }
        binding.pageNumberMinusTv.setOnClickListener { presenter.minusPageNumber() }
        presenter.loadCart()
    }

    override fun setCartProducts(cartProducts: List<CartProductState>) {
        adapter.setItems(cartProducts)
    }

    override fun setCartPageNumber(number: Int) {
        binding.pageNumberTv.text = number.toString()
    }

    override fun setCartPageNumberPlusEnable(isEnable: Boolean) {
        if (isEnable) {
            binding.pageNumberPlusTv.setBackgroundColor(getColor(R.color.teal_200))
        } else {
            binding.pageNumberPlusTv.setBackgroundColor(getColor(R.color.gray))
        }
    }

    override fun setCartPageNumberMinusEnable(isEnable: Boolean) {
        if (isEnable) {
            binding.pageNumberMinusTv.setBackgroundColor(getColor(R.color.teal_200))
        } else {
            binding.pageNumberMinusTv.setBackgroundColor(getColor(R.color.gray))
        }
    }

    companion object {
        fun startActivity(context: Context) {
            val intent = Intent(context, CartActivity::class.java)
            context.startActivity(intent)
        }
    }
}
