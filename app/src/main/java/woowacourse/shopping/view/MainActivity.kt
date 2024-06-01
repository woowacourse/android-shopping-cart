package woowacourse.shopping.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ActivityMainBinding
import woowacourse.shopping.view.products.ProductsListFragment

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setFragment()
    }

    private fun setFragment() {
        supportFragmentManager.beginTransaction().replace(
            R.id.fragment_container,
            ProductsListFragment(),
        ).addToBackStack("mainFragment").commit()
    }
}
