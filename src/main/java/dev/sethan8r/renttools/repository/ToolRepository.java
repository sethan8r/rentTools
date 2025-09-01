package dev.sethan8r.renttools.repository;

import dev.sethan8r.renttools.model.Tool;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface ToolRepository extends JpaRepository<Tool, Long> {
    Page<Tool> findByIsAvailable(Boolean isAvailable, Pageable pageable);
    Page<Tool> findByType(String type, Pageable pageable);
    Page<Tool> findByTypeAndIsAvailable(String type, Boolean isAvailable, Pageable pageable);
    Page<Tool> findByNameContainingIgnoreCase(String name, Pageable pageable);

    @Query("SELECT t FROM Tool t ORDER BY t.isAvailable DESC, t.name ASC")
    Page<Tool> findAllToolByAvailableDesc(Pageable pageable);
}
