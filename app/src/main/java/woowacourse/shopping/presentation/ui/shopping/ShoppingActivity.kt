package woowacourse.shopping.presentation.ui.shopping

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.R
import woowacourse.shopping.data.ShoppingItemsRepositoryImpl
import woowacourse.shopping.databinding.ActivityShoppingBinding
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.presentation.state.UIState
import woowacourse.shopping.presentation.ui.cart.CartActivity
import woowacourse.shopping.presentation.ui.detail.DetailActivity

class ShoppingActivity : AppCompatActivity(), ShoppingItemClickListener {
    private lateinit var binding: ActivityShoppingBinding
    private lateinit var adapter: ShoppingAdapter
    private val viewModel: ShoppingViewModel by viewModels {
        ShoppingViewModelFactory(ShoppingItemsRepositoryImpl())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShoppingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbarMain)
        setUpLoadMoreButton()
        setUpAdapter()
        setUpDataBinding()
        observeViewModel()
    }

    private fun setUpDataBinding() {
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
    }

    private fun setUpAdapter() {
        adapter = ShoppingAdapter(this)
        binding.rvProductList.adapter = adapter
    }

    private fun observeViewModel() {
        viewModel.shoppingUiState.observe(this) { state ->
            when (state) {
                is UIState.Success -> showData(state.data)
                is UIState.Empty -> showData(emptyList())
                is UIState.Error ->
                    showError(
                        state.exception.message ?: getString(R.string.unknown_error),
                    )
            }
        }
    }

    private fun setUpLoadMoreButton() {
        binding.rvProductList.addOnScrollListener(
            object : RecyclerView.OnScrollListener() {
                override fun onScrolled(
                    recyclerView: RecyclerView,
                    dx: Int,
                    dy: Int,
                ) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (!recyclerView.canScrollVertically(1)) {
                        viewModel.updateLoadMoreButtonVisibility(true)
                    } else {
                        if (dy < 0) viewModel.updateLoadMoreButtonVisibility(false)
                    }
                }
            },
        )
    }

    private fun showData(data: List<Product>) {
        adapter.loadData(data)
    }

    private fun showError(errorMessage: String) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.shopping_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        startActivity(CartActivity.createIntent(context = this))
        return true
    }

    override fun onProductClick(productId: Long) {
        startActivity(DetailActivity.createIntent(this, productId))
    }
}
