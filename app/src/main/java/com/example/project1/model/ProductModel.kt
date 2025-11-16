package com.example.project1.model

import java.io.Serializable


data class ProductModel(
    val title:String,
    val thumbnail:String,
    val price : Double,
    val category : String,
    val rating: String,
    var isFavorite: Boolean = false,
    val discountPercentage: Double,
    val stock: Int,
    val brand: String,
    val sku: String,
    val weight: Int,
    val warrantyInformation: String,
    val shippingInformation: String,
    val availabilityStatus: String,
    val returnPolicy: String,
    val minimumOrderQuantity: Int,
    val images: List<String>,
    val description: String,
    val dimensions: Dimensions,
    val review: Review,
    val metaData: MetaData
): Serializable


data class Dimensions(
    val width: Double,
    val height: Double,
    val depth: Double
): Serializable


data class Review(
    val rating: Int,
    val comment: String,
    val date: String,
    val reviewerName: String,
    val reviewerEmail: String
): Serializable

data class MetaData(
    val createdAt: String,
    val updatedAt: String,
    val barcode: String,
    val qrCode: String
): Serializable

