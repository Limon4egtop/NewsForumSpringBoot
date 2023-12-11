package ru.limon4etop.SpringBoot.Services;

import ru.limon4etop.SpringBoot.models.Warning;

import java.util.List;

public interface WarningService {
    void addWarning(final Warning warning);
    void deleteWarning(final Integer warningId);
    List<Warning> findByUserGetNotification(final String userId);
    List<Warning> getAllWarnings();
}
