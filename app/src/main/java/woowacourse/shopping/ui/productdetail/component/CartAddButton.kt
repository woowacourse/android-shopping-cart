package woowacourse.shopping.ui.productdetail.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import woowacourse.shopping.R
import woowacourse.shopping.ui.ShoppingTypography
import woowacourse.shopping.ui.theme.ShoppingColors

@Composable
fun CartAddButton(
    isEnabled: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    Box(
        modifier =
            modifier
                .fillMaxWidth()
                .background(ShoppingColors.BrandGreen)
                .clickable(enabled = isEnabled, onClick = onClick),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = stringResource(R.string.cart_add_button),
            style = ShoppingTypography.actionLabel,
            color = Color.White,
            modifier = Modifier.padding(12.dp),
        )
    }
}

@Composable
@Preview
private fun CartAddButtonPreview() {
    CartAddButton(isEnabled = true, onClick = {})
}
