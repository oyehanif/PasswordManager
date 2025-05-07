package com.hanif.passwordmanager.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hanif.passwordmanager.R
import com.hanif.passwordmanager.model.local.PasswordEntity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordBottomSheet(
    passwordItem: PasswordEntity?,
    isEditMode: Boolean,
    onEditModeChange: (Boolean) -> Unit,
    onAdd: (String, String, String) -> Unit,
    onUpdate: (PasswordEntity) -> Unit,
    onDelete: (Int) -> Unit,
    onDismiss: () -> Unit
) {
    val isAddMode = passwordItem == null
    var accountName by rememberSaveable { mutableStateOf(passwordItem?.serviceName ?: "") }
    var username by rememberSaveable { mutableStateOf(passwordItem?.username ?: "") }
    var password by rememberSaveable { mutableStateOf(passwordItem?.encryptedPassword ?: "") }
    var passwordVisible by rememberSaveable { mutableStateOf(false) }
    var errorMessage by rememberSaveable { mutableStateOf<String?>(null) }

    // Use a ScrollableColumn to ensure content can be scrolled if it exceeds available space
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 10.dp, max = 650.dp) // Set maximum height
            .verticalScroll(rememberScrollState()) // Make content scrollable
            .padding(horizontal = 24.dp, vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
//        // Add a handle at the top for better UX with bottom sheets
//        Box(
//            modifier = Modifier
//                .width(40.dp)
//                .height(4.dp)
//                .background(
//                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.4f),
//                    shape = RoundedCornerShape(2.dp)
//                )
//                .align(Alignment.CenterHorizontally)
//                .padding(bottom = 16.dp)
//        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = stringResource(if (isAddMode) R.string.add_new_account else R.string.account_details),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(bottom = 24.dp)
                .align(Alignment.Start)
        )

        // Account Type Field
        InputField(
            label = stringResource(R.string.account_type),
            value = accountName,
            onValueChange = { accountName = it },
            isEditMode = isEditMode || isAddMode,
            modifier = Modifier.padding(bottom = 16.dp),
            isError = errorMessage != null && accountName.isBlank(),
            placeholder = if (isAddMode) stringResource(R.string.account_name) else null,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        )

        // Username/Email Field
        InputField(
            label = stringResource(R.string.username_email),
            value = username,
            onValueChange = { username = it },
            isEditMode = isEditMode || isAddMode,
            modifier = Modifier.padding(bottom = 16.dp),
            isError = errorMessage != null && username.isBlank(),
            placeholder = if (isAddMode) stringResource(R.string.username_email) else null,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        )

        // Password Field
        PasswordInputField(
            label = stringResource(R.string.password),
            value = password,
            onValueChange = { password = it },
            isEditMode = isEditMode || isAddMode,
            passwordVisible = passwordVisible,
            onPasswordVisibilityChange = { passwordVisible = it },
            modifier = Modifier.padding(bottom = 24.dp),
            isError = errorMessage != null && password.isBlank(),
            placeholder = if (isAddMode) stringResource(R.string.password) else null
        )

        errorMessage?.let {
            Text(
                text = it,
                color = MaterialTheme.colorScheme.error,
                fontSize = 14.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )
        }

        // Action Buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            if (isAddMode) {
                Button(
                    onClick = {
                        if (accountName.isBlank() || username.isBlank() || password.isBlank()) {
                            errorMessage = "All fields are required"
                        } else {
                            errorMessage = null
                            onAdd(accountName, username, password)
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    )
                ) {
                    Text(
                        text = stringResource(R.string.add_new_account),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            } else {
                // Edit/Update Button
                Button(
                    onClick = {
                        if (isEditMode) {
                            if (accountName.isBlank() || username.isBlank() || password.isBlank()) {
                                errorMessage = "All fields are required"
                            } else {
                                errorMessage = null
                                val updatedItem = PasswordEntity(
                                    id = passwordItem!!.id,
                                    serviceName = accountName,
                                    username = username,
                                    encryptedPassword = password
                                )
                                onUpdate(updatedItem)
                            }
                        } else {
                            onEditModeChange(true)
                        }
                    },
                    modifier = Modifier
                        .weight(1f)
                        .height(50.dp)
                        .padding(end = 8.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    )
                ) {
                    Text(
                        text = if (isEditMode) stringResource(R.string.update) else stringResource(R.string.edit),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                }

                // Delete Button
                Button(
                    onClick = { onDelete(passwordItem!!.id) },
                    modifier = Modifier
                        .weight(1f)
                        .height(50.dp)
                        .padding(start = 8.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.error,
                        contentColor = MaterialTheme.colorScheme.onError
                    )
                ) {
                    Text(
                        text = stringResource(R.string.delete),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }

        // Add extra space at the bottom to ensure content isn't cut off by system navigation
        Spacer(modifier = Modifier.height(32.dp))
    }
}
// InputField and PasswordInputField remain unchanged
@Composable
private fun InputField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    isEditMode: Boolean,
    modifier: Modifier = Modifier,
    isError: Boolean = false,
    placeholder: String? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
) {
    Text(
        text = label,
        fontSize = 14.sp,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 4.dp)
    )

    if (isEditMode) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = modifier.fillMaxWidth(),
            singleLine = true,
            shape = RoundedCornerShape(8.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.outline,
                unfocusedBorderColor = MaterialTheme.colorScheme.outlineVariant
            ),
            placeholder = placeholder?.let { { Text(it, color = MaterialTheme.colorScheme.onSurfaceVariant) } },
            isError = isError,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions
        )
    } else {
        Text(
            text = value,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            modifier = modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun PasswordInputField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    isEditMode: Boolean,
    passwordVisible: Boolean,
    onPasswordVisibilityChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    isError: Boolean = false,
    placeholder: String? = null
) {
    Text(
        text = label,
        fontSize = 14.sp,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 4.dp)
    )

    if (isEditMode) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = modifier.fillMaxWidth(),
            singleLine = true,
            shape = RoundedCornerShape(8.dp),
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
                IconButton(onClick = { onPasswordVisibilityChange(!passwordVisible) }) {
                    Icon(
                        painter = painterResource(
                            if (passwordVisible) R.drawable.visibility_off else R.drawable.visibility
                        ),
                        contentDescription = stringResource(
                            if (passwordVisible) R.string.hide_password else R.string.show_password
                        )
                    )
                }
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.outline,
                unfocusedBorderColor = MaterialTheme.colorScheme.outlineVariant
            ),
            placeholder = placeholder?.let { { Text(it, color = MaterialTheme.colorScheme.onSurfaceVariant) } },
            isError = isError
        )
    } else {
        Row(
            modifier = modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = if (passwordVisible) value else stringResource(R.string.masked_password),
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                letterSpacing = 1.sp,
                modifier = Modifier.weight(1f)
            )
            IconButton(onClick = { onPasswordVisibilityChange(!passwordVisible) }) {
                Icon(
                    painter = painterResource(
                        if (passwordVisible) R.drawable.visibility_off else R.drawable.visibility
                    ),
                    contentDescription = stringResource(
                        if (passwordVisible) R.string.hide_password else R.string.show_password
                    ),
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}