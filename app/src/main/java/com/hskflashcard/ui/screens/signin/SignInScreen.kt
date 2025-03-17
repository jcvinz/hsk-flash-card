package com.hskflashcard.ui.screens.signin

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.hskflashcard.R
import com.hskflashcard.ui.theme.HSKFlashCardTheme

@Composable
fun SignInScreen(
    navController: NavHostController,
    viewModel: SignInViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    SignInScreenComponent {
        viewModel.signInWithGoogle(context)
    }
}

@Composable
fun SignInScreenComponent(
    modifier: Modifier = Modifier,
    onGoogleSignInClick: () -> Unit
) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        Image(
            modifier = Modifier.matchParentSize(),
            painter = painterResource(R.drawable.sign_in_screen_bg),
            contentScale = ContentScale.FillHeight,
            contentDescription = null
        )
        Box(
            modifier = Modifier
                .matchParentSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0x99141414),
                            Color(0xFF141414)
                        )
                    )
                )
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(bottom = 144.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier.padding(horizontal = 24.dp),
                text = "HSK Made Easy with Flash Card",
                style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold),
                color = Color.White,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                modifier = Modifier.padding(start = 24.dp, end = 24.dp),
                text = "Boost your learning with AI-powered flashcards.",
                style = MaterialTheme.typography.titleLarge,
                color = Color.White,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(32.dp))
            Image(
                modifier = Modifier
                    .padding(start = 24.dp, end = 24.dp)
                    .clickable {
                        onGoogleSignInClick()
                    },
                imageVector = ImageVector.vectorResource(R.drawable.android_light_rd_si),
                contentDescription = null
            )
        }
    }
}

@Preview(widthDp = 412, heightDp = 917)
@Composable
fun SignInScreenPreview() {
    HSKFlashCardTheme {
        SignInScreenComponent { }
    }
}