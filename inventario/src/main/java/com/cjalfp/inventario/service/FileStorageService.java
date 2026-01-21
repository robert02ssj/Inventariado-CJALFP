package com.cjalfp.inventario.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class FileStorageService {

    private static final Logger logger = LoggerFactory.getLogger(FileStorageService.class);
    private final String uploadDir = "uploads/asignaciones/";

    public FileStorageService() {
        // Crear directorio si no existe
        try {
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
        } catch (IOException e) {
            throw new RuntimeException("No se pudo crear el directorio de uploads", e);
        }
    }

    /**
     * Guarda un archivo PDF y retorna la ruta relativa
     */
    public String guardarPdf(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            return null;
        }

        // Validar que sea PDF por content-type
        String contentType = file.getContentType();
        if (contentType == null || !contentType.equals("application/pdf")) {
            throw new IllegalArgumentException("El archivo debe ser un PDF");
        }

        // Validar extensión del archivo
        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null || !originalFilename.toLowerCase().endsWith(".pdf")) {
            throw new IllegalArgumentException("El archivo debe tener extensión .pdf");
        }

        // Generar nombre único usando UUID para evitar colisiones
        String fileName = "asignacion_" + UUID.randomUUID().toString() + ".pdf";
        Path uploadPath = Paths.get(uploadDir);
        Path filePath = uploadPath.resolve(fileName);

        // Guardar archivo
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        return uploadDir + fileName;
    }

    /**
     * Elimina un archivo PDF
     */
    public void eliminarPdf(String rutaPdf) {
        if (rutaPdf == null || rutaPdf.isEmpty()) {
            return;
        }

        try {
            Path filePath = Paths.get(rutaPdf);
            Files.deleteIfExists(filePath);
        } catch (IOException e) {
            // Log error pero no fallar
            logger.error("Error al eliminar archivo: {}", rutaPdf, e);
        }
    }
}
