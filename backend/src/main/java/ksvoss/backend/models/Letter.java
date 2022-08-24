package ksvoss.backend.models;

import java.util.Arrays;
import java.util.Objects;

public record Letter(
        int id,
        String signAsText,
        byte[] signAsPicture,
        String spelling,
        int pronunciationStartInMs,
        int pronunciationDurationMs,
        String pronunciationUrl
        ) {
        @Override
        public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                Letter letter = (Letter) o;
                return id == letter.id && pronunciationStartInMs == letter.pronunciationStartInMs && pronunciationDurationMs == letter.pronunciationDurationMs && Objects.equals(signAsText, letter.signAsText) && Arrays.equals(signAsPicture, letter.signAsPicture) && Objects.equals(spelling, letter.spelling) && Objects.equals(pronunciationUrl, letter.pronunciationUrl);
        }

        @Override
        public int hashCode() {
                int result = Objects.hash(id, signAsText, spelling, pronunciationStartInMs, pronunciationDurationMs, pronunciationUrl);
                result = 31 * result + Arrays.hashCode(signAsPicture);
                return result;
        }

        @Override
        public String toString() {
                return "Letter{" +
                        "id=" + id +
                        ", signAsText='" + signAsText + '\'' +
                        ", signAsPicture=" + Arrays.toString(signAsPicture) +
                        ", spelling='" + spelling + '\'' +
                        '}';
        }
}
