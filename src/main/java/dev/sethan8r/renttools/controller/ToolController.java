package dev.sethan8r.renttools.controller;

import dev.sethan8r.renttools.dto.ToolCreateDTO;
import dev.sethan8r.renttools.dto.ToolResponseDTO;
import dev.sethan8r.renttools.service.ToolService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tool")
@RequiredArgsConstructor
public class ToolController {

    private final ToolService toolService;

    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
    @PostMapping("/create")
    public ResponseEntity<Void> createTool(@RequestBody ToolCreateDTO toolCreateDTO) {
        toolService.createTool(toolCreateDTO);

        return ResponseEntity.status(HttpStatus.CREATED).build();

    }

    @GetMapping("/get/{id}")
    public ToolResponseDTO getToolById(@PathVariable Long id) {

        return toolService.getToolById(id);
    }

    @PreAuthorize("hasRole('SUPERADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteTool(@PathVariable Long id) {
        toolService.deleteTool(id);

        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
    @PatchMapping("/replace/picture/{toolId}/picture/{pictureId}")
    public ResponseEntity<Void> replacePictureToTool(@PathVariable Long pictureId,@PathVariable Long toolId) {
        toolService.replacePictureToTool(pictureId, toolId);

        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasRole('SUPERADMIN')")
    @PatchMapping("/replace/price/{toolId}")
    public ResponseEntity<Void> replacePriceToTool(@PathVariable Long toolId,@RequestParam Long price) {
        toolService.replacePriceToTool(toolId, price);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/search")
    public Page<ToolResponseDTO> searchToolByName(
            @RequestParam String name,@PageableDefault(size = 20, sort = "name") Pageable pageable)  {

        return toolService.searchToolByName(name, pageable);
    }

    @GetMapping("/get/all")
    public Page<ToolResponseDTO> getAllTool(@PageableDefault(size = 20, sort = "name") Pageable pageable) {

        return toolService.getAllTool(pageable);
    }

    @GetMapping("/get/type")
    public Page<ToolResponseDTO> getByType(
            @RequestParam String type,@PageableDefault(size = 20, sort = "name") Pageable pageable) {

        return toolService.getByType(type, pageable);
    }

    @GetMapping("/get/isAvailable")
    public Page<ToolResponseDTO> getByIsAvailable(
            @RequestParam Boolean isAvailable,@PageableDefault(size = 20, sort = "name") Pageable pageable) {

        return toolService.getByIsAvailable(isAvailable, pageable);
    }

    @GetMapping("/get/isAvailable/type")
    public Page<ToolResponseDTO> getByTypeAndIsAvailable(
            @RequestParam String type,@RequestParam Boolean isAvailable,
            @PageableDefault(size = 20, sort = "name") Pageable pageable) {

        return toolService.getByTypeAndIsAvailable(type, isAvailable, pageable);
    }

    @PatchMapping("/replace/take/{id}")
    public ResponseEntity<Void> takeTool(@PathVariable Long id) {
        toolService.takeTool(id);

        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
    @PatchMapping("/replace/return/{id}")
    public ResponseEntity<Void> returnTool(@PathVariable Long id) {
        toolService.returnTool(id);

        return ResponseEntity.ok().build();
    }
}
