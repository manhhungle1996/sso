package vn.com.atomi.openbanking.authservice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.com.atomi.openbanking.authservice.common.annotations.UseLogging;
import vn.com.atomi.openbanking.authservice.dto.extauth.ExtAuthDTO;
import vn.com.atomi.openbanking.authservice.entities.PrivateToken;
import vn.com.atomi.openbanking.authservice.services.PrivateTokenService;
import vn.com.atomi.openbanking.authservice.utils.Constants;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(Constants.REQUEST_MAPPING_PREFIX)
public class ExtAuthController {
	@Autowired
	private PrivateTokenService privateTokenService;
	@PostMapping("/v1/ext-auth")
	@UseLogging
	public ResponseEntity<String> extAuthConnected(@RequestBody ExtAuthDTO requestBody, HttpServletRequest request ) {
		PrivateToken privateToken = new PrivateToken();
		privateToken.setConsentId(requestBody.getBindingMessage());
		privateToken.setAuthorization(request.getHeader("authorization"));
		privateTokenService.save(privateToken);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
}