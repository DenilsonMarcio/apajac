package com.apajac.acolhimento;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
        info = @Info(
                title = "Projeto Integrador - APAJAC / Jacareí",
                version = "1.0",
                description = "A APAJAC de Jacareí é uma associação dedicada a apoiar jovens e suas famílias na comunidade local." +
                        " Através de programas educacionais, orientação psicossocial e atividades recreativas, " +
                        "buscamos promover o desenvolvimento pessoal e social dos jovens, enquanto trabalhamos para prevenir problemas como violência juvenil e evasão escolar. " +
                        "Nossa missão é fortalecer laços familiares, promover a cidadania e oferecer oportunidades para que os jovens alcancem seu pleno potencial " +
                        "e construam um futuro promissor.",
                contact = @Contact(name = "Equipe DevTeam PI 1 - Univesp")
        ),
        servers = {
                @Server(url = "http://localhost:80", description = "Servidor Local"),
                @Server(url = "https://acolhimento-apajac-dev.squareweb.app/", description = "Servidor de Desenvolvimento")
//                ,@Server(url = "https://associacao-apajac.squareweb.app//", description = "Servidor de Produção")
        },
        tags = {
                @Tag(name = "Assistidos", description = "Endpoints para gerenciamento de assistidos"),
                @Tag(name = "Usuários", description = "Endpoints para gerenciamento de usuários"),
                @Tag(name = "Auditória", description = "Endpoints de auditória"),
                @Tag(name = "Endereço", description = "Endpoints para busca de endereços"),
                @Tag(name = "Avaliações", description = "Endpoints para gerenciamento das avaliações"),
                @Tag(name = "Info", description = "Endpoint para gerenciamento da saúde da aplicação"),
                @Tag(name = "Relatórios", description = "Endpoints para geração de relatórios")
        }
)
public class AcolhimentoApplication {

    public static void main(String[] args) {
        SpringApplication.run(AcolhimentoApplication.class, args);
    }

}
