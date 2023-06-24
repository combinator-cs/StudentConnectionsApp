package com.proj.services.user.routing

import com.proj.services.user.api.Connection
import com.proj.services.user.api.User
import com.proj.services.user.domain.service.UserService
import kotlinx.coroutines.reactor.mono
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/v1/users")
class UserRoutingController(private val userService: UserService) {

    @GetMapping("{userId}")
    fun getByUserId(
            @PathVariable userId: String,
    ): Mono<User> = mono {
        userService.getByUserId(userId)
    }

    @PostMapping("/add-new-user")
    fun addNewUser(@RequestBody user: User): Mono<User> = mono {
        userService.addNewUser(user)
    }

    @PostMapping("/add-user-connection")
    fun addUserConnection(@RequestBody connection: Connection): Mono<Connection> = mono {
        userService.addUserConnection(connection)
    }
}