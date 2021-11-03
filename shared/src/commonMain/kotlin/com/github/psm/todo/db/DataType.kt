package com.github.psm.todo.db

data class DataType(
    val int: Int = 0,
    val long: Long = 0L,
    val float: Float = 0f,
    val double: Double = 0.0,
    val list: List<String> = listOf(),
    val arrayList: ArrayList<String> = arrayListOf(),
    val mutableList: MutableList<String> = mutableListOf(),
    val collection: Collection<String> = listOf(),
    val map: Map<String, String> = mapOf(),
    val mutableMap: MutableMap<String, String> = mutableMapOf()
)