package com.ppol.onlineserver.controller;

import java.util.Map;
import java.util.Set;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ppol.onlineserver.dto.response.CharacterDto;
import com.ppol.onlineserver.service.CharacterReadService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/online")
@RequiredArgsConstructor
public class OnlineController {

	// service
	private final CharacterReadService characterReadService;

	@GetMapping("/{groupId}")
	public ResponseEntity<?> readGroupCharacters(@PathVariable String groupId) {

		Map<String, Set<CharacterDto>> returnObject = characterReadService.readGroupCharacterList(groupId);

		return ResponseEntity.ok(returnObject);
	}
}
