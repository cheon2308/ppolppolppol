package com.ppol.onlineserver.controller;

import java.util.Set;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ppol.onlineserver.dto.response.CharacterDto;
import com.ppol.onlineserver.service.CharacterUpdateService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/online")
@RequiredArgsConstructor
public class OnlineController {

	// service
	private final CharacterUpdateService characterUpdateService;

	@GetMapping("/{groupId}")
	public ResponseEntity<?> readGroupCharacters(@PathVariable Long groupId) {

		Set<CharacterDto> returnObject = characterUpdateService.getCharacterSet(groupId);

		return ResponseEntity.ok(returnObject);
	}
}
