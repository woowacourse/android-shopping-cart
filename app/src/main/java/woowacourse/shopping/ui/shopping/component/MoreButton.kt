package woowacourse.shopping.ui.shopping.component

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import woowacourse.shopping.R

@Composable
fun MoreButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    Button(
        modifier = modifier.padding(10.dp),
        onClick = onClick,
        colors =
            ButtonDefaults.buttonColors(
                containerColor = Color(0xFF04C09E),
                contentColor = Color.White,
            ),
    ) {
        Text(stringResource(R.string.more_button))
    }
}

@Preview
@Composable
private fun MoreButtonPreview() {
    MoreButton(onClick = {})
}
