package com.accounting.accountingapi.event;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

import javax.servlet.http.HttpServletResponse;

@Getter
public class ResourceCreatedEvent extends ApplicationEvent {

    private static final long serialVersionUID = -4173682947858245477L;

    private transient HttpServletResponse response;
    private Long id;

    public ResourceCreatedEvent(Object source, HttpServletResponse response, Long id) {
        super(source);
        this.response = response;
        this.id = id;
    }
}
