package com.example.project1.model



data class ProductModel(
    val title:String,
    val thumbnail:String,
    val price : Double,
    val category : String,
    val rating: String,
    var isFavorite: Boolean = false
)
