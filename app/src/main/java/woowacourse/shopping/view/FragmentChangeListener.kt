package woowacourse.shopping.view

import androidx.fragment.app.Fragment

interface FragmentChangeListener {
    fun changeFragment(nextFragment: Fragment)

    fun popFragment()
}
