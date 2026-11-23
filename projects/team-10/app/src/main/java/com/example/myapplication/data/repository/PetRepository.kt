package com.example.myapplication.data.repository

import com.example.myapplication.data.model.AdoptionRequest
import com.example.myapplication.data.model.Pet
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore

class PetRepository {

    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    fun buscarNomeUsuario(onResult: (String) -> Unit) {
        val uid = auth.currentUser?.uid ?: return
        db.collection("usuarios").document(uid).get()
            .addOnSuccessListener { doc ->
                val nome = doc.getString("nome") ?: "Usuário"
                onResult(nome)
            }
            .addOnFailureListener {
                onResult("Usuário")
            }
    }

    fun buscarDadosDono(donoId: String, onResult: (String, String) -> Unit) {
        if (donoId.isBlank()) {
            onResult("Protetor não informado", "")
            return
        }
        db.collection("usuarios").document(donoId).get()
            .addOnSuccessListener { doc ->
                val nome = doc.getString("nome") ?: "Protetor"
                val telefone = doc.getString("telefone") ?: ""
                onResult(nome, telefone)
            }
            .addOnFailureListener {
                onResult("Não disponível", "")
            }
    }

    fun observarPets(onResult: (List<Pet>) -> Unit) {
        db.collection("pets")
            .whereEqualTo("disponivel", true)
            .addSnapshotListener { snapshot, _ ->
                if (snapshot != null) {
                    val lista = snapshot.documents.mapNotNull { doc ->
                        doc.toObject(Pet::class.java)?.copy(id = doc.id)
                    }
                    onResult(lista)
                }
            }
    }

    fun observarFavoritos(onResult: (Set<String>) -> Unit) {
        val uid = auth.currentUser?.uid ?: return
        db.collection("usuarios").document(uid)
            .addSnapshotListener { snapshot, _ ->
                if (snapshot != null && snapshot.exists()) {
                    @Suppress("UNCHECKED_CAST")
                    val favs = snapshot.get("favoritos") as? List<String> ?: emptyList()
                    onResult(favs.toSet())
                }
            }
    }

    fun alternarFavorito(petId: String, jaEhFavorito: Boolean) {
        val uid = auth.currentUser?.uid ?: return
        val userRef = db.collection("usuarios").document(uid)

        val atualizacao = if (jaEhFavorito) {
            mapOf("favoritos" to FieldValue.arrayRemove(petId))
        } else {
            mapOf("favoritos" to FieldValue.arrayUnion(petId))
        }

        userRef.set(atualizacao, com.google.firebase.firestore.SetOptions.merge())
    }


    fun cadastrarPet(
        pet: Pet,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        val uid = auth.currentUser?.uid ?: ""
        val novoDoc = db.collection("pets").document()
        val petComId = pet.copy(
            id = novoDoc.id,
            donoId = uid,
            disponivel = true
        )

        novoDoc.set(petComId)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { e ->
                onError(e.localizedMessage ?: "Erro ao cadastrar pet.")
            }
    }

    fun solicitarAdocao(
        pet: Pet,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        val user = auth.currentUser
        if (user == null) {
            onError("Você precisa estar logado para adotar.")
            return
        }

        val docRef = db.collection("solicitacoes").document()

        val solicitacao = AdoptionRequest(
            id = docRef.id,
            petId = pet.id,
            petNome = pet.nome,
            petEmoji = pet.emoji,
            adotanteId = user.uid,
            status = "pendente"
        )

        docRef.set(solicitacao)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { e ->
                onError(e.localizedMessage ?: "Erro ao enviar solicitação.")
            }
    }

    fun observarMinhasSolicitacoes(onResult: (List<AdoptionRequest>) -> Unit) {
        val uid = auth.currentUser?.uid ?: return
        db.collection("solicitacoes")
            .whereEqualTo("adotanteId", uid)
            .addSnapshotListener { snapshot, _ ->
                if (snapshot != null) {
                    val lista = snapshot.documents.mapNotNull { doc ->
                        doc.toObject(AdoptionRequest::class.java)?.copy(id = doc.id)
                    }
                    onResult(lista)
                }
            }
    }
}
