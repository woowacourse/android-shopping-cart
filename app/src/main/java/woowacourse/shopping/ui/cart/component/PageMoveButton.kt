package woowacourse.shopping.ui.cart.component

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import woowacourse.shopping.ui.theme.cartPageButtonColor
import woowacourse.shopping.ui.theme.topAppBarColor

@Composable
fun PageMoveButton(
    onClick: () -> Unit,
    shape: RoundedCornerShape,
    enabled: Boolean,
    modifier: Modifier = Modifier,
    image: @Composable () -> Unit = {},
) {
    Button(
        onClick = onClick,
        shape = shape,
        colors =
            ButtonDefaults.buttonColors(
                containerColor = cartPageButtonColor,
                disabledContainerColor = topAppBarColor,
            ),
        enabled = enabled,
        modifier = modifier,
    ) {
        image()
    }
}

@Preview
@Composable
private fun PageMoveButtonPreview() {
    PageMoveButton(
        onClick = {},
        shape =
            RoundedCornerShape(
                topStart = 0.dp,
                topEnd = 4.dp,
                bottomStart = 0.dp,
                bottomEnd = 4.dp,
            ),
        enabled = true,
        image = {},
    )
}
