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
import org.springframework.transaction.annotation.Transactional;


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

    @Transactional
    public void createTool(ToolCreateDTO toolCreateDTO) {
        toolRepository.save(toolMapper.toTool(toolCreateDTO));
    }

    public void deleteTool(Long id) {
        toolRepository.deleteById(id);
    }

    @Transactional
    public void replacePictureToTool(Long pictureId, Long toolId) {
        Tool tool = toolRepository.findById(toolId).orElseThrow(
                () -> new NotFoundException("Инструмент с ID " + toolId + " не найден"));
        tool.setPicture(pictureRepository.findById(pictureId).orElseThrow(
                () -> new NotFoundException("Изображение с ID " + pictureId + " не найден")));

        toolRepository.save(tool);
    }

    @Transactional
    public void replacePriceToTool(Long toolId, Long price) {
        Tool tool = toolRepository.findById(toolId).orElseThrow(
                () -> new NotFoundException("Инструмент с ID " + toolId + " не найден"));
        tool.setPrice(price);

        toolRepository.save(tool);
    }

    public Page<ToolResponseDTO> searchToolByName(String name, Pageable pageable) {
        return toolRepository.findByNameContainingIgnoreCase(name, pageable).map(toolMapper::toToolResponseDTO);
    }

    public Page<ToolResponseDTO> getAllTool(Pageable pageable) {
        return toolRepository.findAllToolByAvailableDesc(pageable).map(toolMapper::toToolResponseDTO);
    }

    public Page<ToolResponseDTO> getByType(String type, Pageable pageable) {
        return toolRepository.findByType(type,pageable)
                .map(toolMapper::toToolResponseDTO);
    }

    public Page<ToolResponseDTO> getByIsAvailable(Boolean isAvailable, Pageable pageable) {
        return toolRepository.findByIsAvailable(isAvailable, pageable)
                .map(toolMapper::toToolResponseDTO);
    }

    public Page<ToolResponseDTO> getByTypeAndIsAvailable(String type, Boolean isAvailable, Pageable pageable) {
        return toolRepository.findByTypeAndIsAvailable(type, isAvailable, pageable)
                .map(toolMapper::toToolResponseDTO);
    }

    @Transactional
    public void takeTool(Long id) {
        Tool tool = toolRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Инструмент с ID " + id + " не найден"));

        tool.setIsAvailable(false);
        toolRepository.save(tool);
    }

    @Transactional
    public void returnTool(Long id) {
        Tool tool = toolRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Инструмент с ID " + id + " не найден"));

        tool.setIsAvailable(true);
        toolRepository.save(tool);
    }
}
