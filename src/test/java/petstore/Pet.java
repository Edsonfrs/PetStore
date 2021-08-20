package petstore;

import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;


public class Pet {

    String uri = "https://petstore.swagger.io/v2/pet";

    public String lerJson(String caminhoJson) throws IOException {
        return new String(Files.readAllBytes(Paths.get(caminhoJson)));
    }
    @Test(priority = 1)
    public void incluirPet() throws IOException {

        String jsonBody = lerJson("db/pet1.json");

        given()
                .contentType("application/Json")
                .log().all().body(jsonBody).
        when()
                .post(uri).
        then()
                .log().all().statusCode(200)
                .body("name", is("Cerberus"))
                .body("status", is("available"))
                .body("category.name", is("Dog"))

        ;

    }

    @Test(priority = 2)
    public void consultarPet() {
        String petId = "31101969";

        given()
                .contentType("application/Json")
                .log().all().
        when()
                .get(uri + "/" + petId).

        then()
                .log().all()
                .statusCode(200)
                .body("name", is("Cerberus"))
                .body("status", is("available"))
                .body("category.name", is("Dog"))
        ;
    }

    @Test(priority = 3)
    public void alterarPet() throws IOException {
        String jsonBody = lerJson("db/pet2.json");

        given()
                .contentType("application/Json")
                .log().all().body(jsonBody).

        when()
                .put(uri).

        then()
                .log().all().statusCode(200)
                .body("name", is("Cerberus"))
                .body("status", is("sold"))

        ;

    }

}
