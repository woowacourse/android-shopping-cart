package woowacourse.shopping.view.productdetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ActivityProductDetailBinding
import woowacourse.shopping.model.cart.CartItem
import woowacourse.shopping.view.intent.getSerializableExtraData
import woowacourse.shopping.view.products.QuantitySelectButtonListener

class ProductDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProductDetailBinding
    private val intentCartItemData by lazy {
        intent.getSerializableExtraData<CartItem>(CART_ITEM_DATA_KEY)
            ?: throw IllegalArgumentException()
    }
    private val productDetailViewModel: ProductDetailViewModel by lazy {
        ViewModelProvider(
            this,
            ProductDetailViewModel.Companion.Factory(intentCartItemData),
        )[ProductDetailViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_product_detail)
        binding.lifecycleOwner = this
        binding.viewModel = productDetailViewModel
        productDetailViewModel.setLastProductTitle()
        binding.quantitySelector.productId = intentCartItemData.product.id
        binding.quantitySelector.tvProductQuantity.text = intentCartItemData.quantity.toString()

        productDetailViewModel.quantity.observe(this) {
            binding.quantitySelector.tvProductQuantity.text = it.toString()
        }
        binding.quantitySelector.quantitySelectButtonListener =
            object : QuantitySelectButtonListener {
                override fun increase(productId: Long) {
                    productDetailViewModel.increaseQuantity(productId)
                }

                override fun decrease(productId: Long) {
                    productDetailViewModel.decreaseQuantity(productId)
                }
            }

        setCloseButtonClickListener()
        observeAddToCart()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun observeAddToCart() {
        productDetailViewModel.addToCart.observe(this) {
            it.getContentIfNotHandled()?.let {
                showAddToCartToastMessage()
                finish()
            }
        }
    }

    private fun setCloseButtonClickListener() {
        binding.closeImageBtn.setOnClickListener {
            finish()
        }
    }

    private fun showAddToCartToastMessage() {
        Toast.makeText(this, getString(R.string.add_to_cart_message), Toast.LENGTH_SHORT).show()
    }

    companion object {
        private const val CART_ITEM_DATA_KEY = "cartItem"

        fun getIntent(
            context: Context,
            cartItem: CartItem,
        ): Intent =
            Intent(context, ProductDetailActivity::class.java).apply {
                putExtra(CART_ITEM_DATA_KEY, cartItem)
            }
    }
}
