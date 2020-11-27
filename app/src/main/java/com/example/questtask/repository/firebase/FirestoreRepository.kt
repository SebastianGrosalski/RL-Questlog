package com.example.questtask.repository.firebase

import android.app.Application
import android.util.Log
import android.widget.Toast
import com.example.questtask.repository.PreferenceProvider
import com.example.questtask.repository.classes.Friend
import com.example.questtask.repository.classes.FriendRequest
import com.example.questtask.repository.room.Quest
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Delay
import kotlinx.coroutines.tasks.await

object FirebaseRepository {
    private val db = Firebase.firestore
    private val mAuth = Firebase.auth
    private val usersRef = db.collection("users")
    private val initialRef = db.collection("initialQuests")
    private val friendRequestsRef = db.collection("friendRequests")
    private val friendCollection = db.collection("friendships")
    var username = ""

     fun addUser(userName: String) {
        val user = hashMapOf(
            "uid" to mAuth.currentUser?.uid,
            "name" to userName,
            "email" to mAuth.currentUser?.email
        )

        db.collection("users")
            .document(mAuth.currentUser!!.uid)
            .set(user)
    }

    fun setPreferredTopics(topicMap: HashMap<String, Boolean>) {
        for (element in topicMap) {
            usersRef.document(mAuth.currentUser!!.uid).collection("Topics").document(element.key)
                .set(
                    element.key to element.value
                )
        }
    }

    fun updatePreferredTopics(topicMap: HashMap<String, Boolean>) {
        for (element in topicMap) {
            usersRef.document(mAuth.currentUser!!.uid).collection("Topics").document(element.key)
                .update(
                    mapOf(
                        "second" to element.value
                    )
                )
        }
    }

    fun updateUserName(username: String) {
        usersRef.document(mAuth.currentUser!!.uid).update(
            mapOf(
                "name" to username
            )
        )
    }

    fun getCurrentUser(): Task<DocumentSnapshot> {
        return usersRef.document(mAuth.currentUser!!.uid)
            .get()
    }

    fun sendFriendRequest(email: String) {
        var receiver: String? = ""
        usersRef.get().addOnSuccessListener { querySnapshot ->
            for (user in querySnapshot) {
                if (user.get("email") == email) {
                    receiver = user.get("uid") as String
                    break
                } else {
                    receiver = null
                }
            }
            if (receiver != null) {
                Log.i("REPOSITORY", "${mAuth.currentUser!!.email}")
                val request = hashMapOf(
                    "sender" to mAuth.currentUser!!.uid,
                    "receiver" to receiver,
                    "status" to "pending",
                    "senderEmail" to mAuth.currentUser!!.email
                )
                friendRequestsRef.document().set(request)
            } else {
                Log.i("REPOSITORY", "RECEIVER WAS NULL")
            }
        }
    }

    /*fun getRequests() : MutableList<FriendRequest>{
        var requestList : MutableList<FriendRequest> = mutableListOf()
        friendRequestsRef.get().addOnSuccessListener { querySnapshot ->
            for(request in querySnapshot){
                if(request.get("receiver") == mAuth.currentUser!!.uid){
                    val tmp = FriendRequest(
                        request.get("receiver") as String,
                        request.get("sender") as String,
                        request.get("status") as String,
                        request.get("senderEmail") as String
                    )
                    requestList.add(tmp)
                }
            }
        }
        return requestList
    }
*/

    fun getReceivedFriendRequests() =
        friendRequestsRef
            .whereEqualTo("receiver", mAuth.currentUser!!.uid)
            .whereEqualTo("status", "pending")
            .get()

    fun getEmailById(id: String): String {
        var email: String = ""
        usersRef.document(id).get().addOnSuccessListener { documentSnapshot ->
            email = documentSnapshot["email"] as String
        }
        return email
    }

    suspend fun getNameById(id: String): String {
        var user = usersRef.document(id).get().await()
        return user.get("name") as String
    }

    suspend fun getIdByEmail(email: String): String {
        var id: String = ""
        val users = usersRef.whereEqualTo("email", email).get().await()
        for (user in users) {
            id = user["uid"] as String
        }
        return id
    }

    suspend fun doesRequestExist(receiver: String) = friendRequestsRef
        .whereEqualTo("receiver", receiver)
        .whereEqualTo("sender", mAuth.currentUser!!.uid)
        .whereEqualTo("status", "pending")
        .get().await().isEmpty

    fun acceptFriendRequest(friendRequest: FriendRequest) {
        friendRequestsRef.whereEqualTo("receiver", friendRequest.receiver)
            .whereEqualTo("sender", friendRequest.sender)
            .whereEqualTo("senderEmail", friendRequest.senderEmail).get().addOnSuccessListener {
                for (document in it) {
                    friendRequestsRef.document(document.id).update("status", "accepted")
                }
            }
    }

    fun declineFriendRequest(friendRequest: FriendRequest) {
        friendRequestsRef.whereEqualTo("receiver", friendRequest.receiver)
            .whereEqualTo("sender", friendRequest.sender)
            .whereEqualTo("senderEmail", friendRequest.senderEmail).get().addOnSuccessListener {
                for (document in it) {
                    friendRequestsRef.document(document.id).update("status", "declined")
                }
            }
    }

    /* Schreib die ganzen Freundschaften in die usercollection, speicher deine id immer an uid1-stelle damit ez abgefragt werden kann*/
    fun createFriendship(sender: String, receiver: String) {
        if (sender == mAuth.currentUser!!.uid) {
            usersRef.document(sender).collection("friendsWith").document(receiver).set(
                hashMapOf(
                    "uid" to receiver
                )
            )
            usersRef.document(receiver).collection("friendsWith").document(sender).set(
                hashMapOf(
                    "uid" to sender
                )
            )
        } else if (receiver == mAuth.currentUser!!.uid) {
            usersRef.document(receiver).collection("friendsWith").document(sender).set(
                hashMapOf(
                    "uid" to sender
                )
            )
            usersRef.document(sender).collection("friendsWith").document(receiver).set(
                hashMapOf(
                    "uid" to receiver
                )
            )
        }
    }

    suspend fun getAllFriends(): MutableList<Friend> {
        val friendList: MutableList<Friend> = mutableListOf()
        val friendIds =
            usersRef.document(mAuth.currentUser!!.uid).collection("friendsWith").get().await()
        for (friendId in friendIds) {
            var userId = friendId.get("uid") as String
            var singleFriend = usersRef.document(userId).get().await().toObject(Friend::class.java)
            if (singleFriend != null) {
                friendList.add(singleFriend)
            }
        }
        return friendList
    }

    fun getInitialQuestRef(): Task<QuerySnapshot> {
        return initialRef.get()
    }

    suspend fun getInitialQuests(): MutableList<Quest> {
        val questList = mutableListOf<Quest>()
        val lists = initialRef.get().await()
        for (quest in lists) {
            questList.add(quest.toObject(Quest::class.java))
        }
        return questList
    }

    fun writeInitialQuestsToUser(questList: MutableList<Quest>) {
        for (quest in questList) {
            usersRef.document(mAuth.currentUser!!.uid).collection("quests").add(
                quest
            )
        }
    }

    fun writeQuestToFirestore(quest: Quest) {
        usersRef.document(mAuth.currentUser!!.uid).collection("quests")
            .document(quest.firestoreId!!).set(
            quest
        )
    }

    fun shareQuest(){
        
    }
}




