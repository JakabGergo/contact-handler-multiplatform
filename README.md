# IDDE laborfeladatok

Adatbázis kódok:
CREATE TABLE Contact (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    phoneNumber VARCHAR(50),
    birthdate DATE,
    address VARCHAR(255)
);
ALTER TABLE Contact ADD COLUMN gender VARCHAR(10);
ALTER TABLE Contact MODIFY COLUMN phoneNumber VARCHAR(100);
ALTER TABLE Contact CHANGE COLUMN address fullAddress VARCHAR(255);
ALTER TABLE Contact DROP COLUMN birthdate;
ALTER TABLE Contact ADD CONSTRAINT unique_email UNIQUE (email);
SELECT * FROM Contact;

Desktop futtatása:
Desktop konfigurációt kiválasztva és a környezeti változót megadva
- mem: DAO_FACTORY_IMPL=mem
- jdbc: DAO_FACTORY_IMPL=prod (az alapérték is ez, ha nem mem lenne beállítva)

Web futtatása:
HA váltok a dev és prod között környezeti változóban, akkor újra kell indítani a tomcatet!!
Miután módosítottam mindig ./gradlew undeploy, majd ./gradlew deploy

Handlebars endpointok:
- backend: http://localhost:8080/jgim2241-web/contacts
- backend: http://localhost:8080/jgim2241-web/contacts?id=1
- backend: http://localhost:8080/jgim2241-web/login
- backend: http://localhost:8080/jgim2241-web/logout
- bejelentkezési form: http://localhost:8080/jgim2241-web/login (admin - password)
- listázás: http://localhost:8080/jgim2241-web/contact-list

Spring:
- endpointok:
  - http://localhost:8081/api/contacts
  - http://localhost:8081/api/contacts/42/notes

Bemeneti json body:
Contact {
    "name": "John Doe",
    "email": "johndoe@example.com",
    "phoneNumber": "55555555544",
    "birthdate": "2025-01-01T00:00:00.000+00:00",
    "address": "123 Main Street, Springfield"
}

Note {
    "content": "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nunc justo ex, congue et nisi in, auctor pretium lacus. Quisque gravida bibendum varius. In ac ex elit. Quisque tempor malesuada orci vitae ultrices. Sed eu dolor ut sapien blandit pulvinar vitae eget libero. Nullam quis libero diam. Praesent efficitur feugiat odio, nec ultricies libero. Sed sit amet pretium nisi, vel pulvinar sem.",
    "createdAt": "2025-11-01T00:00:00.000+00:00"
}