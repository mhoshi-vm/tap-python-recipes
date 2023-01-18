package jp.vmware.tanzu.pythonfuncconvention.model;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import io.kubernetes.client.custom.IntOrString;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;

@JsonComponent
public class IntOrStringSerializer extends StdSerializer<IntOrString> {

    public IntOrStringSerializer(){
        super(IntOrString.class);
    }

    @Override
    public void serialize(IntOrString intOrString, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if(intOrString.isInteger()){
            jsonGenerator.writeNumber(intOrString.getIntValue());
        }else{
            jsonGenerator.writeString(intOrString.getStrValue());
        }
    }
}
