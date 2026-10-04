package br.com.travelu.feature.signup

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.travelu.core.widget.TraveluCircleImageButton
import br.com.travelu.core.widget.TraveluSpacer

@Composable
fun SignUpScreen() {
    Scaffold {
        var passwordVisibility by remember { mutableStateOf(false) }
        var confirmPasswordVisibility by remember { mutableStateOf(false) }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            TraveluCircleImageButton(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back arrow",
                modifier = Modifier,
                onClick = {}
            )
            TraveluSpacer(20.dp)
            Text(
                "Create account",
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                textAlign = TextAlign.Center,
                fontSize = 32.sp,
            )
            Text(
                "Please fill the details to continue",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )

            TraveluSpacer(26.dp)

            OutlinedTextField(
                "Full Name", onValueChange = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(
                        MaterialTheme.colorScheme.surfaceVariant.copy(0.2f)
                    ),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.surfaceVariant.copy(0.2f),
                    unfocusedBorderColor = MaterialTheme.colorScheme.surfaceVariant.copy(0.2f)
                ),
                placeholder = {
                    Text(
                        "Full Name",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onBackground.copy(0.7f)
                    )
                }
            )
            TraveluSpacer(16.dp)

            OutlinedTextField(
                "Email Address", onValueChange = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(
                        MaterialTheme.colorScheme.surfaceVariant.copy(0.2f)
                    ),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.surfaceVariant.copy(0.2f),
                    unfocusedBorderColor = MaterialTheme.colorScheme.surfaceVariant.copy(0.2f)
                ),
                placeholder = {
                    Text(
                        "Email Address",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onBackground.copy(0.7f)
                    )
                }
            )
            TraveluSpacer(16.dp)

            OutlinedTextField(
                "Password", onValueChange = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(
                        MaterialTheme.colorScheme.surfaceVariant.copy(0.2f)
                    ),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.surfaceVariant.copy(0.2f),
                    unfocusedBorderColor = MaterialTheme.colorScheme.surfaceVariant.copy(0.2f)
                ),
                placeholder = {
                    Text(
                        "Password",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onBackground.copy(0.7f)
                    )
                },
                visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    Image(
                        imageVector = if (!passwordVisibility) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                        contentDescription = "Password Icon",
                        modifier = Modifier
                            .size(48.dp)
                            .padding(12.dp)
                            .clickable {
                                passwordVisibility = !passwordVisibility
                            }
                    )
                }
            )
            TraveluSpacer(16.dp)

            OutlinedTextField(
                "Confirm Password", onValueChange = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(
                        MaterialTheme.colorScheme.surfaceVariant.copy(0.2f)
                    ),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.surfaceVariant.copy(0.2f),
                    unfocusedBorderColor = MaterialTheme.colorScheme.surfaceVariant.copy(0.2f)
                ),
                placeholder = {
                    Text(
                        "Confirm Password",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onBackground.copy(0.7f)
                    )
                },
                visualTransformation = if (confirmPasswordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    Image(
                        imageVector = if (!confirmPasswordVisibility) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                        contentDescription = "Confirm Password Icon",
                        modifier = Modifier
                            .size(48.dp)
                            .padding(12.dp)
                            .clickable {
                                confirmPasswordVisibility = !confirmPasswordVisibility
                            }
                    )
                }
            )

            TraveluSpacer(16.dp)

            Button(
                onClick = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .clip(RoundedCornerShape(6.dp)),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Sign Up", modifier = Modifier.padding(vertical = 8.dp))
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Already have an account?",
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
                TextButton(onClick = {}) {
                    Text("Sign in", color = MaterialTheme.colorScheme.primary)
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun SignUpScreenPreview() {
    SignUpScreen()
}
