import io.swagger.v3.oas.models.OpenAPI;

import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;


@Configuration
public class OpenAPIConfiguration {

    @Bean
    public OpenAPI defineOpenApi() {
        Server server = new Server();
        server.setUrl("http://localhost:8080");
        server.setDescription("Development");

        Contact myContact = new Contact();
        myContact.setName("Nikolas Louret");
        myContact.setEmail("nikolasalouret@gmail.com");
        myContact.setUrl("https://github.com/NikolasLouret");

        Info information = new Info()
                .title("API Gerenciamento de Pacientes")
                .version("1.0")
                .description("API Java para gerenciar pacientes de uma clínica médica.")
                .contact(myContact);

        return new OpenAPI().info(information).servers(List.of(server));
    }
}
