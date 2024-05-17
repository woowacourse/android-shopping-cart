package woowacourse.shopping.base

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

object FragmentReplacer {
    fun replaceFragment(
        containerId: Int,
        fragment: Fragment,
        parentFragmentManager: FragmentManager,
    ) {
        parentFragmentManager.beginTransaction().apply {
            replace(containerId, fragment)
            addToBackStack(null)
            commit()
        }
    }
}
