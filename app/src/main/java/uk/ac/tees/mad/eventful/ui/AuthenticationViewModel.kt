package uk.ac.tees.mad.eventful.ui

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class AuthenticationViewModel : ViewModel() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    private val _authState = mutableStateOf<String?>(null)
    val authState: State<String?> = _authState

    fun loginUser(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _authState.value = "home"
                } else {
                    _authState.value = task.exception?.message
                }
            }
    }

    fun registerUser(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    firestore.collection("users")
                        .document(auth.currentUser!!.uid)
                        .set(mapOf("email" to email)).addOnSuccessListener {
                            _authState.value = "home"
                        }.addOnFailureListener {
                            _authState.value = it.message
                        }
                } else {
                    _authState.value = task.exception?.message
                }
            }
    }

    fun resetAuthState() {
        _authState.value = null
    }
}
