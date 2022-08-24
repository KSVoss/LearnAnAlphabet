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
		return id == alphabet.id;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
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
