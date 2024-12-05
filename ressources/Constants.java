package ressources;

public class Constants {
    // Parameters for the application
    public static final String RULES_FILE_PATH = "ressources/shipping_rules.json";

    // Error messages for loading shipping rules
    public static final String SHIPPING_RULES_FILE_NOT_FOUND = "Shipping rules file not found: '%s'";
    public static final String SHIPPING_RULES_FILE_OPEN_ERROR = "Shipping rules file could not be opened: '%s' \nPlease check the file.";
    public static final String SHIPPING_RULES_EMPTY_ERROR = "No shipping rules found in the file: '%s' \nPlease check the file.";
    public static final String SHIPPING_RULES_INVALID_FIELD = "Invalid/missing '%s' field in shipping rule from file: '%s' \nPlease check the file.";
    public static final String SHIPPING_RULES_ERROR_LOADING = "Error loading shipping rules";


}
