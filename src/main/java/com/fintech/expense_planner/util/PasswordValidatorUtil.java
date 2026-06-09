package com.fintech.expense_planner.util;

import java.util.List;

import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.LengthRule;
import org.passay.PasswordData;
import org.passay.PasswordValidator;
import org.passay.RuleResult;
import org.passay.WhitespaceRule;
import org.springframework.stereotype.Component;

@Component
public class PasswordValidatorUtil {

    private final PasswordValidator validator = new PasswordValidator(List.of(
        new LengthRule(8, 64),
        new CharacterRule(EnglishCharacterData.UpperCase, 1),
        new CharacterRule(EnglishCharacterData.LowerCase, 1),
        new CharacterRule(EnglishCharacterData.Digit, 1),
        new CharacterRule(EnglishCharacterData.Special, 1),
        new WhitespaceRule()
    ));

    public void validate(String password) {
        RuleResult result = validator.validate(new PasswordData(password));

        if (!result.isValid()) {
            String messages = String.join(", ", validator.getMessages(result));
            throw new RuntimeException("Invalid password: " + messages);
        }
    }
}