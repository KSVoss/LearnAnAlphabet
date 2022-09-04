package ksvoss.backend.models;

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
		if(letters.isEmpty())return null;	// throw execption
		for(int i=0;i<letters.size();i++){
			if(letters.get(i).id()==letterId)return letters.get(i);
		}
		return null;		// throw execption
	}

	public String name(String preferredLanguage){

		if(names.length==0){
			return names[0].name();
		}
 		for(int i=0;i< names.length;i++){
			 System.out.println("i:"+i+" Language:"+names[i].language());
			if(names[i].language().equals(preferredLanguage))return names[i].name();
 		}
		return names[0].name();
	}
}
