package services.bussiness;

import models.SlangEntry;
import java.util.ArrayList;
import java.util.List;

/**
 * Parser class for parsing slang definitions from a single line of text.
 * Parses slang entries in the format: Slang`Meaning1| Meaning2| Meaning3
 */

public class SlangParser implements IParsable<SlangEntry> {

    
    // Delimiter used to separate slang from its meanings
    private static final String SLANG_MEANING_DELIMITER = "`";
    
    // Delimiter used to separate multiple meanings
    private static final String MEANING_SEPARATOR = "\\|";
    
    /**
     * Parses a slang definition line and extracts the slang word and its meanings.
     * 
     * Format: Slang`Meaning1| Meaning2| Meaning3
     * 
     * @param line The line to parse
     * @return A SlangEntry object containing the slang and its meanings, or null if parsing fails
     */
    @Override
    public SlangEntry parse(String line) {
        // Skip empty lines and lines starting with '#' (comments)
        if (line == null || line.trim().isEmpty() || line.trim().startsWith("#")) {
            return null;
        }
        
        // Split by the backtick delimiter
        String[] parts = line.split(SLANG_MEANING_DELIMITER);
        
        // Must have at least slang and one meaning
        if (parts.length < 2) {
            return null;
        }
        
        String slang = parts[0].trim();
        
        // Skip if slang is empty
        if (slang.isEmpty()) {
            return null;
        }
        
        // Extract meanings from the second part
        String meaningsStr = parts[1].trim();
        
        // Split meanings by pipe separator and trim whitespace
        List<String> meanings = new ArrayList<>();
        for (String meaning : meaningsStr.split(MEANING_SEPARATOR)) {
            String trimmedMeaning = meaning.trim();
            if (!trimmedMeaning.isEmpty()) {
                meanings.add(trimmedMeaning);
            }
        }
        
        // Skip if no valid meanings found
        if (meanings.isEmpty()) {
            return null;
        }
        
        return new SlangEntry(slang, meanings);
    }
}
