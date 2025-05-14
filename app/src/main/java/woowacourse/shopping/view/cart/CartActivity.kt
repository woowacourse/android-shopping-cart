package woowacourse.shopping.view.cart

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ActivityCartBinding
import woowacourse.shopping.model.intent.getSerializableExtraData
import woowacourse.shopping.model.products.Product

class CartActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCartBinding
    private lateinit var adapter: CartAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_cart)
        binding.backImageBtn.setOnClickListener { finish() }

        val intentProductInCartData = intent.getSerializableExtraData<Product>("productInCart")

        intentProductInCartData?.let {
            Cart.add(it)
        }
        adapter =
            CartAdapter(Cart.productsInCart, onProductRemoveClickListener = {
                intentProductInCartData?.let {
                    Cart.remove(it)
                    adapter.notifyDataSetChanged()
                }
            })
        binding.rvProductsInCart.adapter = adapter
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}
