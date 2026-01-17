package com.example.caiosystems.serialization;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class TrimmedStringDeserializer extends JsonDeserializer<String> {

	@Override
	public String deserialize(JsonParser p, DeserializationContext ctxt) 
		throws IOException 
	{
		String str = p.getText();
		return str != null ? str.trim() : null;
	}
}