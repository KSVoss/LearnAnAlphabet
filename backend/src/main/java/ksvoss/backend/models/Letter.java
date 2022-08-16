package ksvoss.backend.models;

public record Letter(
        int id,
        String signAsText,
        byte[] signAsPicture,
        String spelling,
        int pronunciationStartInMs,
        int pronunciationDurationMs,
        String pronunciationUrl
        ) {
}
