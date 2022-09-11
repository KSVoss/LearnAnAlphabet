package ksvoss.backend.models;

import ksvoss.backend.user.CombinationLetterInAnAlphabetException;
import ksvoss.backend.user.EmptyAlphabetDatabaseException;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public record Alphabet(


        int id,
	    AlphabetNameInDifferentLanguage[] names,
		List<Letter> letters)
{
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
				", letters=" + letters +
				'}';
	}

	public Letter findLetterById(int letterId){
		if(letters.isEmpty())throw new EmptyAlphabetDatabaseException();
		for (Letter letter : letters) {
			if (letter.id() == letterId) return letter;
		}
		throw new CombinationLetterInAnAlphabetException(this.id,letterId);
 	}

	public String name(String preferredLanguage){
		if(names==null)return "No_Name";
		if(names.length==0)return "No_Name";

		if(names.length==1){
			return names[0].name();
		}
		for (AlphabetNameInDifferentLanguage name : names) {
			if (name.language().equals(preferredLanguage)) return name.name();
		}
		return names[0].name();
	}
}
