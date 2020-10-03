package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's name in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidCompany(String)}
 */
public class Company {
    public static final String MESSAGE_CONSTRAINTS = "Company name should  not be blank";
    public final String companyName;

    /**
     * Constructs a {@code Name}.
     *
     * @param name A valid name.
     */
    public Company(String name) {
        requireNonNull(name);
        checkArgument(isValidCompany(name), MESSAGE_CONSTRAINTS);
        companyName = name;
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidCompany(String test) {
        return test.trim().length() > 0;
    }


    @Override
    public String toString() {
        return companyName;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Name // instanceof handles nulls
                && companyName.equals(((Name) other).fullName)); // state check
    }

    @Override
    public int hashCode() {
        return companyName.hashCode();
    }

}
