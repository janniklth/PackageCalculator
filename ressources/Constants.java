package ressources;

public class Constants {
    // Parameters for the application
    public static final String RULES_FILE_PATH = "ressources/shipping_rules.json";
    public static final double CALCULATION_ANIMATION_DELAY = 0.1;
    public static final double CALCULATION_ANIMATION_DURATION = 0.4;
    public static final int CALCULATION_ANIMATION_CYCLES = 3;


    // Error messages for loading shipping rules
    public static final String SHIPPING_RULES_FILE_NOT_FOUND = "Shipping rules file not found: '%s'";
    public static final String SHIPPING_RULES_FILE_OPEN_ERROR = "Shipping rules file could not be opened: '%s' \nPlease check the file.";
    public static final String SHIPPING_RULES_EMPTY_ERROR = "No shipping rules found in the file: '%s' \nPlease check the file.";
    public static final String SHIPPING_RULES_INVALID_FIELD = "Invalid/missing '%s' field in shipping rule from file: '%s' \nPlease check the file.";
    public static final String SHIPPING_RULES_ERROR_LOADING = "Error loading shipping rules: ";

    // Error messages for user input
    public static final String INVALID_INPUT_TITLE = "Invalid Input";
    public static final String INVALID_INPUT = "Invalid input, please check:\n";
    public static final String LENGTH_FIELD_EMPTY = "- Length field is empty.\n";
    public static final String WIDTH_FIELD_EMPTY = "- Width field is empty.\n";
    public static final String HEIGHT_FIELD_EMPTY = "- Height field is empty.\n";
    public static final String WEIGHT_FIELD_EMPTY = "- Weight field is empty.\n";
    public static final String ENTER_VALID_NUMBER = "Enter a valid integer number: ";

    // Error messages for the calculator
    public static final String INVALID_PACKAGE_MESSAGE = "Invalid package parameters: ";
    public static final String INVALID_PACKAGE_LENGTH = "- Length must be greater than 0.\n";
    public static final String INVALID_PACKAGE_WIDTH = "- Width must be greater than 0.\n";
    public static final String INVALID_PACKAGE_HEIGHT = "- Height must be greater than 0.\n";
    public static final String INVALID_PACKAGE_WEIGHT = "- Weight must be greater than 0.\n";
    public static final String NO_SHIPPING_RULE_FOUND = "No suitable shipping rule found for the given package dimensions and weight.";
    public static final String PACKAGE_IS_NULL = "Package is null. Please provide valid package dimensions and weight.";

    // Other error texts
    public static final String CALCULATION_ERROR_RESULT = "Error";

    // Labels for the user interface
    public static final String LENGTH_LABEL = "Length:";
    public static final String WIDTH_LABEL = "Width:";
    public static final String HEIGHT_LABEL = "Height:";
    public static final String WEIGHT_LABEL = "Weight:";
    public static final String RESULT_LABEL = "Shipping Costs:";
    public static final String CALCULATE_BUTTON = "Calculate";
    public static final String CALCULATOR_AREA_LABEL = "Shipping Cost Calculator";
    public static final String COSTVIEW_AREA_LABEL = "Cost Overview";
    public static final String COSTVIEW_TABLE_TYPE_COLUM_HEADER = "Type";
    public static final String COSTVIEW_TABLE_COST_COLUM_HEADER = "Cost";
    public static final String COSTVIEW_TABLE_DIMENSIONS_COLUM_HEADER = "Dimensions";
    public static final String COSTVIEW_TABLE_MAXWEIGHT_COLUMN_HEADER = "Max Weight";

    // Other texts
    public static final String CALUCLATE_ANIMATION_SYMBOL = ".";

    // Further parameters for the user interface
    public static final String FONT_NAME = "System";

    // Labels for the settings
    public static final String SETTINGS_DIALOG_TITLE = "Settings";
    public static final String SETTINGS_MEASUREMENT_SYSTEM_SELECT_LABEL = "Measurement system";
    public static final String SETTINGS_CURRENCY_SELECT_LABEL = "Currency";
    public static final String SETTINGS_ERROR_DISPLAY_SELECT_LABEL = "Show error messages:";
    public static final String SETTINGS_APPLY_BUTTON = "Apply";
    public static final String SETTINGS_CANCEL_BUTTON = "Cancel";

    // Labels for the toolbar
    public static final String TOOLBAR_SETTINGS_BUTTON_LABEL = "Settings";
    public static final String TOOLBAR_INFO_BUTTON_LABEL = "About";
    public static final String TOOLBAR_EXIT_BUTTON_LABEL = "Exit";
    public static final String CALCULATE_BUTTON_NOTE = "Note: All different orientations of the package will\nbe tested automatically.";
}
