package woowacourse.shopping.ui.common

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import woowacourse.shopping.R

@Composable
fun AddCartButton(
    onIncrement: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Surface(
        onClick = onIncrement,
        shape = CircleShape,
        color = Color.White,
        modifier = modifier.size(40.dp),
    ) {
        Icon(
            painter = painterResource(R.drawable.add_icon),
            contentDescription = stringResource(R.string.cart_add_description),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun AddCartButtonPreview() {
    AddCartButton(
        onIncrement = {},
    )
}
