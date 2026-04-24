package com.capgemini.training.appointmentbooking.web;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.capgemini.training.appointmentbooking.dataaccess.entity.UserEntity;
import com.capgemini.training.appointmentbooking.dataaccess.entity.ClientEntity;
import com.capgemini.training.appointmentbooking.dataaccess.repository.ClientRepository;
import com.capgemini.training.appointmentbooking.dataaccess.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

	private final ClientRepository clientRepository;
	private final UserRepository userRepository;

	@GetMapping("/info")
	public ResponseEntity<Map<String, Object>> getCurrentUser() {
		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication == null || !authentication.isAuthenticated()
				|| authentication.getPrincipal().equals("anonymousUser")) {
			return ResponseEntity.ok(Map.of("authenticated", false));
		}

		final Map<String, Object> userInfo = new HashMap<>();
		userInfo.put("authenticated", true);
		userInfo.put("username", authentication.getName());
		userInfo.put("roles", authentication.getAuthorities().stream()
				.map(auth -> auth.getAuthority().replace("ROLE_", "")).toList());

		// Get the client ID for the current user
		List<UserEntity> users = userRepository.findAllByEmail(authentication.getName());
		if (!users.isEmpty()) {
			UserEntity user = users.get(0);
			ClientEntity client = clientRepository.findByUserId(user.getId());
			if (client != null) {
				userInfo.put("id", client.getId());
				userInfo.put("clientId", client.getId());
			}
		}

		return ResponseEntity.ok(userInfo);
	}
}
