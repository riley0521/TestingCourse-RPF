package com.plcoding.testingcourse.util

import com.plcoding.testingcourse.part7.domain.Post
import com.plcoding.testingcourse.part7.domain.Profile
import com.plcoding.testingcourse.part7.domain.User
import java.util.UUID

fun user(): User {
    return User(
        id = UUID.randomUUID().toString(),
        username = "user1"
    )
}

fun post(
    userId: String
): Post {
    return Post(
        id = UUID.randomUUID().toString(),
        userId = userId,
        title = "Test Title",
        body = "Test Body"
    )
}

fun profile(): Profile {
    val user = user()

    return Profile(
        user = user,
        posts = listOf(
            post(user.id),
            post(user.id)
        )
    )
}