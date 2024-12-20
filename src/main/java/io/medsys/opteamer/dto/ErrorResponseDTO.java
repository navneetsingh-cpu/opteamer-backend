package io.medsys.opteamer.dto;

public class ErrorResponseDTO extends AbstractResponseDTO {
    public ErrorResponseDTO(boolean succeeded, String message) {
        setMessage(message);
        setSucceeded(succeeded);
    }
}
