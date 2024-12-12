package uz.itteacher.contactapp103.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import uz.itteacher.contactapp103.model.MyContact

@Dao
interface MyContactDAO {
    @Query("SELECT * FROM contacts")
    fun getAllContacts(): List<MyContact>

    @Insert
    fun addContact(myContact: MyContact)

    @Query("DELETE FROM contacts WHERE id = :id")
    fun deleteContact(id: Int)
}