package com.cmms.api.security;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

public class AuthoritiesDeserializer extends StdDeserializer<Collection<? extends GrantedAuthority>> {

    public AuthoritiesDeserializer() {
        super(Collection.class);
    }

    @Override
    public Collection<? extends GrantedAuthority> deserialize(JsonParser p, DeserializationContext ctxt)
            throws IOException, JsonProcessingException {
        JsonNode node = p.getCodec().readTree(p);
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        for (JsonNode authorityNode : node) {
            authorities.add(new SimpleGrantedAuthority(authorityNode.asText()));
        }
        return authorities;
    }
}
