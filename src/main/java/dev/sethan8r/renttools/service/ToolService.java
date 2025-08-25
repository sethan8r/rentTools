package dev.sethan8r.renttools.service;

import dev.sethan8r.renttools.dto.ToolCreateDTO;
import dev.sethan8r.renttools.dto.ToolResponseDTO;
import dev.sethan8r.renttools.exception.NotFoundException;
import dev.sethan8r.renttools.mapper.ToolMapper;
import dev.sethan8r.renttools.model.Tool;
import dev.sethan8r.renttools.repository.PictureRepository;
import dev.sethan8r.renttools.repository.ToolRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ToolService {

    private final ToolMapper toolMapper;
    private final ToolRepository toolRepository;
    private final PictureRepository pictureRepository;

    public ToolResponseDTO getToolById(Long id) {
        return toolMapper.toToolResponseDTO(toolRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Инструмент с ID " + id + " не найден")));
    }

    public void createTool(ToolCreateDTO toolCreateDTO) {
        toolRepository.save(toolMapper.toTool(toolCreateDTO));
    }

    public void deleteTool(Long id) {
        toolRepository.deleteById(id);
    }

    public void replacePictureToTool(Long pictureId, Long toolId) {
        Tool tool = toolRepository.findById(toolId).orElseThrow(
                () -> new NotFoundException("Инструмент с ID " + toolId + " не найден"));
        tool.setPicture(pictureRepository.findById(pictureId).orElseThrow(
                () -> new NotFoundException("Изображение с ID " + pictureId + " не найден")));

        toolRepository.save(tool);
    }

    public void replacePriceToTool(Long toolId, Long price) {
        Tool tool = toolRepository.findById(toolId).orElseThrow(
                () -> new NotFoundException("Инструмент с ID " + toolId + " не найден"));
        tool.setPrice(price);

        toolRepository.save(tool);
    }

    public Page<Tool> searchToolByName(String name, Pageable pageable) {
        return toolRepository.findByNameContainingIgnoreCase(name, pageable);
    }

    public Page<Tool> getAllTool(Pageable pageable) {
        return toolRepository.findAllToolByAvailableDesc(pageable);
    }

    public List<Tool> getByType(String type) {
        return toolRepository.findByType(type);
    }

    public List<Tool> getByIsAvailable(Boolean isAvailable) {
        return toolRepository.findByIsAvailable(isAvailable);
    }

    public List<Tool> getByTypeAndIsAvailable(String type, Boolean isAvailable) {
        return toolRepository.findByTypeAndIsAvailable(type, isAvailable);
    }
}
