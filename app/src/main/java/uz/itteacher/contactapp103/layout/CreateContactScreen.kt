package uz.itteacher.contactapp103.layout

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Environment
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.core.text.isDigitsOnly
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import uz.itteacher.contactapp103.db.AppDataBase
import uz.itteacher.contactapp103.model.MyContact
import uz.itteacher.contactapp103.R
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateContactScreen(navController: NavController, appDataBase: AppDataBase, id: Int) {

    var name by remember { mutableStateOf("") }
    var surname by remember { mutableStateOf("") }
    var phone_num by remember { mutableStateOf("") }
    var check by remember { mutableStateOf(false) }

    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val context = LocalContext.current

    fun saveImageToAppDirectory(context: Context, uri: Uri): Uri? {
        val contentResolver = context.contentResolver
        val inputStream = contentResolver.openInputStream(uri) ?: return null
        val appDir = File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "MyAppImages")
        if (!appDir.exists()) {
            appDir.mkdirs()
        }
        val file = File(appDir, "image_${System.currentTimeMillis()}.jpg")
        return try {
            val outputStream: OutputStream = FileOutputStream(file)
            inputStream.copyTo(outputStream)
            outputStream.close()
            inputStream.close()
            Uri.fromFile(file)
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }

    val galleryLauncher = rememberLauncherForActivityResult(StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            result.data?.data?.let { uri ->
                saveImageToAppDirectory(context, uri)?.let { savedUri ->
                    imageUri = savedUri
                }
            }
        }
    }


    if(id != 0 && !check){
        val contact = appDataBase.getMyContactDao().getContactById(id)
        name = contact.name
        surname = contact.lastName
        phone_num = contact.phoneNumber
//        imageUri = Uri.parse(contact.image)
        check = true
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(15.dp)
    ) {
        Row(Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically){
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = {
                    navController.navigate("main")
                }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back"
                    )
                }
                Text("Kontakt qo'shish", fontSize = 20.sp)
            }
            IconButton(onClick = {
                if(IsValidPhone(name, surname, phone_num)){
                    if (check){
                        appDataBase.getMyContactDao().updateContact(
                            id = id,
                            name = name,
                            lastName = surname,
                            phoneNumber = phone_num,
                            image = imageUri.toString()
                        )

                    }else {
                        appDataBase.getMyContactDao().addContact(
                            myContact = MyContact(
                                name = name,
                                lastName = surname,
                                phoneNumber = phone_num,
                                image = imageUri.toString()
                            )
                        )

                    }
                    navController.navigate("main")
                }

            }) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Back"
                )
            }
        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
            if(imageUri != null){
                AsyncImage(
                    model = imageUri.toString(),
                    contentDescription = null,
                )
            }else{
                Image(painter = painterResource(R.drawable.person), contentDescription = "person", Modifier.size(140.dp).padding(end = 20.dp))
            }

            IconButton(onClick = {
                val galleryIntent = Intent(Intent.ACTION_PICK).apply {
                    type = "image/*"
                }
                galleryLauncher.launch(galleryIntent)


            }, modifier = Modifier.padding(top = 75.dp).size(60.dp)) {
                Image(painter = painterResource(R.drawable.gallery), contentDescription = "gallery", Modifier.size(30.dp))
            }

            IconButton(onClick = {}, modifier = Modifier.padding(top = 75.dp).size(60.dp)) {
                Image(painter = painterResource(R.drawable.camera), contentDescription = "camera", Modifier.size(30.dp))
            }
        }
        Text(
            text = "Ism",
            fontSize = 20.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .background(
                    color = Color(0xFFF5F5F5),
                    shape = RoundedCornerShape(8.dp)
                )
                .border(
                    width = 1.dp,
                    color = Color.Gray,
                    shape = RoundedCornerShape(8.dp)
                )
        ) {
            OutlinedTextField(
                value = name,
                onValueChange = {
                    name = it
                },
                maxLines = 1,
                singleLine = true,
                shape = RoundedCornerShape(8.dp),
                placeholder = {
                    Text(text = "Ism kiriting")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .background(
                        color = Color.Transparent,
                        shape = RoundedCornerShape(8.dp)
                    ),
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                )
            )
        }
        Spacer(Modifier.height(20.dp))


        Text(
            text = "Familiya",
            fontSize = 20.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .background(
                    color = Color(0xFFF5F5F5),
                    shape = RoundedCornerShape(8.dp)
                )
                .border(
                    width = 1.dp,
                    color = Color.Gray,
                    shape = RoundedCornerShape(8.dp)
                )
        ) {
            OutlinedTextField(
                value = surname,
                onValueChange = {
                    surname = it
                },
                maxLines = 1,
                singleLine = true,
                shape = RoundedCornerShape(8.dp),
                placeholder = {
                    Text(text = "Familiya kiriting")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .background(
                        color = Color.Transparent,
                        shape = RoundedCornerShape(8.dp)
                    ),
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                )
            )
        }
        Spacer(Modifier.height(20.dp))


        Text(
            text = "Telefon raqam",
            fontSize = 20.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .background(
                    color = Color(0xFFF5F5F5),
                    shape = RoundedCornerShape(8.dp)
                )
                .border(
                    width = 1.dp,
                    color = Color.Gray,
                    shape = RoundedCornerShape(8.dp)
                )
        ) {
            OutlinedTextField(
                value = phone_num,
                onValueChange = {
                    phone_num = it
                },
                maxLines = 1,
                singleLine = true,
                shape = RoundedCornerShape(8.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                placeholder = {
                    Text(text = "+998 __ ___ __ __")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .background(
                        color = Color.Transparent,
                        shape = RoundedCornerShape(8.dp)
                    ),
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                )
            )
        }
    }
}

fun IsValidPhone(name: String, surname: String, phone: String): Boolean {
    if(name.isEmpty() || surname.isEmpty() || phone.isEmpty()){
        return false
    }
    if(phone.length != 13) {
        return false
    }
    if(!phone.startsWith("+998")){
        return false
    }
    if(!phone.substring(1).isDigitsOnly()){
        return false
    }
    return true
}




