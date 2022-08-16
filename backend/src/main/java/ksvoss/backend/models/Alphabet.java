package ksvoss.backend.models;

public record Alphabet(
        int id,
	    AlphabetNameInDifferentLanguage[] names,
        byte[] pronunciationSoundfile,
        Letter[] letters

) {
}
