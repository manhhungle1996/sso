package vn.com.atomi.openbanking.authservice.services;

import vn.com.atomi.openbanking.authservice.entities.PrivateToken;

public interface PrivateTokenService {
    PrivateToken save(PrivateToken privateToken);
}
