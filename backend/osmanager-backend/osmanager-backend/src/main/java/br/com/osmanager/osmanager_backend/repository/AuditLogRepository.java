package br.com.osmanager.osmanager_backend.repository;

import br.com.osmanager.osmanager_backend.entity.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {
}
