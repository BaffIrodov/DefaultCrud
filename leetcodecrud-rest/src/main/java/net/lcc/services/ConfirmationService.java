package net.lcc.services;

import lombok.RequiredArgsConstructor;
import net.lcc.repositories.ConfirmationTokenRepository;
import net.lcc.repositories.UserRepository;
import net.lcc.entities.ConfirmationToken;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ConfirmationService {

    private final ConfirmationTokenRepository confirmationTokenRepository;

    private final UserRepository userRepository;

    private final ResourceHelper resourceHelper;

    @Value("classpath:/accept_confirmation.html")
    private Resource confirmMailHtml;

    @Transactional
    public String confirmInterview(UUID confirmationToken) {
        ConfirmationToken token = confirmationTokenRepository.findByConfirmationToken(confirmationToken);
        if (token != null) {
            token.setAccepted(true);
            confirmationTokenRepository.save(token);
            return resourceHelper.getResourceAsString(this.confirmMailHtml);
        } else {
            return "Ошибка подтверждения";
        }
    }
}
