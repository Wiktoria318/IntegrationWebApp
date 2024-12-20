package pl.polsl.mostowska.integrationwebapp;
import javax.swing.JOptionPane;
import lombok.*;

/**
 * The IntegrationErrorHandler class is responsible for handling different types of exceptions that
 * may occur during integration calculations. It provides methods to display error messages via
 * dialog boxes.
 * 
 * @author Wiktoria Mostowska
 * @version 3.0
 */
public class IntegrationErrorHandler {
    /**
     * Enumeration representing specific types of integration errors.
     * Each error type is associated with a corresponding error message.
     */
    @AllArgsConstructor
    public enum IntegrationError {
        /** 
         * Occurs when partition count is invalid */
        INVALID_PARTITIONS("Invalid partition number."),
        /** 
         * Occurs when the upper bound is smaller than the lower bound
         */
        UPPER_BOUND_LESS_THAN_LOWER("Upper bound cannot be less than lower bound."),
        /** 
         * Occurs when the input cannot be parsed to a number
         */
        NUMBER_FORMAT_ERROR("Invalid number format. Please enter valid numeric values."),
        /** 
         * Represents a general or unknown error
         */
        GENERAL_ERROR("An error occurred during the calculation."),
        /** 
         * Occurs when a null reference is accessed
         */
        NULL_POINTER_ERROR("Null pointer encountered. Please ensure all required inputs are provided.");

        private final String message;
         /**
         * Retrieves the error message associated with the specific error type.
         * 
         * @return The error message as a string.
         */
        public String getMessage() {
            return message;
        }
    }
    /**
     * Handles the integration error by displaying an error message in a dialog box.
     *
     * @param error The IntegrationError that is being handled.
     */
    public void handleError(IntegrationError error) {
        showErrorDialog(error.getMessage());
    }
    
    /**
     * Handles the UpperBoundLessThanLowerBoundException by displaying an error message in a dialog box.
     *
     * @param exception The UpperBoundLessThanLowerBoundException that is being handled.
     */
    public void handleUpperBoundLessThanLowerBoundException(IntegrationError exception) {
        showErrorDialog(IntegrationError.UPPER_BOUND_LESS_THAN_LOWER.getMessage() + " " + exception.getMessage());
    }
    
    /**
     * Handles InvalidPartitionsException by displaying an error message in a dialog box.
     *
     * @param exception The InvalidPartitionsException that is being handled.
     */
    public void handleInvalidPartitionsException(IntegrationError exception) {
        showErrorDialog(IntegrationError.INVALID_PARTITIONS.getMessage() + " " + exception.getMessage());
    }
    /**
     * Handles NumberFormatException by displaying an error message in a dialog box.
     *
     * @param exception The NumberFormatException that is being handled.
     */
    public void handleNumberFormatException(NumberFormatException exception) {
        showErrorDialog(IntegrationError.NUMBER_FORMAT_ERROR.getMessage() + " " + exception.getMessage());
    }
    /**
     * Handles the general exception by displaying an error message in a dialog box.
     *
     * @param exception The Exception to be handled.
     */
    public void handleGeneralException(Exception exception) {
        showErrorDialog(IntegrationError.GENERAL_ERROR.getMessage() + " " + exception.getMessage());
    }
    
    /**
     * Handles the NullPointerException by displaying an error message in a dialog box.
     * @param exception The Exception to be handled.
     */
    public void handleNullPointerException(NullPointerException exception) {
        showErrorDialog(IntegrationError.NULL_POINTER_ERROR.getMessage() + " " + exception.getMessage());
    }

    /**
     * Helper method to display error messages in a dialog box.
     * This method is used to centralize the logic for showing error messages in dialog boxes.
     * 
     * @param message The error message to be displayed in the dialog.
     */
    private void showErrorDialog(String message) {
        /**
         * Displays an error dialog with the provided error message
         */
        JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
