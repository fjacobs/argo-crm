package nl.argo360.argorepo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.fail;

@SpringBootTest
class CrmRepositoryTest {
    @Autowired
    CrmRepository repository;

    @BeforeEach
    void init_database() {
        Contact contact1 = Contact.builder()
                .title("Project Manager Infrastructure Services")
                .type(Contact.Type.ONSITE)
                .firstName("John")
                .lastName("Doe")
                .email("email@test.nl")
                .phone("31609876543")
                .build();

        Contact contact2 = Contact.builder()
                .title("Account Manager")
                .type(Contact.Type.SALES)
                .firstName("Mary")
                .lastName("Jane")
                .email("mary@yahoo.nl")
                .phone("0355240985")
                .build();

        Account account1 = Account.builder()
                .companyName("Equinix")
                .accountType(Account.ACCOUNT_TYPE.ALL)
                .phone("+31612345678")
                .owner("Victor Jansma")
                .website("https://www.equinix.nl")
                .contacts(Set.of(contact2, contact1))
                .build();

        repository.save(account1);

        Contact contact3 = Contact.builder()
                .title("Title 3")
                .type(Contact.Type.BILLING)
                .firstName("FirstName3")
                .lastName("LastName3")
                .email("email3@test3.nl")
                .phone("316333333333")
                .build();

        Contact contact4 = Contact.builder()
                .title("Title 4")
                .type(Contact.Type.CERTIFICATE)
                .firstName("FirstName3")
                .lastName("LastName3")
                .email("email3@test3.nl")
                .phone("316333333333")
                .build();

        // -----------------------------------------------------------------------------------------

        Location location1 = Location.builder()
                .name("Vestiging Rotterdam")
                .country("The Netherlands")
                .city("Rotterdam")
                .address("Weena 664")
                .postalCode("3012 CN")
                .phone("010 - 742 17 54 ")
                .build();


        Account account2 = Account.builder()
                .companyName("Coolblue")
                .accountType(Account.ACCOUNT_TYPE.ALL)
                .phone("+31698888888")
                .owner("Dylan Klein")
                .website("https://www.coolblue.nl")
                .contacts(Set.of(contact3, contact4))
                .locations(Set.of(location1))
                .build();

        account2.setLocations(Set.of(location1));
        repository.save(account2);
    }

    @Test
    void account_test() {
        if (repository.findById("Equinix").isEmpty()) {
            fail("Account was not inserted in the database.....................");
        }

        var dbAccount = repository.findById("Equinix").get();

        assertThat(dbAccount.getCompanyName()).isEqualTo("Equinix");
        assertThat(dbAccount.getAccountType()).isEqualTo(Account.ACCOUNT_TYPE.ALL);
        assertThat(dbAccount.getPhone()).isEqualTo("+31612345678");
        assertThat(dbAccount.getOwner()).isEqualTo("Victor Jansma");
        assertThat(dbAccount.getWebsite()).isEqualTo("https://www.equinix.nl");
    }

    @Test
    void contact_test() {
        if (repository.findById("Equinix").isEmpty()) {
            fail("Account was not inserted in the database.....................");
        }
        var dbAccount = repository.findById("Equinix").get();

        if (dbAccount.getContacts().isEmpty()) {
            fail("Account does not have any contacts at all................");
        }

        Contact dbContact = dbAccount.getContacts().
                stream().filter(contact -> contact.getFirstName().equals("John")).findFirst().get();

        assertThat(dbContact.getTitle()).isEqualTo("Project Manager Infrastructure Services");
        assertThat(dbContact.getType()).isEqualTo(Contact.Type.ONSITE);
        assertThat(dbContact.getFirstName()).isEqualTo("John");
        assertThat(dbContact.getLastName()).isEqualTo("Doe");
        assertThat(dbContact.getEmail()).isEqualTo("email@test.nl");
        assertThat(dbContact.getPhone()).isEqualTo("31609876543");
    }

    @Test
    void location_test() {
        if (repository.findById("Coolblue").isEmpty()) {
            fail("Account was not inserted in the database.....................");
        }
        var dbAccount = repository.findById("Coolblue").get();

        if (dbAccount.getLocations().isEmpty()) {
            fail("DB Table account does not have any locations...............");
        }
        var dbLocation1 = dbAccount.getLocations().stream().findFirst().get();
        assertThat(dbLocation1.getName()).isEqualTo("Vestiging Rotterdam");
        assertThat(dbLocation1.getCountry()).isEqualTo("The Netherlands");
        assertThat(dbLocation1.getCity()).isEqualTo("Rotterdam");
        assertThat(dbLocation1.getAddress()).isEqualTo("Weena 664");
        assertThat(dbLocation1.getPostalCode()).isEqualTo("3012 CN");
        assertThat(dbLocation1.getPhone()).isEqualTo("010 - 742 17 54 ");
    }
}
