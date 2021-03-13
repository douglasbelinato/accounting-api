package com.accounting.accountingapi.interfaces.rest.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                title = "Accounting API",
                description = "" +
                        "API for accounting operations",
                contact = @Contact(
                        name = "Douglas Belinato",
                        url = "https://github.com/douglasbelinato/",
                        email = "douglas_belinato@hotmail.com"
                ),
                license = @License(
                        name = "MIT Licence",
                        url = "https://github.com/douglasbelinato/accounting-api/blob/master/LICENSE")),
        servers = @Server(url = "http://localhost:8080")
)
public class SwaggerConfig {
}
