package com.example.cinema2.model

class Film (
    val tmdbId: Int = 0,
    val titre: String = "",
    val synopsis: String = "",
    val cheminAffiche: String = "",
    val noteTmdb: Double = 0.0,
    val dateSortie: String = "",
    // Données personnelles de l'utilisateur
    var statut: String = "a_voir",
    var notePersonnelle: Float = 0f,
    var avisPersonnel: String = "",
    var dateVisionnage: String = "",
    val dateAjout: Long = System.currentTimeMillis()

)