package edu.bbte.idde.jgim2241.repository.jdbc;

import edu.bbte.idde.jgim2241.model.Contact;
import edu.bbte.idde.jgim2241.repository.ContactDao;
import edu.bbte.idde.jgim2241.repository.RepositoryException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Repository
@Profile("prod")
@Slf4j
public class ContactJdbcDao implements ContactDao {
    @Autowired
    private ConnectionProvider connectionProvider;

    @Override
    public Collection<Contact> findAll() throws RepositoryException {
        List<Contact> contacts = new ArrayList<>();

        try (Connection connection = connectionProvider.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Contact");
            while (resultSet.next()) {
                Contact contact = getContactFromResultSet(resultSet);
                contacts.add(contact);
            }
            log.info("Find all Contact successful");
        } catch (SQLException e) {
            log.error("Database error at find all", e);
            throw new RepositoryException("Cant find entities", e);
        }

        return contacts;
    }

    @Override
    public Contact findByID(Long id) throws RepositoryException {
        try (Connection connection = connectionProvider.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Contact WHERE id = ?");
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return getContactFromResultSet(resultSet);
            }
            log.info("Find Contact by id successful");
        } catch (SQLException e) {
            log.error("Database error at find Contact by id", e);
            throw new RepositoryException("Cant find entity", e);
        }
        return null;
    }

    @Override
    public Collection<Contact> findByName(String name) throws RepositoryException {
        List<Contact> contacts = new ArrayList<>();

        try (Connection connection = connectionProvider.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT * FROM Contact WHERE name LIKE ?"
            );
            preparedStatement.setString(1, "%" + name + "%");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Contact contact = getContactFromResultSet(resultSet);
                contacts.add(contact);
            }
            log.info("Find Contact by name successful");
        } catch (SQLException e) {
            log.error("Database error at find Contact by name", e);
            throw new RepositoryException("Cant find entity", e);
        }

        return contacts;
    }

    @Override
    public Long getCount() throws RepositoryException {
        var count = 0L;

        try (Connection connection = connectionProvider.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM Contact");
            if (resultSet.next()) {
                count = resultSet.getLong(1);
            }
            log.info("Get Contact count successful");
        } catch (SQLException e) {
            log.error("Get Contact count error", e);
            throw new RepositoryException("Cant get count", e);
        }

        return count;
    }

    @Override
    public Contact create(Contact entity) throws RepositoryException {
        Contact newContact = null;

        try (Connection connection = connectionProvider.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO Contact (`name`, `email`, `phoneNumber`, `address`, `birthdate`) VALUES (?,?,?,?,?)",
                    Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, entity.getName());
            preparedStatement.setString(2, entity.getEmail());
            preparedStatement.setString(3, entity.getPhoneNumber());
            preparedStatement.setString(4, entity.getAddress());
            preparedStatement.setDate(5, new Date(entity.getBirthdate().getTime()));
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                Long newId = resultSet.getLong(1);
                newContact = findByID(newId);
            }
            log.info("Contact creation was successful");
        } catch (SQLException e) {
            log.error("Contact creation error", e);
            throw new RepositoryException("Error at creating entity", e);
        }

        return newContact;
    }

    @Override
    public Contact update(Contact entity) throws RepositoryException {
        try (Connection connection = connectionProvider.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "UPDATE Contact SET name = ?, email = ?, phoneNumber = ?, "
                            + "address = ?, birthdate = ? WHERE id = ?"
            );
            preparedStatement.setString(1, entity.getName());
            preparedStatement.setString(2, entity.getEmail());
            preparedStatement.setString(3, entity.getPhoneNumber());
            preparedStatement.setString(4, entity.getAddress());
            preparedStatement.setDate(5, new Date(entity.getBirthdate().getTime()));
            preparedStatement.setLong(6, entity.getId());
            preparedStatement.executeUpdate();
            log.info("Contact update was successful");
        } catch (SQLException e) {
            log.error("Contact update error", e);
            throw new RepositoryException("Error at update entity", e);
        }
        return findByID(entity.getId());
    }

    @Override
    public void delete(Long id) throws RepositoryException {
        try (Connection connection = connectionProvider.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM Contact WHERE id = ?");
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            log.info("Contact deletion was successful");
        } catch (SQLException e) {
            log.error("Contact delete error", e);
            throw new RepositoryException("Error at delete entity", e);
        }
    }

    private static Contact getContactFromResultSet(ResultSet resultSet) throws SQLException {
        Contact contact = new Contact();
        contact.setId(resultSet.getLong("id"));
        contact.setName(resultSet.getString("name"));
        contact.setEmail(resultSet.getString("email"));
        contact.setPhoneNumber(resultSet.getString("phoneNumber"));
        contact.setAddress(resultSet.getString("address"));
        contact.setBirthdate(resultSet.getDate("birthdate"));
        return contact;
    }
}
