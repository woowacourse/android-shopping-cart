package woowacourse.shopping.ui.common.component.cartcontrol

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import woowacourse.shopping.R
import woowacourse.shopping.ui.theme.ShoppingColors

@Composable
fun AddToCartButton(
    isEnabled: Boolean,
    modifier: Modifier = Modifier,
    onAddToCart: () -> Unit,
) {
    Button(
        onClick = onAddToCart,
        enabled = isEnabled,
        modifier = modifier.size(48.dp),
        shape = CircleShape,
        contentPadding = PaddingValues(0.dp),
        colors =
            ButtonDefaults.buttonColors(
                containerColor = Color.White,
                contentColor = ShoppingColors.Gray4,
            ),
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = stringResource(R.string.cart_add_button),
            tint = ShoppingColors.Gray4,
            modifier = Modifier.size(40.dp),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun AddToCartButtonPreview() {
    AddToCartButton(
        isEnabled = true,
        onAddToCart = {},
    )
}
