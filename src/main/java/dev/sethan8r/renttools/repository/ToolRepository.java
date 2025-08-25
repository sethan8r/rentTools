package dev.sethan8r.renttools.repository;

import dev.sethan8r.renttools.model.Tool;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ToolRepository extends JpaRepository<Tool, Long> {
    List<Tool> findByIsAvailable(Boolean isAvailable);
    List<Tool> findByType(String type);
    List<Tool> findByTypeAndIsAvailable(String type, Boolean isAvailable);
    Page<Tool> findByNameContainingIgnoreCase(String name, Pageable pageable);

    @Query("SELECT t FROM Tool t ORDER BY t.isAvailable DESC, t.name ASC")
    Page<Tool> findAllToolByAvailableDesc(Pageable pageable);
}
