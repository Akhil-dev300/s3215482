package uk.ac.tees.mad.eventful.ui

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore

class ProfileViewModel : ViewModel() {
    private val firestore = FirebaseFirestore.getInstance()

    private val _userData = mutableStateOf<UserProfile?>(null)
    val userData: State<UserProfile?> = _userData

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    fun fetchUserProfile(userId: String) {
        _isLoading.value = true
        firestore.collection("users").document(userId).get()
            .addOnSuccessListener { document ->
                val user = document.toObject(UserProfile::class.java)
                Log.d("ProfileViewModel", "User psrofile fetched: $user")
                _userData.value = user
                _isLoading.value = false
            }
            .addOnFailureListener {
                _isLoading.value = false
                Log.e("ProfileViewModel", "Error fetching user profile: ${it.message}")
            }
    }

}

data class UserProfile(
    val name: String = "",
    val email: String = "",
    val profileImage: String = "https://robohash.org/user"
)
