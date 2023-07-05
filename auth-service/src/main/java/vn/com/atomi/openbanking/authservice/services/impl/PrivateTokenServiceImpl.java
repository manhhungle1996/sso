package vn.com.atomi.openbanking.authservice.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.com.atomi.openbanking.authservice.entities.PrivateToken;
import vn.com.atomi.openbanking.authservice.repositories.PrivateTokenRepository;
import vn.com.atomi.openbanking.authservice.services.PrivateTokenService;
@Service
public class PrivateTokenServiceImpl implements PrivateTokenService {
    @Autowired
    PrivateTokenRepository privateTokenRepository;
    @Override
    public PrivateToken save(PrivateToken privateToken) {
        return privateTokenRepository.save(privateToken);
    }
}
