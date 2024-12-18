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

    @Query("SELECT * FROM contacts WHERE id = :id")
    fun getContactById(id: Int): MyContact

    @Query("UPDATE contacts SET name = :name, last_name = :lastName, phone_number = :phoneNumber, image = :image WHERE id = :id")
    fun updateContact(id: Int, name: String, lastName: String, phoneNumber: String, image: String)


}