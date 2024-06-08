package com.cmms.api.security;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.springframework.security.core.GrantedAuthority;

import java.io.IOException;
import java.util.Collection;

public class AuthoritiesSerializer extends StdSerializer<Collection<? extends GrantedAuthority>> {

    public AuthoritiesSerializer() {
        super(Collection.class, false);
    }

    @Override
    public void serialize(Collection<? extends GrantedAuthority> authorities, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStartArray();
        for (GrantedAuthority authority : authorities) {
            gen.writeString(authority.getAuthority());
        }
        gen.writeEndArray();
    }
}

