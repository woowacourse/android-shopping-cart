package woowacourse.shopping.feature.productDetail.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import woowacourse.shopping.R
import woowacourse.shopping.core.designsystem.component.BasicTopAppBar

@Composable
fun ProductDetailTopAppBar(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    BasicTopAppBar(
        modifier = modifier
    ) {
        Spacer(Modifier.weight(1f))
        Icon(
            imageVector = ImageVector.vectorResource(R.drawable.icon_close_16),
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier.clickable(onClick = onClick)
        )
    }
}

@Preview
@Composable
private fun ProductDetailTopAppBarPreview() {
    ProductDetailTopAppBar(onClick = {})
}
