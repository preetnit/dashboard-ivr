/*
 * This file is a part of the source code and related artifacts for
 * Recommendations Service.
 *
 * Copyright © 2017 [24]7.ai, Inc.
 * All rights reserved.
 */

package com.design.ivrservice.controller

import com.design.ivrservice.entity.Health
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(value = ["/v1/health"])
class HealthController {
    @GetMapping
    @Operation(
            summary = "Check server health",
            responses = [ApiResponse(responseCode = "200", description = "Server is working")]
    )
    fun healthCheck(): ResponseEntity<Health> {
        return ResponseEntity.ok(Health("ok"))
    }
}
