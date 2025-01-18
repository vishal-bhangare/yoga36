package com.example.yoga36
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class YogaPose(
    val sanskrit_name: String,
    val english_name: String,
    val description: String,
    val time: String,
    val image: String,
    val benefits: String,
    val variations: String,
    val category: String,
    val target: String,
    val steps: String
) : Parcelable
