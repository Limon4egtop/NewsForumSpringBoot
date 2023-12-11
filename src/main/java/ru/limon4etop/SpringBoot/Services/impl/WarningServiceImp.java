package ru.limon4etop.SpringBoot.Services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.limon4etop.SpringBoot.Services.WarningService;
import ru.limon4etop.SpringBoot.models.Warning;
import ru.limon4etop.SpringBoot.repos.warningRepo;

import java.util.List;

@Service
public class WarningServiceImp implements WarningService {
    private warningRepo warningRepo;

    @Autowired
    public WarningServiceImp(final warningRepo warningRepo) {
        this.warningRepo = warningRepo;
    }


    @Override
    public void addWarning(final Warning warning) {
        warning.setAdminSendNotificationId(getAuthenticationUserId());
        warningRepo.save(warning);
    }

    @Override
    public void deleteWarning(final Integer warningId) {
        Warning warning = new Warning(warningId);
        warningRepo.delete(warning);
    }

    @Override
    public List<Warning> findByUserGetNotification(final String userId) {
        return warningRepo.findByUserGetNotification(userId);
    }

    @Override
    public List<Warning> getAllWarnings() {
        return this.warningRepo.findAll();
    }

    private String getAuthenticationUserId() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
