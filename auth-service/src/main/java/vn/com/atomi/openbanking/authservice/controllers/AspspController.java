package vn.com.atomi.openbanking.authservice.controllers;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.com.atomi.openbanking.authen.dto.CibaCallBackRequest;
import vn.com.atomi.openbanking.authservice.common.annotations.UseLogging;
import vn.com.atomi.openbanking.authservice.common.exception.BusinessException;
import vn.com.atomi.openbanking.authservice.services.BackkofficeService;
import vn.com.atomi.openbanking.authservice.utils.Constants;

import javax.validation.Valid;

@RestController
@RequestMapping(Constants.REQUEST_MAPPING_PREFIX)
public class AspspController {
    @Autowired
    private BackkofficeService backkofficeService;
    @PostMapping("/v1/update-status-consents/{consentId}")
    @ApiOperation("API update status consents")
    @UseLogging
    public ResponseEntity<?> update(@ApiParam(name = "x-fapi-auth-date", value = "The time when the PSU last logged in with the TPP.  All dates in the HTTP headers are " +
            "represented as RFC 7231 Full Dates. An example is below:  Sun, 10 Sep 2017 19:43:31 UTC", example = "Sun, 10 Sep 2017 19:43:31 GMT")
                                             @RequestHeader(value = "x-fapi-auth-date", required = false)
                                             @JsonFormat(shape=JsonFormat.Shape.STRING, pattern=Constants.HTTP_DATE_FORMAT, timezone=Constants.TIMEZONE) String xFapiAuthDate,
                                         @ApiParam(name = "x-fapi-customer-ip-address", value = "The PSU's IP address if the PSU is currently logged in with the TPP.", example = "104.25.212.99")
                                             @RequestHeader(value = "x-fapi-customer-ip-address", required = false) String xFapiCustomerIpAddress,

                                         @ApiParam(name = "x-fapi-interaction-id", value = "An RFC4122 UID used as a correlation id.", example = "93bac548-d2de-4546-b106-880a5018460d")
                                             @RequestHeader(value = "x-fapi-interaction-id", required = false) String xFapiInteractionId,
                                         @PathVariable("consentId") String consentId,
                                    @Valid @RequestBody CibaCallBackRequest cibaCallBackRequest) throws BusinessException {

        return ResponseEntity.ok(backkofficeService.updateStatusConsent(consentId, cibaCallBackRequest));
    }
}
