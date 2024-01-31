package com.example.moviesdbapplication.domain.model

data class UserInfo(
    val nombre: String,
    val email: String,
    val telefono: String
) : BaseObject(){
    fun validaNombre(): Boolean {
        return nombre.isNotEmpty()
    }

    fun validaEmail(): Boolean {
        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
        return email.matches(emailPattern.toRegex())
    }

    fun validaTelefono(): Boolean {
        return telefono.matches("\\d{10}".toRegex())
    }

    fun isValidate(): Boolean {
        return validaNombre() && validaEmail() && validaTelefono()
    }

    fun toHashMap(): Map<String, Any?> {
        return mapOf(
            "nombre" to nombre,
            "email" to email,
            "telefono" to telefono
        )
    }
}