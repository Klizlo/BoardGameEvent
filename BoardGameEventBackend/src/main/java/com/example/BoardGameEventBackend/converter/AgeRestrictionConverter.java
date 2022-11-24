package com.example.BoardGameEventBackend.converter;

import com.example.BoardGameEventBackend.model.AgeRestriction;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class AgeRestrictionConverter implements AttributeConverter<AgeRestriction, String> {
    @Override
    public String convertToDatabaseColumn(AgeRestriction ageRestriction) {
        if(ageRestriction == null){
            return null;
        }
        return ageRestriction.getAge();
    }

    @Override
    public AgeRestriction convertToEntityAttribute(String age) {
        if(age == null){
            return null;
        }

        return Stream.of(AgeRestriction.values())
                .filter(ageRestriction -> ageRestriction.getAge().equals(age))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
