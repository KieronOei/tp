package presspal.contact.model.person;

import static presspal.contact.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import presspal.contact.commons.util.ToStringBuilder;
import presspal.contact.model.category.Category;

/**
 * Represents a Person in the contact book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person {

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email email;

    // Data fields
    private final Organisation organisation;
    private final Set<Category> categories = new HashSet<>();
    // TODO: add InterviewList interviews & Role role

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Email email, Organisation organisation, Set<Category> categories) {
        requireAllNonNull(name, phone, email, organisation, categories);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.organisation = organisation;
        this.categories.addAll(categories);
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public Organisation getOrganisation() {
        return organisation;
    }

    /**
     * Returns an immutable category set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Category> getCategories() {
        return Collections.unmodifiableSet(categories);
    }

    /**
     * Returns true if both persons have the same name.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        return otherPerson != null
                && otherPerson.getName().equals(getName());
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Person)) {
            return false;
        }

        Person otherPerson = (Person) other;
        return name.equals(otherPerson.name)
                && phone.equals(otherPerson.phone)
                && email.equals(otherPerson.email)
                && organisation.equals(otherPerson.organisation)
                && categories.equals(otherPerson.categories);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, organisation, categories);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("phone", phone)
                .add("email", email)
                .add("organisation", organisation)
                .add("categories", categories)
                .toString();
    }

}
