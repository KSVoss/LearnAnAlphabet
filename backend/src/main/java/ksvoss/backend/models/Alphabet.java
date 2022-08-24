package ksvoss.backend.models;

import java.util.Arrays;
import java.util.Objects;

public record Alphabet(
        int id,
	    AlphabetNameInDifferentLanguage[] names,
        byte[] pronunciationSoundfile,
        Letter[] letters

) {
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Alphabet alphabet = (Alphabet) o;
		return id == alphabet.id && Arrays.equals(names, alphabet.names) && Arrays.equals(pronunciationSoundfile, alphabet.pronunciationSoundfile) && Arrays.equals(letters, alphabet.letters);
	}

	@Override
	public int hashCode() {
		int result = Objects.hash(id);
		result = 31 * result + Arrays.hashCode(names);
		result = 31 * result + Arrays.hashCode(pronunciationSoundfile);
		result = 31 * result + Arrays.hashCode(letters);
		return result;
	}

	@Override
	public String toString() {
		return "Alphabet{" +
				"id=" + id +
				", names=" + Arrays.toString(names) +
				", pronunciationSoundfile=" + Arrays.toString(pronunciationSoundfile) +
				", letters=" + Arrays.toString(letters) +
				'}';
	}
}
