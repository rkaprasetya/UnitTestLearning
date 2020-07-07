package com.raka.mockitotutorial.github

data class GithubAccount(
     var login : String = "",
    var id : Int = 0,
     var avatarUrl : String = "",
     var createdAt : String = "",
     var updatedAt : String = "") {

    override fun equals(obj: Any?): Boolean {
        return login == (obj as GithubAccount).login
    }
}