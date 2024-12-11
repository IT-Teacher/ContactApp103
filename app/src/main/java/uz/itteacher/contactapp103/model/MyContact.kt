package uz.itteacher.contactapp103.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contacts")
data class MyContact(
    @PrimaryKey(autoGenerate = true)
    val id:Int = 0,
    val name:String,
    @ColumnInfo(name = "last_name")
    val lastName:String,
    @ColumnInfo(name = "phone_number")
    val phoneNumber:String,
    val image:String? = null
)
