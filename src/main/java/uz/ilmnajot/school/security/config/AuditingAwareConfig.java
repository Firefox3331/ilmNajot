package uz.ilmnajot.school.security.config;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import uz.ilmnajot.school.entity.Users;

import java.util.Optional;

public class AuditingAwareConfig implements AuditorAware<Long> {


    private static final ThreadLocal<Boolean> auditFlag = ThreadLocal.withInitial(() -> Boolean.TRUE);

    public static void disableAuditing() {
        auditFlag.set(Boolean.FALSE);
    }

    public static void enableAuditing() {
        auditFlag.set(Boolean.TRUE);
    }


    @Override
    public Optional<Long> getCurrentAuditor() {
        if (!auditFlag.get()) {
            return Optional.empty();
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null
                && authentication.isAuthenticated()
                && !authentication.getPrincipal().equals("anonymousUser")) {
            Users user = (Users) authentication.getPrincipal();
            return Optional.of(user.getId());
        }
        return Optional.empty();
    }
}
