package woowacourse.shopping.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ActivityMainBinding
import woowacourse.shopping.view.products.ProductsListFragment

class MainActivity : AppCompatActivity(), MainFragmentListener {
    private val mainViewModel: MainViewModel by viewModels()

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        supportFragmentManager.beginTransaction().replace(
            R.id.fragment_container,
            ProductsListFragment(),
        ).commit()
    }

    override fun changeFragment(nextFragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragment_container, nextFragment)
            .addToBackStack(null)
            .commit()
    }

    override fun popFragment() {
        supportFragmentManager.popBackStack()
    }

    override fun observeProductList(products: (Map<Long, Int>) -> Unit) {
        mainViewModel.updateProductEvent.observe(this) {
            products(it)
        }
    }

    override fun saveUpdateProduct(productId: Long, count: Int) {
        mainViewModel.saveUpdate(mapOf(productId to count))
    }
}
