package mainPackage.model

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.internal.synchronized

class DatabaseMockup {

    var usersdbm = MutableLiveData<List<User>>()

    fun getUserList(): List<User>? { return this.usersdbm.value }

    companion object{
        @Volatile private var instance: DatabaseMockup? = null

        @OptIn(InternalCoroutinesApi::class)
        fun getInstance()= instance?: synchronized(this){
            instance?:DatabaseMockup().also { instance=it }
        }
    }
}