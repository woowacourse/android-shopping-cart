package woowacourse.shopping.ui.productdetail.component

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
import woowacourse.shopping.ui.theme.buttonColor

@Composable
fun ActionButton(
    onClick: () -> Unit,
    text: String,
    modifier: Modifier = Modifier,
) {
    Button(
        modifier = modifier,
        onClick = onClick,
        shape = RectangleShape,
        colors =
            ButtonDefaults.buttonColors(
                containerColor = buttonColor,
            ),
    ) {
        Text(
            text = text,
            fontWeight = FontWeight.W700,
            fontSize = 20.sp,
            color = Color.White,
        )
    }
}

@Preview
@Composable
private fun ActionButtonPreview() {
    ActionButton(
        onClick = {},
        text = "",
    )
}
