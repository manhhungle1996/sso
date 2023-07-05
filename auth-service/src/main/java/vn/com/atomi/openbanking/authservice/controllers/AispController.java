package vn.com.atomi.openbanking.authservice.controllers;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.com.atomi.openbanking.authen.KeycloakService;
import vn.com.atomi.openbanking.authservice.common.annotations.UseLogging;
import vn.com.atomi.openbanking.authservice.common.exception.BusinessException;
import vn.com.atomi.openbanking.authservice.common.exception.IncorrectParameterException;
import vn.com.atomi.openbanking.authservice.dto.AccessConsent;
import vn.com.atomi.openbanking.authservice.dto.backoffice.RefreshTokenDTO;
import vn.com.atomi.openbanking.authservice.services.BackkofficeService;
import vn.com.atomi.openbanking.authservice.services.ConsentService;
import vn.com.atomi.openbanking.authservice.utils.Constants;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import static vn.com.atomi.openbanking.authservice.utils.Constants.IS_NOT_NULL;
import static vn.com.atomi.openbanking.authservice.utils.ValidatorUtils.validateString;

@RestController
@RequestMapping(Constants.REQUEST_MAPPING_PREFIX)
@RequiredArgsConstructor
public class AispController {
    @Autowired
    ConsentService consentService;
    @Autowired
    private BackkofficeService backkofficeService;
    @Autowired
    private KeycloakService keycloakService;
    @PostMapping("/v1/account-access-consents")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Account resource successfully retrieved", response = AccessConsent.class),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 405, message = "Method Not Allowed"),
            @ApiResponse(code = 406, message = "Not Acceptable"),
            @ApiResponse(code = 429, message = "Too Many Requests"),
            @ApiResponse(code = 500, message = "Internal Server Error")})
    @ApiOperation("API create access consents")
    @UseLogging
    public ResponseEntity<?> create(
//            @ApiParam(name ="Authorization", value="An Authorisation Token as per https://tools.ietf.org/html/rfc6750", example = "Bearer 2YotnFZFEjr1zCsicMWpAA", required = true)
//            @RequestHeader(value = "Authorization", required = true) String authorization,
            @ApiParam(name = "x-fapi-auth-date", value = "The time when the PSU last logged in with the TPP.  All dates in the HTTP headers are " +
                    "represented as RFC 7231 Full Dates. An example is below:  Sun, 10 Sep 2017 19:43:31 UTC", example = "Sun, 10 Sep 2017 19:43:31 GMT")
            @RequestHeader(value = "x-fapi-auth-date", required = false)
            @JsonFormat(shape=JsonFormat.Shape.STRING, pattern=Constants.HTTP_DATE_FORMAT, timezone=Constants.TIMEZONE) String xFapiAuthDate,
            @ApiParam(name = "x-fapi-customer-ip-address", value = "The PSU's IP address if the PSU is currently logged in with the TPP.", example = "104.25.212.99")
            @RequestHeader(value = "x-fapi-customer-ip-address", required = false) String xFapiCustomerIpAddress,

            @ApiParam(name = "x-fapi-interaction-id", value = "An RFC4122 UID used as a correlation id.", example = "93bac548-d2de-4546-b106-880a5018460d")
            @RequestHeader(value = "x-fapi-interaction-id", required = false) String xFapiInteractionId,
            @Valid @RequestBody AccessConsent accessConsent,
            HttpServletResponse response) {
        response.setHeader("x-fapi-interaction-id", xFapiInteractionId);

        return ResponseEntity.ok(consentService.create(accessConsent, xFapiInteractionId));
    }
    @DeleteMapping("/v1/account-access-consents/{consentId}")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Account resource successfully retrieved", response = AccessConsent.class),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 405, message = "Method Not Allowed"),
            @ApiResponse(code = 406, message = "Not Acceptable"),
            @ApiResponse(code = 429, message = "Too Many Requests"),
            @ApiResponse(code = 500, message = "Internal Server Error")})
    @ApiOperation("API delete access consents")
    @UseLogging
    public ResponseEntity<?> delete(
//            @ApiParam(name ="Authorization", value="An Authorisation Token as per https://tools.ietf.org/html/rfc6750", example = "Bearer 2YotnFZFEjr1zCsicMWpAA", required = true)
//            @RequestHeader(value = "Authorization", required = true) String authorization,
            @ApiParam(name = "x-fapi-auth-date", value = "The time when the PSU last logged in with the TPP.  All dates in the HTTP headers are " +
                    "represented as RFC 7231 Full Dates. An example is below:  Sun, 10 Sep 2017 19:43:31 UTC", example = "Sun, 10 Sep 2017 19:43:31 GMT")
            @RequestHeader(value = "x-fapi-auth-date", required = false)
            @JsonFormat(shape=JsonFormat.Shape.STRING, pattern=Constants.HTTP_DATE_FORMAT, timezone=Constants.TIMEZONE) String xFapiAuthDate,
            @ApiParam(name = "x-fapi-customer-ip-address", value = "The PSU's IP address if the PSU is currently logged in with the TPP.", example = "104.25.212.99")
            @RequestHeader(value = "x-fapi-customer-ip-address", required = false) String xFapiCustomerIpAddress,

            @ApiParam(name = "x-fapi-interaction-id", value = "An RFC4122 UID used as a correlation id.", example = "93bac548-d2de-4546-b106-880a5018460d")
            @RequestHeader(value = "x-fapi-interaction-id", required = false) String xFapiInteractionId,
            @PathVariable("consentId") String consentId,
            HttpServletResponse response) {
        int count = consentService.updateStatus(Constants.ConsentStatus.REMOVED, consentId);
        response.setHeader("x-fapi-interaction-id", xFapiInteractionId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/v1/account-access-consents/{consentId}")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Account resource successfully retrieved", response = AccessConsent.class),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 405, message = "Method Not Allowed"),
            @ApiResponse(code = 406, message = "Not Acceptable"),
            @ApiResponse(code = 429, message = "Too Many Requests"),
            @ApiResponse(code = 500, message = "Internal Server Error")})
    @ApiOperation("API get access consents")
    @UseLogging
    public ResponseEntity<?> getById(
//            @ApiParam(name ="Authorization", value="An Authorisation Token as per https://tools.ietf.org/html/rfc6750", example = "Bearer 2YotnFZFEjr1zCsicMWpAA", required = true)
//            @RequestHeader(value = "Authorization", required = true) String authorization,
            @ApiParam(name = "x-fapi-auth-date", value = "The time when the PSU last logged in with the TPP.  All dates in the HTTP headers are " +
                    "represented as RFC 7231 Full Dates. An example is below:  Sun, 10 Sep 2017 19:43:31 UTC", example = "Sun, 10 Sep 2017 19:43:31 GMT")
            @RequestHeader(value = "x-fapi-auth-date", required = false)
            @JsonFormat(shape=JsonFormat.Shape.STRING, pattern=Constants.HTTP_DATE_FORMAT, timezone=Constants.TIMEZONE) String xFapiAuthDate,
            @ApiParam(name = "x-fapi-customer-ip-address", value = "The PSU's IP address if the PSU is currently logged in with the TPP.", example = "104.25.212.99")
            @RequestHeader(value = "x-fapi-customer-ip-address", required = false) String xFapiCustomerIpAddress,

            @ApiParam(name = "x-fapi-interaction-id", value = "An RFC4122 UID used as a correlation id.", example = "93bac548-d2de-4546-b106-880a5018460d")
            @RequestHeader(value = "x-fapi-interaction-id", required = false) String xFapiInteractionId,
            @PathVariable("consentId") String consentId,
            HttpServletResponse response) throws IncorrectParameterException, BusinessException {
        response.setHeader("x-fapi-interaction-id", xFapiInteractionId);
        validateString(consentId, "consentID " + IS_NOT_NULL);
        return ResponseEntity.ok(consentService.findByConsentId(consentId));
    }

    @GetMapping("/v1/get-access-tokens/{consentId}")
    @ApiOperation("API get token by consent id")
    @UseLogging
    public ResponseEntity<?> getToken(@ApiParam(name = "x-fapi-auth-date", value = "The time when the PSU last logged in with the TPP.  All dates in the HTTP headers are " +
            "represented as RFC 7231 Full Dates. An example is below:  Sun, 10 Sep 2017 19:43:31 UTC", example = "Sun, 10 Sep 2017 19:43:31 GMT")
                                      @RequestHeader(value = "x-fapi-auth-date", required = false)
                                      @JsonFormat(shape=JsonFormat.Shape.STRING, pattern=Constants.HTTP_DATE_FORMAT, timezone=Constants.TIMEZONE) String xFapiAuthDate,
                                      @ApiParam(name = "x-fapi-customer-ip-address", value = "The PSU's IP address if the PSU is currently logged in with the TPP.", example = "104.25.212.99")
                                      @RequestHeader(value = "x-fapi-customer-ip-address", required = false) String xFapiCustomerIpAddress,

                                      @ApiParam(name = "x-fapi-interaction-id", value = "An RFC4122 UID used as a correlation id.", example = "93bac548-d2de-4546-b106-880a5018460d")
                                      @RequestHeader(value = "x-fapi-interaction-id", required = false) String xFapiInteractionId,
                                      @PathVariable("consentId") String consentId, HttpServletRequest httpServletRequest) throws BusinessException {
        System.out.println("Hello: "+ httpServletRequest.getHeader("x-fapi-xxx-id"));
        return ResponseEntity.ok(backkofficeService.getTokenByConsentId(consentId));
    }
    @PostMapping("/v1/refresh-tokens")
    @ApiOperation("API refresh tokens")
    @UseLogging
    public ResponseEntity<?> refreshToken(@ApiParam(name = "x-fapi-auth-date", value = "The time when the PSU last logged in with the TPP.  All dates in the HTTP headers are " +
            "represented as RFC 7231 Full Dates. An example is below:  Sun, 10 Sep 2017 19:43:31 UTC", example = "Sun, 10 Sep 2017 19:43:31 GMT")
                                          @RequestHeader(value = "x-fapi-auth-date", required = false)
                                          @JsonFormat(shape=JsonFormat.Shape.STRING, pattern=Constants.HTTP_DATE_FORMAT, timezone=Constants.TIMEZONE) String xFapiAuthDate,
                                          @ApiParam(name = "x-fapi-customer-ip-address", value = "The PSU's IP address if the PSU is currently logged in with the TPP.", example = "104.25.212.99")
                                          @RequestHeader(value = "x-fapi-customer-ip-address", required = false) String xFapiCustomerIpAddress,

                                          @ApiParam(name = "x-fapi-interaction-id", value = "An RFC4122 UID used as a correlation id.", example = "93bac548-d2de-4546-b106-880a5018460d")
                                          @RequestHeader(value = "x-fapi-interaction-id", required = false) String xFapiInteractionId,
                                          @RequestBody RefreshTokenDTO refreshTokenDTO) {
        return ResponseEntity.ok(keycloakService.refreshToken(refreshTokenDTO.getRefreshToken()));
    }

}
