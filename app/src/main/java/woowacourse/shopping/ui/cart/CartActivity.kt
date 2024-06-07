package woowacourse.shopping.ui.cart

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ActivityCartBinding
import woowacourse.shopping.model.Product


class CartActivity : AppCompatActivity() {

    private val binding by lazy { ActivityCartBinding.inflate(layoutInflater) }

    private val viewModel: CartViewModel by lazy {
        CartViewModelFactory().create(CartViewModel::class.java)
    }

    private lateinit var dateFormatter: DateFormatter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupDateFormatter()
        setupBinding()
        setupToolbar()
        setupView()
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    private fun setupDateFormatter() {
        dateFormatter = DateFormatter(this)
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setupBinding() {
        binding.lifecycleOwner = this
        binding.vm = viewModel
        setContentView(binding.root)
    }

    private fun setupView() {
        setupCartProductData()
        setupCartProductList()
    }

    private fun setupCartProductData() {
        viewModel.getAllCartProducts()
    }

    private fun setupCartProductList() {
        setupAdapter()
        viewModel.uiState.observe(this) {
            if (it is CartUiState.Success) {
                addCartProducts(it.cartProducts)
            } else if (it is CartUiState.Error) {
                Toast.makeText(this, "에러 발생했어요~", Toast.LENGTH_SHORT).show()
            }
        }
        viewModel.onCartProductDeleted.observe(this) {
            Toast.makeText(this, getString(R.string.cart_deleted), Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupAdapter() {
        val adapter = CartProductAdapter(
            onClickDelete = viewModel::deleteCartProduct,
            dateFormatter = dateFormatter
        )
        binding.rvCartProducts.setHasFixedSize(true)
        binding.rvCartProducts.adapter = adapter
    }

    private fun addCartProducts(products: List<Product>) {
        val adapter = binding.rvCartProducts.adapter as? CartProductAdapter
        adapter?.submitList(products)
    }
}
