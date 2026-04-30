package woowacourse.shopping.feature.productDetail.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import woowacourse.shopping.core.designsystem.theme.LightGreen

@Composable
fun AddCartButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    Button(
        modifier = modifier.fillMaxWidth(),
        onClick = onClick,
        enabled = enabled,
        shape = RectangleShape,
        colors =
            ButtonDefaults.buttonColors(
                containerColor = Color.LightGreen,
                contentColor = Color.White,
            ),
    ) {
        Text(
            text = "장바구니 담기",
            fontSize = 20.sp,
            fontWeight = FontWeight.W700,
        )
    }
}

@Preview
@Composable
fun AddCartButtonPreview() {
    AddCartButton(onClick = {})
}
