package com.movelog.domain.auth.application;

import com.movelog.domain.auth.dto.NicknameRes;
import com.movelog.domain.user.domain.User;
import com.movelog.domain.user.domain.repository.UserRepository;
import com.movelog.global.config.security.token.UserPrincipal;
import com.movelog.global.payload.Message;
import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {

    private final UserRepository userRepository;

    @Transactional
    public User findOrCreateUser(String provider, String idToken) {

        DecodedJWT decodedJWT = JWT.decode(idToken);
        String providerId = decodedJWT.getSubject();  // 사용자 고유 ID (sub)
        String email = decodedJWT.getClaim("email").asString();
        String username = decodedJWT.getClaim("nickname").asString();

        Optional<User> optionalUser = userRepository.findByProviderAndProviderId(provider, providerId);

        if (optionalUser.isPresent()) {
            return optionalUser.get();
        } else {
            User newUser = new User("", username, email, "ROLE_USER", provider, providerId);
            return userRepository.save(newUser);
        }
    }


    @Transactional
    public String findEmail(String providerId) {
        User user = userRepository.findByProviderId(providerId)
                .orElseThrow(EntityNotFoundException::new);
        return user.getEmail();
    }



    @Transactional
    public Message unlinkAccount(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(EntityNotFoundException::new);
        userRepository.delete(user);
        return Message.builder()
                .message("회원 탈퇴에 성공 했습니다.")
                .build();
    }
    @Transactional
    public void changeIsRegistered(Long id) {
        User user = userRepository.findById(id).get();
        user.updateIsRegistered();
    }
}
