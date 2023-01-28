package mainPackage.model

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.Mockito

internal class RepositoryMockupTest {

    val repo = RepositoryMockup()

    @Test
    fun testSuccessfulUserWrite() {
        // mock FirebaseFirestore and DocumentReference objects
        val mockDatabase = Mockito.mock(FirebaseFirestore::class.java)
        val mockCollectionRef = Mockito.mock(CollectionReference::class.java)
        val mockRef = Mockito.mock(DocumentReference::class.java)

        // configure mock objects to return the mock DocumentReference when calling collection() and document()
        Mockito.`when`(mockDatabase.collection("Users")).thenReturn(mockCollectionRef)
        Mockito.`when`(mockCollectionRef.document("test@example.com")).thenReturn(mockRef)

        // call the method and check that the set() method is called on the mock DocumentReference
        val task = repo.writeNewUser("password", "test@example.com", true)
        Mockito.verify(mockRef).set(Mockito.any<Map<String, Any>>())
    }
}