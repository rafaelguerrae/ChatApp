package guerra.chatbotapp.viewmodel

import com.google.firebase.firestore.FirebaseFirestore
import guerra.chatbotapp.data.Room
import kotlinx.coroutines.tasks.await
import guerra.chatbotapp.data.Result

class RoomRepository(private val firestore: FirebaseFirestore) {

    suspend fun createRoom(name: String): Result<Unit> = try {
        val room = Room(name = name)
        firestore.collection("rooms").add(room).await()
        Result.Success(Unit)
    } catch (e: Exception) {
        Result.Error(e)
    }

    suspend fun getRooms(): Result<List<Room>> = try {
        val querySnapshot = firestore.collection("rooms").get().await()
        val rooms = querySnapshot.documents.map { document ->
            document.toObject(Room::class.java)!!.copy(id = document.id)
        }
        Result.Success(rooms)
    } catch (e: Exception) {
        Result.Error(e)
    }
}