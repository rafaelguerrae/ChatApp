package guerra.chatbotapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import guerra.chatbotapp.data.Injection
import guerra.chatbotapp.data.Result
import guerra.chatbotapp.data.Room
import kotlinx.coroutines.launch

class RoomViewModel : ViewModel() {

    private val _rooms = MutableLiveData<List<Room>>()
    val rooms: LiveData<List<Room>> get() = _rooms
    private val roomRepository: RoomRepository
    init {
        roomRepository = RoomRepository(Injection.instance())
        loadRooms()
    }

    fun createRoom(name: String, onComplete: ()-> Unit) {
        viewModelScope.launch {
            roomRepository.createRoom(name)
            onComplete()
        }
    }

    fun loadRooms() {
        viewModelScope.launch {
            when (val result = roomRepository.getRooms()) {
                is Result.Success -> _rooms.value = result.data
                is Result.Error -> {

                }
            }
        }
    }

}