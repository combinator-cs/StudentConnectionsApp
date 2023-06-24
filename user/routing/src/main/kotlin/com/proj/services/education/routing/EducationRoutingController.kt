package com.proj.services.education.routing

import com.proj.services.education.domain.service.EducationService
import com.proj.services.user.api.User
import com.proj.services.user.api.UserEducationHistory
import io.github.oshai.kotlinlogging.KLoggable
import kotlinx.coroutines.reactor.mono
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/api")
class EducationRoutingController(private val educationService: EducationService) : KLoggable {

    override val logger = logger()

    @GetMapping("/educations/{userId}")
    fun getEducationRecordsByUserId(@PathVariable userId: String): Mono<List<UserEducationHistory>> = mono {
        educationService.getEducationRecordsByUserId(userId)
    }

    @GetMapping("/education/{publicId}")
    fun getEducationRecordByPublicId(@PathVariable publicId: String): Mono<UserEducationHistory> = mono {
        educationService.getEducationRecordsByPublicId(publicId)
    }

    @PostMapping("/education")
    fun addNewEducation(@RequestBody educationHistory: UserEducationHistory): Mono<UserEducationHistory> = mono {
        educationService.addEducation(educationHistory)
    }

    @PutMapping("/education/{publicId}")
    fun updateEducationRecord(@PathVariable publicId: String, @RequestBody educationHistory: UserEducationHistory): Mono<UserEducationHistory> = mono {
        educationService.updateEducationRecord(publicId, educationHistory)
    }

    @DeleteMapping("/education/{publicId}")
    fun deleteEducationRecord(@PathVariable publicId: String): Mono<Boolean> = mono {
        educationService.deleteEducationRecord(publicId)
    }

    @GetMapping("/education/users")
    fun getUsersForInstitute(@RequestParam(required = false) institutionId: String, @RequestParam(required = false) sortBy: String?,
                             @RequestParam(required = false) sortDirection: String?, @RequestParam(required = false) page: Int?,
                             @RequestParam(required = false) pageSize: Int?): Mono<List<User>> = mono {
        logger.info("Request to get users for institute: {}, sortBy: {}, sortDirection: {}, page: {}, pageSize: {}",
                institutionId, sortBy, sortDirection, page, pageSize)
        educationService.getUsersForInstitute(institutionId, sortBy, sortDirection, page, pageSize)
    }

    @GetMapping("/education/users/{userId}")
    fun getUsersForInstituteByUser(@RequestParam(required = false) institutionId: String, @RequestParam(required = false) sortBy: String?,
                                   @RequestParam(required = false) sortDirection: String?, @RequestParam(required = false) page: Int?,
                                   @RequestParam(required = false) pageSize: Int?, @PathVariable userId: String): Mono<List<User>> = mono {
        logger.info("Request to get users for institute: {}, sortBy: {}, sortDirection: {}, page: {}, pageSize: {} by User: {}",
                institutionId, sortBy, sortDirection, page, pageSize, userId)
        educationService.getUsersForInstituteByUser(institutionId, sortBy, sortDirection, page, pageSize, userId)
    }

    @GetMapping("/education/users/sub-connections/{userId}")
    fun getUsersForInstituteByUserSubConnections(@RequestParam(required = false) institutionId: String, @RequestParam(required = false) sortBy: String?,
                                                 @RequestParam(required = false) sortDirection: String?, @RequestParam(required = false) page: Int?,
                                                 @RequestParam(required = false) pageSize: Int?, @PathVariable userId: String): Mono<List<User>> = mono {
        logger.info("Request to get users for institute: {}, sortBy(with sub connections): {}, sortDirection: {}, page: {}, pageSize: {} by User: {}",
                institutionId, sortBy, sortDirection, page, pageSize, userId)
        educationService.getUsersForInstituteByUserSubConnections(institutionId, sortBy, sortDirection, page, pageSize, userId)
    }

}