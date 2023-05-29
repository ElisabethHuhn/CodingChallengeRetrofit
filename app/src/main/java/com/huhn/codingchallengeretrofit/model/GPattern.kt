package com.huhn.codingchallengeretrofit.model

data class GPattern(
    val `data`: List<Data>,
    val total: String
)

data class Data(
    val endDate: String,
    val icon: String,
    val loginRequired: Boolean,
    val name: String,
    val objType: String,
    val startDate: String,
    val url: String,
    val venue: Venue
)

class Venue