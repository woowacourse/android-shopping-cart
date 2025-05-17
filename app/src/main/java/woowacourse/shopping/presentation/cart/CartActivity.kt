package woowacourse.shopping.presentation.cart

import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ActivityCartBinding
import woowacourse.shopping.domain.Product

class CartActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCartBinding
    private val viewModel: CartViewModel by viewModels { CartViewModel.Factory }
    private val cartProductAdapter by lazy { CartProductAdapter(::deleteProduct) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = DataBindingUtil.setContentView(this, R.layout.activity_cart)
        binding.vm = viewModel
        binding.clickHandler = viewModel
        binding.lifecycleOwner = this

        initInsets()
        initViews()
        observeViewModel()
        viewModel.loadItems()
    }

    private fun initInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.clCart) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun initViews() {
        binding.ibBack.setOnClickListener { finish() }
        binding.rvCartProduct.adapter = cartProductAdapter
    }

    private fun observeViewModel() {
        viewModel.toastMessage.observe(this) { resId ->
            Toast.makeText(this, getString(resId), Toast.LENGTH_SHORT).show()
        }

        viewModel.products.observe(this) {
            cartProductAdapter.setData(it)
        }
    }

    private fun deleteProduct(product: Product) {
        viewModel.deleteProduct(product)
        showToast(R.string.cart_delete_complete)
    }

    private fun showToast(messageResId: Int) {
        Toast.makeText(this, getString(messageResId), Toast.LENGTH_SHORT).show()
    }

    private fun ImageButton.setCyanOrGrayTint(isEnabled: Boolean) {
        val colorRes = if (isEnabled) R.color.cyan else R.color.gray1
        backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(context, colorRes))
    }

    companion object {
        fun newIntent(context: Context): Intent = Intent(context, CartActivity::class.java)
    }
}
